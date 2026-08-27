package com.noir.job.gateway.config;

import com.noir.job.gateway.jwt.JwtConstant;
import com.noir.job.gateway.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;


@Configuration
@RequiredArgsConstructor
public class RouteConfig {

    private final JwtUtil jwtUtil;


    @Bean
    public RouterFunction<ServerResponse> authRoutes() {
        return GatewayRouterFunctions.route("auth-routes")
                .route(RequestPredicates.path("/auth/**")
                        .or(RequestPredicates.path("/oauth2/**"))
                        .or(RequestPredicates.path("/login/**")), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("job-portal-user-service"))
                .build();
    }

    // ==================== Admin-only Routes (JWT + ROLE_ADMIN) ====================

    @Bean
    public RouterFunction<ServerResponse> adminRoutes() {
        return GatewayRouterFunctions.route("admin-routes")
                .route(RequestPredicates.path("/api/admin/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("job-portal-user-service"))
                .before(this::jwtAuthFilter)
                .before(request -> requireRole(request, "ROLE_ADMIN"))
                .build();
    }

    // ==================== Protected Routes (JWT required) ====================

    @Bean
    public RouterFunction<ServerResponse> userServiceRoutes() {
        return GatewayRouterFunctions.route("user-service-routes")
                .route(RequestPredicates.path("/api/users/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("job-portal-user-service"))
                .before(this::jwtAuthFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> companyServiceRoutes() {
        return GatewayRouterFunctions.route("company-service-routes")
                .route(RequestPredicates.path("/api/companies/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("job-portal-company-service"))
                .before(this::jwtAuthFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> jobServiceRoutes() {
        return GatewayRouterFunctions.route("job-service-routes")
                .route(RequestPredicates.path("/api/jobs/**")
                        .or(RequestPredicates.path("/api/job-categories/**"))
                        .or(RequestPredicates.path("/api/job-skills/**"))
                        .or(RequestPredicates.path("/api/job-tags/**")),
                        HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("job-portal-job-service"))
                .before(this::jwtAuthFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> applicationServiceRoutes() {
        return GatewayRouterFunctions.route("application-service-routes")
                .route(RequestPredicates.path("/api/applications/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("job-portal-application-service"))
                .before(this::jwtAuthFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> resumeServiceRoutes() {
        return GatewayRouterFunctions.route("resume-service-routes")
                .route(RequestPredicates.path("/api/resumes/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("job-portal-resume-service"))
                .before(this::jwtAuthFilter)
                .build();
    }





    @Bean
    public RouterFunction<ServerResponse> preferenceServiceRoutes() {
        return GatewayRouterFunctions.route("preference-service-routes")
                .route(RequestPredicates.path("/api/preferences/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("job-portal-preference-service"))
                .before(this::jwtAuthFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> aiServiceRoutes() {
        return GatewayRouterFunctions.route("ai-service-routes")
                .route(RequestPredicates.path("/api/ai/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("job-portal-ai-service"))
                .before(this::jwtAuthFilter)
                .build();
    }

    // ==================== JWT Filter ====================

    private ServerRequest jwtAuthFilter(ServerRequest request) {
        String authHeader = request.headers().firstHeader(JwtConstant.JWT_HEADER);

        if (authHeader == null || !authHeader.startsWith(JwtConstant.TOKEN_PREFIX)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Missing or invalid Authorization header");
        }

        String token = authHeader.substring(JwtConstant.TOKEN_PREFIX.length());

        if (!jwtUtil.isTokenValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Invalid or expired JWT token");
        }

        String email = jwtUtil.extractEmail(token);
        String authorities = jwtUtil.extractAuthorities(token);
        Long userId = jwtUtil.extractUserId(token);

        return ServerRequest.from(request)
                .header("X-User-Id", String.valueOf(userId))
                .header("X-User-Email", email)
                .header("X-User-Roles", authorities)
                .build();
    }

    // ==================== Role Authorization ====================

    private ServerRequest requireRole(ServerRequest request, String role) {
        String roles = request.headers().firstHeader("X-User-Roles");
        if (roles == null || !roles.contains(role)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Access denied. Required role: " + role);
        }
        return request;
    }
}
