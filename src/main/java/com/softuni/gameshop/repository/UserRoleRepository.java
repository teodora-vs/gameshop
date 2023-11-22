package com.softuni.gameshop.repository;

import com.softuni.gameshop.model.UserRole;
import com.softuni.gameshop.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    UserRole findByRoleName(UserRoleEnum roleName);

}
