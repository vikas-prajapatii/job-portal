package com.noir.job.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.noir.job.common.domain.SocialPlatform;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SocialLinkResponse {

    private SocialPlatform platform;
    private String url;
}
