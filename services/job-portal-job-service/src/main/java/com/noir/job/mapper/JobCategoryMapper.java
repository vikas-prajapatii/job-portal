package com.noir.job.mapper;

import com.noir.job.dto.JobCategoryResponse;
import com.noir.job.model.JobCategory;
import com.noir.job.payload.JobCategoryRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JobCategoryMapper {

     public static JobCategoryResponse toJobCategoryResponse(JobCategory jobCategory,boolean
                                                             includeChildren) {
         List<JobCategoryResponse> subCategories = null;
         if(includeChildren && jobCategory.getSubcategories() != null && !jobCategory.getSubcategories().isEmpty()) {
             subCategories = jobCategory.getSubcategories()
             .stream().map(sub -> toJobCategoryResponse(sub,false))
             .collect(Collectors.toList());
         }

         return JobCategoryResponse.builder()
                 .id(jobCategory.getId())
                 .name(jobCategory.getName())
                 .description(jobCategory.getDescription())
                 .slug(jobCategory.getSlug())
                 .iconUrl(jobCategory.getIconUrl())
                 .active(jobCategory.isActive())
                 .parentId(jobCategory.getParent() != null ? jobCategory.getParent().getId() : null)
                 .parentName(jobCategory.getParent() != null ? jobCategory.getParent().getName() : null)
                 .subCategories(subCategories)
                 .createdAt(jobCategory.getCreatedAt())
                 .build();
     }
}
