package com.stormx.hicoder.entities;

import com.stormx.hicoder.common.TokenType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Setter
@Getter
@Table(name = "tokens")
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Getter
    private String token;

    @Getter
    @Enumerated(EnumType.STRING)
    private TokenType type;

    @Getter
    private boolean active;

    public Token(User user, String deviceToken, TokenType type) {
        this.user = user;
        this.token = deviceToken;
        this.type = type;
        this.active = false;
    }
}
