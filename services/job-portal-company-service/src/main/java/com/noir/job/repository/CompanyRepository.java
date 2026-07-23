package com.noir.job.repository;

import com.noir.job.domain.CompanyStatus;
import com.noir.job.domain.CompanyType;
import com.noir.job.domain.IndustryType;
import com.noir.job.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByOwnerId(Long id);
    boolean existsByOwnerId(Long ownerId);
    boolean existsByName(String name);
    boolean existsBySlug(String slug);
    boolean existsByRegistrationNumber(String registrationNumber);
    @Query("select c from Company c where" +
    "(:companyType Is NULL OR c.companyType=:companyType) AND"+
    "(:industryType IS NULL OR c.industryType=:industryType) AND"+
    "(:status IS NULL OR c.companyStatus = :status)"
    )
    List<Company> findByFilters(
            @Param("companyType") CompanyType companyType,
            @Param("industryType")IndustryType industryType,
            @Param("status")CompanyStatus status
            );
}
