package com.noir.job.controller;

import com.noir.job.domain.UserRole;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HomeController {
    @GetMapping
    public String homeController(){
        return "job portal user service-----------" + UserRole.ROLE_JOB_SEEKER;
    }
}
