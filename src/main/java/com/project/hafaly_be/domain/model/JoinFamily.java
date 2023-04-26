package com.project.hafaly_be.domain.model;

import com.project.hafaly_be.domain.enums.StatusRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="join_families")
public class JoinFamily {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID requestId;

    @Enumerated(EnumType.STRING)
    private StatusRequest statusRequest;

    private String message;

    private Date createAt;
    private Date updateAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    protected User user;

    @ManyToOne
    @JoinColumn(name = "family_id")
    protected Family family;


}
