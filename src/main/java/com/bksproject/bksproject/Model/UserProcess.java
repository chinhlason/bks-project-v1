package com.bksproject.bksproject.Model;

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
public class UserProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant createAt;

    @NotBlank
    private Boolean isComplete = false;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="lesson_id", referencedColumnName = "ID")
    @JsonManagedReference
    private Lessons lessonIdCurrent;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", referencedColumnName = "ID")
    @JsonManagedReference
    private Users userCurrent;

    public UserProcess(Lessons lessonIdCurrent, Users userCurrent) {
        this.createAt = Instant.now();
        this.lessonIdCurrent = lessonIdCurrent;
        this.userCurrent = userCurrent;
    }

    public void setIsComplete(){
        this.isComplete = !isComplete;
    }
}
