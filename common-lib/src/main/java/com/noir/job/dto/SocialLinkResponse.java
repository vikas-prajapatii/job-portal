package com.noir.job.dto;

import com.noir.job.domain.SocialPlatform;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialLinkResponse {
    private SocialPlatform platform;
    private String url;

}
