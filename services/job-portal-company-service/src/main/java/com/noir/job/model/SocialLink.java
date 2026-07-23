package com.noir.job.model;


import com.noir.job.domain.SocialPlatform;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Builder
public class SocialLink {
    private String url;
    private SocialPlatform platform;
}
