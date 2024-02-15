package com.softuni.gameshop.service.impl;


import com.softuni.gameshop.model.DTO.UserRegisterDTO;
import com.softuni.gameshop.model.UserEntity;
import com.softuni.gameshop.model.UserRole;
import com.softuni.gameshop.model.enums.UserRoleEnum;
import com.softuni.gameshop.repository.ShoppingCartRepository;
import com.softuni.gameshop.repository.UserRepository;
import com.softuni.gameshop.repository.UserRoleRepository;
import com.softuni.gameshop.service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_EMAIL = "admin@example.com";
    private static final String ADMIN_FULL_NAME = "Admin Adminov";
    private static final String ADMIN_PASSWORD = "admin";

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository, ShoppingCartRepository shoppingCartRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
        this.shoppingCartRepository = shoppingCartRepository;
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
        user.setUsername(ADMIN_USERNAME);
        user.setEmail(ADMIN_EMAIL);
        user.setFullName(ADMIN_FULL_NAME);

        UserRole adminRole = userRoleRepository.findByRoleName(UserRoleEnum.ADMIN);

        if (adminRole == null) {
            adminRole = new UserRole().setRoleName(UserRoleEnum.ADMIN);
            userRoleRepository.save(adminRole);
        }

        user.setUserRoles(Collections.singletonList(adminRole));
        user.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public boolean addAdminByUsername(String username) {
        Optional<UserEntity> byUsername = this.userRepository.findByUsername(username);
        if (byUsername.isEmpty()){
            return  false;
        }
        UserEntity user = byUsername.get();
        String userEmail = user.getEmail();

        List<UserRole> currentRoles = user.getUserRoles();
        UserRole adminRole = this.userRoleRepository.findByRoleName(UserRoleEnum.ADMIN);

        if (currentRoles.contains(adminRole) || userEmail == null){
            return false;
        }

        Long cartId = user.getShoppingCart().getId();

        if (user.getShoppingCart().getCartItems() != null || !user.getShoppingCart().getCartItems().isEmpty()) {
            user.getShoppingCart().getCartItems().clear();
        }

        user.setShoppingCart(null);

        currentRoles.clear();
        currentRoles.add(adminRole);
        user.setUserRoles(currentRoles);

        this.userRepository.save(user);
        this.shoppingCartRepository.deleteById(cartId);
        this.userRepository.flush();

        return true;
    }

    public Optional<UserEntity> getByUsername(String username){
        Optional<UserEntity> byUsername = this.userRepository.findByUsername(username);
        return byUsername;
    }

}
