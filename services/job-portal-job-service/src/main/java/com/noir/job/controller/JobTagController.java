package com.noir.job.controller;

import com.noir.job.dto.ApiResponse;
import com.noir.job.dto.JobTagResponse;
import com.noir.job.payload.JobTagRequest;
import com.noir.job.service.JobTagService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/job-tags")
public class JobTagController {
    private final JobTagService tagService;


    @PostMapping
    public ResponseEntity<JobTagResponse> createTag(
            @RequestBody @Valid JobTagRequest req) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tagService.createTag(req));
    }

    @GetMapping
    public ResponseEntity<List<JobTagResponse>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @GetMapping("/search")
    public ResponseEntity<List<JobTagResponse>> searchTags(
            @RequestParam String keyword) {
        return ResponseEntity.ok(tagService.searchTags(keyword));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobTagResponse> getTagById(
            @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(tagService.getTagId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobTagResponse> updateTag(
            @PathVariable Long id,
            @RequestBody @Valid JobTagRequest req)
            throws Exception {
        return ResponseEntity.ok(tagService.updateTag(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteTag(
            @PathVariable Long id) throws Exception {
        tagService.deleteTag(id);
        return ResponseEntity.ok(new ApiResponse("Tag deleted successfully", true));

    }
}