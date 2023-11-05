package com.bksproject.bksproject.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CourseRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String abtrac;

    @NotBlank
    private String author;

    @NotBlank
    private String imgUrl;

    @NotBlank
    private Long price;

    @NotBlank
    private String serial;
}
