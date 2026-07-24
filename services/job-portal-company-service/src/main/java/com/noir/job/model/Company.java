package com.noir.job.model;

import com.noir.job.domain.CompanySize;
import com.noir.job.domain.CompanyStatus;
import com.noir.job.domain.CompanyType;
import com.noir.job.domain.IndustryType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.internal.build.AllowPrintStacktrace;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "companies")
public class Company{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tagLine;
    private String description;
    private String coverImageUrl;
    private String website;
    private int foundedYear;
    @Enumerated(EnumType.STRING)
    private CompanySize companySize;
    @Column(unique = true)
    private String slug;
    private String email;
    @Column(unique = true, nullable = false)
    private String name;
    private String phone;
    private String logoUrl;
    @Enumerated(EnumType.STRING)
    private CompanyType companyType;
    @Enumerated(EnumType.STRING)
    private IndustryType industryType;
    private CompanyStatus companyStatus;
    @Column(unique = true)
    private String registrationNumber;
    @Column(unique = true, nullable = false)
    private Long ownerId;
    private boolean isVerified = false;
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<SocialLink> socialLinks = new ArrayList<>();
    private boolean active = true;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
