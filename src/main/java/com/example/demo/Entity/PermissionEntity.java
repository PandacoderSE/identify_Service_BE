package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "permission")
public class PermissionEntity {
    @Id
    private String name ;
    @Column(name ="description")
    private String description ;
}
