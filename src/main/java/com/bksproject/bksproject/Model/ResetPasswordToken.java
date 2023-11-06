package com.bksproject.bksproject.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ResetPasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String token;

    private Instant expiryDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", referencedColumnName = "ID")
    @JsonManagedReference
    private Users userIdReset;

    public ResetPasswordToken(String token, Instant expiryDate, Users userIdReset) {
        this.token = token;
        this.expiryDate = expiryDate;
        this.userIdReset = userIdReset;
    }
}
