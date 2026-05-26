package com.brayan.security.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 80)
    @NotBlank
    @Size(max = 80)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 80)
    @NotBlank
    @Size(max = 80)
    private String lastName;

    @Column(nullable = false, unique = true, length = 120)
    @NotBlank
    @Email
    @Size(max = 120)
    private String email;

    @Column(nullable = false)
    @NotBlank
    private String password;

    @Column(nullable = false)
    @NotNull
    private Boolean enabled;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
        @Builder.Default
        private Set<Role> roles = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        if (this.enabled == null) {
            this.enabled = true;
        }
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
