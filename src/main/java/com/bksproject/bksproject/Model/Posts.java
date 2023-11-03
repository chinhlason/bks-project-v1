package com.bksproject.bksproject.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant createAt;

    @NotBlank
    private String category;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @ManyToOne
    @JoinColumn(name="userID", referencedColumnName = "ID")
//    @JsonBackReference
    private Users user_post;

    @OneToMany(mappedBy = "postId")
    @JsonBackReference
    private Set<Comments> post_comments;

    public Posts(String category, String title, String content, Users user_post) {
        this.createAt = Instant.now();
        this.category = category;
        this.title = title;
        this.content = content;
        this.user_post = user_post;
    }
}
