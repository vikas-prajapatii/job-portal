package com.noir.job.repository;
import com.noir.job.domain.JobStatus;
import com.noir.job.model.Job;
import com.noir.job.payload.JobSearchRequest;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;
public class JobSpecification {
    private JobSpecification() {}
    public static Specification<Job> build(JobSearchRequest request){
        return (root,query, criteriaBuilder)->{
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.isTrue(root.get("active")));
            JobStatus status = request.getStatus() != null ? request.getStatus() : JobStatus.ACTIVE;
            predicates.add(criteriaBuilder.equal(root.get("status"), status));
            if(request.getWorkMode()!= null) predicates.add(criteriaBuilder.equal(root.get("workMode"), request.getWorkMode()));
            if(request.getExperienceLevel() != null) predicates.add(criteriaBuilder.equal(root.get("experienceLevel"), request.getExperienceLevel()));
            if(request.getCategoryId() != null) predicates.add(criteriaBuilder.equal(root.get("categoryId"), request.getCategoryId()));
            if(request.getCompanyId() != null) predicates.add(criteriaBuilder.equal(root.get("companyId"), request.getCompanyId()));
            if(request.getJobType() != null) predicates.add(criteriaBuilder.equal(root.get("jobType"), request.getJobType()));
            if(request.getLocation() != null && !request.getLocation().isBlank())
            {
                String pattern = "%" + request.getLocation().toLowerCase() + "%";
                Path<String> city = root.get("location").get("city");
                Path<String> state = root.get("location").get("state");
                Path<String> country = root.get("location").get("country");
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(city),pattern),
                        criteriaBuilder.like(criteriaBuilder.lower(state),pattern),
                        criteriaBuilder.like(criteriaBuilder.lower(country),pattern)
                ));
            }
            if(request.getMinSalary() != null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("salaryRange").get("maxSalary").as(java.math.BigDecimal.class), request.getMinSalary()));
            }
            if(request.getMaxSalary() != null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("salaryRange").get("maxSalary").as(java.math.BigDecimal.class), request.getMaxSalary()));
            }
            if(request.getMinOpenings() != null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("openings").as(Integer.class), request.getMinOpenings()));
            }
            if(request.getMaxOpenings() != null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("openings").as(Integer.class), request.getMaxOpenings()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
