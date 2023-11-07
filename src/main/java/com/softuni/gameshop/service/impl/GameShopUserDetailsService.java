package com.softuni.gameshop.service.impl;
import com.softuni.gameshop.model.UserEntity;
import com.softuni.gameshop.model.UserRole;
import com.softuni.gameshop.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class GameShopUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public GameShopUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(GameShopUserDetailsService::map)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found!"));
    }

    private static UserDetails map(UserEntity userEntity) {
        return User
                .withUsername(userEntity.getUsername())
                .password(userEntity.getPassword())
                .authorities(userEntity.getUserRoles().stream().map(GameShopUserDetailsService::map).toList())
                .build();
    }

    private static GrantedAuthority map(UserRole userRole) {
        return new SimpleGrantedAuthority(
                "ROLE_" + userRole.getRoleName().name()
        );
    }
}
