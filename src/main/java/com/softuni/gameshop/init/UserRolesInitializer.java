package com.softuni.gameshop.init;

import com.softuni.gameshop.model.UserRole;
import com.softuni.gameshop.model.enums.UserRoleEnum;
import com.softuni.gameshop.repository.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserRolesInitializer implements CommandLineRunner {

    private final UserRoleRepository userRoleRepository;

    public UserRolesInitializer(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public void run(String... args) {
        UserRoleEnum[] roleEnums = UserRoleEnum.values();

        for (UserRoleEnum roleEnum : roleEnums) {
            UserRole existingRole = userRoleRepository.findByRoleName(roleEnum);
            if (existingRole == null) {
                UserRole role = new UserRole().setRoleName(roleEnum);
                userRoleRepository.save(role);
            }
        }
    }
}
