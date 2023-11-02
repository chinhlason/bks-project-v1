package com.bksproject.bksproject.payload.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UpdatePostRequest {
    private String category;
    private String title;
    private String content;
}
