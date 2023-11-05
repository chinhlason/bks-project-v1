package com.bksproject.bksproject.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LessonRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String mediaUrl;
}
