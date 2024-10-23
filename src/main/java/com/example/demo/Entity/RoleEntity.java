package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
public class RoleEntity {
    @Id
    private String name ;
    @Column(name ="description")
    private String description ;

    @ManyToMany
    Set<PermissionEntity> permissions ;
}
