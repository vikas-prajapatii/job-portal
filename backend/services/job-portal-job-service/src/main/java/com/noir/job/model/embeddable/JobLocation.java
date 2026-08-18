package com.noir.job.model.embeddable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Builder
public class JobLocation {
    private String address;
    private String city;
    private String country;
    private String state;
    private String zip;
}
