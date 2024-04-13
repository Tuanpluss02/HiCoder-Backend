package com.stormx.hicoder.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;


@Entity
@Setter
@Getter
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "receiver_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User receiver;

    @CreationTimestamp
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "send_at")
    private Timestamp sendAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "edited_at")
    private Timestamp editedAt;

    public boolean isSendBy(User user) {
        return sender.getId().equals(user.getId());
    }
}