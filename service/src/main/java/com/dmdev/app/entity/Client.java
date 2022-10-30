package com.dmdev.app.entity;

import com.dmdev.app.enums.ClientStatus;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "orders")
@EqualsAndHashCode(exclude = {"orders", "id"})
@Builder
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private Initials initials;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private ClientStatus status;

    @Builder.Default
    @OneToMany(mappedBy = "client")
    private List<Order> orders = new ArrayList<>();

}
