package com.noir.job.controller;

import com.noir.job.dto.ApiResponse;
import com.noir.job.dto.JobSkillResponse;
import com.noir.job.payload.JobSkillRequest;
import com.noir.job.service.JobSkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/skills")
public class JobSkillController {

    private final JobSkillService jobSkillService;

    @PostMapping
    public ResponseEntity<JobSkillResponse> createSkill(
            @RequestBody @Valid JobSkillRequest req) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobSkillService.createSkill(req));
    }

    @GetMapping
    public ResponseEntity<List<JobSkillResponse>> getAllSkills() {
        return ResponseEntity.ok(jobSkillService.getAllSkills());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobSkillResponse> getSkillById(
            @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(jobSkillService.getSkillsById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobSkillResponse> updateSkill(
            @PathVariable Long id,
            @RequestBody @Valid JobSkillRequest req) throws Exception {
        return ResponseEntity.ok(jobSkillService.updateSkill(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteSkill(
            @PathVariable Long id) throws Exception {
        jobSkillService.deleteSkill(id);
        return ResponseEntity.ok(new ApiResponse("Skill deleted successfully", true));
    }
}
