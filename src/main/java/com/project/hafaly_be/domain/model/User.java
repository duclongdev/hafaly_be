package com.project.hafaly_be.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.hafaly_be.domain.enums.Role;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @ToString.Exclude
    @JsonIgnore
    private UUID id;

    private String firstname;
    private String lastname;
    private String email;
    @JsonIgnore
    private String password;
    private String address;
    private String phone;
    private Date dateOfBirth;
    private boolean gender;
    private Date createdAt;
    private Date updateAt;

    @Enumerated(EnumType.STRING)
    @ToString.Exclude
    private Role role;

    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private List<Token> tokens;

    @ManyToOne()
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "family_id")
    @JsonIgnore
    private Family family;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
