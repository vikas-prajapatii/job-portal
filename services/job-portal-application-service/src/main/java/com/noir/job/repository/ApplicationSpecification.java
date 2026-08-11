package com.noir.job.repository;

import com.noir.job.domain.AiShortlistStatus;
import com.noir.job.domain.ApplicationStatus;
import com.noir.job.model.Application;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
//import java.util.function.Predicate;

public class ApplicationSpecification {
    public static Specification<Application> forCompanyFilters(Long companyId,
                                                               Long jobId,
                                                               ApplicationStatus status,
                                                               boolean isStarred,
                                                               AiShortlistStatus aiShortlistStatus,
                                                               Integer minAiScore
                                                               ) {
        return(root,query, cb)->{
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("companyId"),companyId));
            if(status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            if(jobId != null) {
                predicates.add(cb.equal(root.get("jobId"), jobId));
            }
            if(isStarred) {
                predicates.add(cb.equal(root.get("isStarred"), isStarred));


            }
            if(aiShortlistStatus != null) {
                predicates.add(cb.equal(root.get("aiShortlistStatus"), aiShortlistStatus));
            }
            if(minAiScore != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("aiScore"), minAiScore));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };

    }
}
