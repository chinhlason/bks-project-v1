package com.bksproject.bksproject.payload.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserProcessResponse {
    private Long id;
    private Long lessonId;
    private String lessonTitle;
    private boolean isComplete;

    public UserProcessResponse(Long id, Long lessonId, String lessonTitle, boolean isComplete) {
        this.id = id;
        this.lessonId = lessonId;
        this.lessonTitle = lessonTitle;
        this.isComplete = isComplete;
    }
}
