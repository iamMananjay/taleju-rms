package com.taleju.rms.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;// ADMIN, MANAGER, STAFF, CHEF, CUSTOMER, GUEST

    @OneToMany(mappedBy = "role")
    private Set<User> users; // Users with this role
}
