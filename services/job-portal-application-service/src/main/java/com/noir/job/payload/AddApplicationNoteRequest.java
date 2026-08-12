package com.noir.job.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddApplicationNoteRequest {

    @NotBlank(message = "Note content is required")
    @Size(max = 2000, message = "Note must not exceed 2000 characters")
    private String content;
}
