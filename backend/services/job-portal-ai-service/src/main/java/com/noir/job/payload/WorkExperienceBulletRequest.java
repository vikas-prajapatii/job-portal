package com.noir.job.payload;

import lombok.Data;

@Data
public class WorkExperienceBulletRequest {
    private String jobTitle;
    private String company;
    private String rawDescription;
    private String achievementsHint;
}
