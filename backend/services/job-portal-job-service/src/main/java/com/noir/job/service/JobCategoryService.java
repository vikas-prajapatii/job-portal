package com.noir.job.service;

import com.noir.job.common.dto.response.JobCategoryResponse;
import com.noir.job.common.exception.JobException;
import com.noir.job.common.exception.ResourceNotFoundException;
import com.noir.job.dto.request.BulkJobCategoryRequest;
import com.noir.job.dto.request.JobCategoryRequest;
import com.noir.job.dto.response.BulkJobCategoryResponse;
import com.noir.job.entity.JobCategory;

import java.util.List;

public interface JobCategoryService {

    JobCategoryResponse createCategory(JobCategoryRequest req)
            throws JobException, ResourceNotFoundException;

    BulkJobCategoryResponse createCategoriesBulk(BulkJobCategoryRequest req);

    List<JobCategoryResponse> getAllCategories();

    List<JobCategoryResponse> getRootCategories();

    JobCategoryResponse getCategoryById(Long id) throws ResourceNotFoundException;

    JobCategoryResponse getCategoryBySlug(String slug) throws ResourceNotFoundException;

    JobCategoryResponse updateCategory(Long id, JobCategoryRequest req)
            throws ResourceNotFoundException, JobException;

    void deleteCategory(Long id) throws ResourceNotFoundException;

    /** Used internally. */
    JobCategory getCategoryEntityById(Long id) throws ResourceNotFoundException;
}
