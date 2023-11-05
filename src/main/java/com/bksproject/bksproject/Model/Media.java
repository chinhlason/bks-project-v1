package com.bksproject.bksproject.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant createAt;

    @NotBlank
    private String videoUrl;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="lesson_id", referencedColumnName = "ID")
    @JsonManagedReference
    private Lessons lessonId;

    public Media(String videoUrl, Lessons lessonId) {
        this.createAt = Instant.now();
        this.videoUrl = videoUrl;
        this.lessonId = lessonId;
    }
}
