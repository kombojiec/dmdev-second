package com.dmdev.app.entity;

import com.dmdev.app.enums.Role;
import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(of = "username")
@ToString(exclude = "passportData")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements BaseEntity<Integer> {

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

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private PassportData passportData;

    public void setPassportData(PassportData passportData) {
        this.passportData = passportData;
        passportData.setUser(this);
    }

}
