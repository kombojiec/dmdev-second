package com.dmdev.app.entity;

import com.dmdev.app.enums.Role;
import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(of = "username")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;

    private String password;

    @Embedded
    private Initials initials;

    @Enumerated(EnumType.STRING)
    private Role role;

}
