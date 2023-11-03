package com.bksproject.bksproject.Model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant createAt;

    private boolean isRead;

    private String message;

    private String type;

    public boolean isRead() {
        return isRead;
    }

    @ManyToOne
    @JoinColumn(name="user_ID", referencedColumnName = "ID")
    private Users userNotification;

    @ManyToOne
    @JoinColumn(name="receiver_id", referencedColumnName = "ID")
    private Users receiver;

    public Notification(String message, Users userNotification, String type, Users receiver) {
        this.createAt = Instant.now();
        this.isRead = false;
        this.message = message;
        this.userNotification = userNotification;
        this.type = type;
        this.receiver = receiver;
    }
}
