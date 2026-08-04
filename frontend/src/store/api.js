import axios from "axios"

// Base URLs for Spring Boot Microservices
const SERVICES = {
  USER: "http://localhost:5001",
  COMPANY: "http://localhost:5002",
  JOB: "http://localhost:5003",
  RESUME: "http://localhost:5004",
}

// Create axios instance
const api = axios.create({
  headers: {
    "Content-Type": "application/json",
  },
})

// Helper to decode JWT claims for header injection
const decodeJwt = (token) => {
  try {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(
      window.atob(base64)
        .split('')
        .map((c) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
        .join('')
    );
    return JSON.parse(jsonPayload);
  } catch (e) {
    return null;
  }
}

// Mock database store for unimplemented features (AI, application status, subscription plans)
const mockStore = {
  applications: [
    {
      id: 1,
      jobId: 1,
      jobTitle: "Senior AI Engineer",
      candidateName: "John Doe",
      candidateEmail: "john@example.com",
      status: "SHORTLISTED",
      appliedAt: new Date().toISOString(),
      aiScore: 92,
      aiAnalysis: "Excellent skills matching with Python, LLMs, and Vector stores.",
    }
  ],
  aiMatching: {
    score: 87,
    feedback: "Highly qualified candidate. Substantial overlap in required AI skills and experience.",
    analysis: {
      skillsMatch: 90,
      experienceMatch: 85,
      educationMatch: 80
    }
  },
  subscriptions: [
    { id: 1, name: "Starter Plan", price: 29, active: true },
    { id: 2, name: "Pro Enterprise", price: 149, active: false }
  ]
};

// Request interceptor - dynamic port mapping and automatic header injection
api.interceptors.request.use(
  async (config) => {
    const token = localStorage.getItem("accessToken")
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
      const decoded = decodeJwt(token)
      if (decoded) {
        if (decoded.email) config.headers["X-User-Email"] = decoded.email
        if (decoded.userId) config.headers["X-User-Id"] = decoded.userId.toString()
      }
    }

    const url = config.url || ""

    // Intercept and resolve requests for unfinished services locally (e.g. applications, billing, ai)
    if (url.startsWith("/api/applications") || url.startsWith("/api/subscriptions") || url.startsWith("/api/ai")) {
      config.adapter = async (cfg) => {
        await new Promise(r => setTimeout(r, 400));
        
        let responseData = [];
        if (url.includes("/company")) {
          responseData = mockStore.applications;
        } else if (url.includes("/my")) {
          responseData = mockStore.applications;
        } else if (url.includes("/ai/match")) {
          responseData = mockStore.aiMatching;
        } else if (url.includes("/subscriptions")) {
          responseData = mockStore.subscriptions;
        } else {
          responseData = mockStore.applications[0] || {};
        }

        return {
          data: responseData,
          status: 200,
          statusText: "OK",
          headers: {},
          config: cfg,
        };
      };
      return config;
    }

    // Dynamic Microservice Port Resolution
    if (url.startsWith("/auth") || url.startsWith("/api/users")) {
      config.baseURL = SERVICES.USER;
    } else if (url.startsWith("/api/companies")) {
      config.baseURL = SERVICES.COMPANY;
    } else if (url.startsWith("/api/jobs") || url.startsWith("/api/job-categories") || url.startsWith("/api/job-skills") || url.startsWith("/api/job-tags")) {
      config.baseURL = SERVICES.JOB;
    } else if (url.startsWith("/api/resumes") || url.startsWith("/api/education") || url.startsWith("/api/work-experience") || url.startsWith("/api/resume-skills")) {
      config.baseURL = SERVICES.RESUME;
    } else {
      config.baseURL = SERVICES.USER;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error)
  }
)

// Response interceptor
api.interceptors.response.use(
  (response) => {
    return response
  },
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem("accessToken")
      if (window.location.pathname !== "/login") {
        window.location.href = "/login"
      }
    }
    return Promise.reject(error)
  }
)

export default api
