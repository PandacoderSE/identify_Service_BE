package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

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
    private long id ;
    @Column(name ="username")
    private String username ;

    @Column(name ="password")
    private String password ;

    @Column(name ="firstname")
    private String firstrname ;

    @Column(name ="lastname")
    private String lastname ;

    @Column(name ="dob")
    private LocalDate dob;
    @Column(name ="roles")
    private Set<String> roles;
}
