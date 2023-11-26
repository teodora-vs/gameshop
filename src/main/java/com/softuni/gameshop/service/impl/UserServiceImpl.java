package com.softuni.gameshop.service.impl;


import com.softuni.gameshop.model.DTO.UserRegisterDTO;
import com.softuni.gameshop.model.ShoppingCart;
import com.softuni.gameshop.model.UserEntity;
import com.softuni.gameshop.model.UserRole;
import com.softuni.gameshop.model.enums.UserRoleEnum;
import com.softuni.gameshop.repository.ShoppingCartRepository;
import com.softuni.gameshop.repository.UserRepository;
import com.softuni.gameshop.repository.UserRoleRepository;
import com.softuni.gameshop.service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public boolean register(UserRegisterDTO userRegisterDTO) {
        if (userRegisterDTO == null || !userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())){
            return false;
        }

        String username = userRegisterDTO.getUsername();
        Optional<UserEntity> byUsername = this.userRepository.findByUsername(username);

        if (byUsername.isPresent()){
            return false;
        }

        UserEntity user = modelMapper.map(userRegisterDTO, UserEntity.class);
        UserRole userRole = userRoleRepository.findByRoleName(UserRoleEnum.USER);
        user.setUserRoles(Collections.singletonList(userRole));
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));

        this.userRepository.save(user);

        return true;
    }


    @Override
    @Transactional
    public void addAdmin(){
        UserEntity user = new UserEntity();
        user.setUsername("admin");
        user.setEmail("admin@example.com");
        user.setFullName("Admin adminov");

        UserRole adminRole = userRoleRepository.findByRoleName(UserRoleEnum.ADMIN);

        if (adminRole == null) {
            adminRole = new UserRole().setRoleName(UserRoleEnum.ADMIN);
            userRoleRepository.save(adminRole);
        }

        user.setUserRoles(Collections.singletonList(adminRole));
        user.setPassword(passwordEncoder.encode("admin"));
        userRepository.save(user);
    }

    @Override
    public boolean addAdminByUsername(String username) {
        Optional<UserEntity> byUsername = this.userRepository.findByUsername(username);
        if (byUsername.isEmpty()){
            return  false;
        }
        UserEntity user = byUsername.get();

        List<UserRole> currentRoles = user.getUserRoles();
        UserRole userRole = this.userRoleRepository.findByRoleName(UserRoleEnum.ADMIN);

        if (currentRoles.contains(userRole)){
            return false;
        }

//        if (byUsername.get().getEmail() == null){
//            return false;
//        }

        currentRoles.add(userRole);
        user.setUserRoles(currentRoles);
        this.userRepository.save(user);

        return true;
    }


    public Optional<UserEntity> getByUsername(String username){
        Optional<UserEntity> byUsername = this.userRepository.findByUsername(username);
        return byUsername;
    }


}
