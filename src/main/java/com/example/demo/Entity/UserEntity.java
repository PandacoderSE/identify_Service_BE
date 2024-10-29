package com.example.demo.Entity;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.*;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    // đánh unique để ko bị ăn 100 request cùng lúc ko valid tính giống nhua đc
    //unique đánh dấu duy nhất ko trùng nhau
    // columnDefinition ="VARCHAR(255) COLLATE utf8mb4_unicode_ci" : đánh dấu ko phân biện hoa thường
    @Column(name = "username", unique = true, columnDefinition ="VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "dob")
    private LocalDate dob;

    @ManyToMany
    private Set<RoleEntity> roles;
}
