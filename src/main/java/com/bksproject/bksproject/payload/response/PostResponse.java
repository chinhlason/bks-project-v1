package com.bksproject.bksproject.payload.response;


import com.bksproject.bksproject.Model.Comments;
import com.bksproject.bksproject.Model.Users;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Data
@Getter
@Setter
public class PostResponse {
    private Long id;

    private Instant createAt;

    private String category;

    private String title;

    private String content;

    private String userPost;

    private Set<CommentResponse> commentPost;

    public PostResponse() {
    }

    public PostResponse(Long id, Instant createAt, String category, String title, String content, String userPost, Set<CommentResponse>  commentPost) {
        this.id = id;
        this.createAt = createAt;
        this.category = category;
        this.title = title;
        this.content = content;
        this.userPost = userPost;
        this.commentPost = commentPost;
    }
}
