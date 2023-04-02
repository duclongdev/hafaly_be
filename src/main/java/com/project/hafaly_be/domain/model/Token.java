package com.project.hafaly_be.domain.model;

import com.project.hafaly_be.domain.enums.TokenType;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tokens")
public class Token {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    protected UUID id;

    @Column(unique = true)
    protected String token;

    @Enumerated(EnumType.STRING)
    protected TokenType tokenType = TokenType.BEARER;

    protected boolean revoked;

    protected boolean expired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    protected User user;

}
