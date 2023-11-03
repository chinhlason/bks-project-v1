package com.bksproject.bksproject.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant createAt;

    @NotBlank
    private String content;

    @ManyToOne
    @JoinColumn(name="userID", referencedColumnName = "ID")
    private Users userId;

    @ManyToOne
    @JoinColumn(name="postID", referencedColumnName = "ID")
    private Posts postId;


    public Comments(String content, Users userId, Posts postId) {
        this.createAt = Instant.now();
        this.content = content;
        this.userId = userId;
        this.postId = postId;
    }

}
