package com.example.demo.Entity;

import java.util.Set;

import jakarta.persistence.*;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
public class RoleEntity {
    @Id
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany
    Set<PermissionEntity> permissions;
}
