package com.noir.job.controller;

import com.noir.job.dto.ApiResponse;
import com.noir.job.dto.JobCategoryResponse;
import com.noir.job.payload.JobCategoryRequest;
import com.noir.job.service.JobCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class JobCategoryController {

    private final JobCategoryService jobCategoryService;

    @PostMapping
    public ResponseEntity<JobCategoryResponse> createCategory(
            @RequestBody @Valid JobCategoryRequest req) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobCategoryService.createCategory(req));
    }

    @GetMapping
    public ResponseEntity<List<JobCategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(jobCategoryService.getAllCategory());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobCategoryResponse> getCategoryById(
            @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(jobCategoryService.getCategoryById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobCategoryResponse> updateCategory(
            @PathVariable Long id,
            @RequestBody @Valid JobCategoryRequest req) throws Exception {
        return ResponseEntity.ok(jobCategoryService.updateCategory(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(
            @PathVariable Long id) throws Exception {
        jobCategoryService.deleteCategory(id);
        return ResponseEntity.ok(new ApiResponse("Category deleted successfully", true));
    }
}
