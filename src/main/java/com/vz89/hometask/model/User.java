package com.vz89.hometask.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user")
@NamedEntityGraph(name = "roles_entity_graph", attributeNodes = {@NamedAttributeNode("roles")})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "created", columnDefinition = "DATE")
    private LocalDate created;

    @Column(name = "updated", columnDefinition = "DATE")
    private LocalDate updated;

    @Column(name = "last_password_change_date", columnDefinition = "DATE")
    private LocalDate lastPasswordChangeDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "phone_number")
    private String phoneNumber;

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

}
