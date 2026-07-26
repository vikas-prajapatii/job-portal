package com.noir.job.service;

import com.noir.job.dto.JobCategoryResponse;
import com.noir.job.model.JobCategory;
import com.noir.job.payload.JobCategoryRequest;

import java.util.List;

public interface JobCategoryService {
    JobCategoryResponse createCategory(JobCategoryRequest req) throws Exception;

    List<JobCategoryResponse> getAllCategory();
    JobCategoryResponse getCategoryById(Long id) throws Exception;
    JobCategoryResponse updateCategory(Long id, JobCategoryRequest req) throws Exception;
    void deleteCategory(Long id) throws Exception;
    JobCategory getCategoryEntityById(Long id) throws Exception;
}
