package com.softuni.gameshop.model;

import com.softuni.gameshop.model.enums.UserRoleEnum;
import jakarta.persistence.*;

@Entity
@Table(name ="roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum roleName;

    public UserRole() {
    }

    public Long getId() {
        return id;
    }

    public UserRole setId(Long id) {
        this.id = id;
        return this;
    }

    public UserRoleEnum getRoleName() {
        return roleName;
    }

    public UserRole setRoleName(UserRoleEnum roleName) {
        this.roleName = roleName;
        return this;
    }
}

