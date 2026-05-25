package com.example.test.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name= "user")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "dateofbirth")
    private String dateOfBirth;

    @Column(name = "phone")
    private String phone;
}
