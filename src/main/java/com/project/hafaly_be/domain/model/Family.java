package com.project.hafaly_be.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="families")
public class Family {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @ToString.Exclude
    @JsonIgnore
    private UUID familyId;

    private String name;
    private String phone;
    private String address;
    private String memberAmount;
    private String imageUrl;
    private String code;
    private Date createdAt;
    private Date updatedAt;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name="host_id")
    private User host;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "family")
    private List<User> members;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "family")
    private List<JoinFamily> joinFamilies;
}
