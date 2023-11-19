package com.softuni.gameshop.config;

import com.softuni.gameshop.model.enums.UserRoleEnum;
import com.softuni.gameshop.repository.UserRepository;
import com.softuni.gameshop.service.impl.GameShopUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(
                authorizeRequests -> authorizeRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/", "/login", "/register", "/login-error", "/return-policy" ,"/games").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/add-to-cart/", "/cart", "/order", "/my-orders", "/games/{id}/add-review").hasRole(UserRoleEnum.USER.name())
                        .requestMatchers("/games/add", "games/edit/**", "/games/delete/**", "/orders", "/orders/", "/admin/add").hasRole(UserRoleEnum.ADMIN.name())
                        .anyRequest().authenticated()
        )
                .formLogin(
                        formLogin -> {
                            formLogin
                                    .loginPage("/login")
                                    .usernameParameter("username")
                                    .passwordParameter("password")
                                    .defaultSuccessUrl("/",true)
                                    .failureForwardUrl("/login-error");
                        }
                ).logout(
                        logout -> {
                            logout
                                    .logoutUrl("/logout")
                                    .logoutSuccessUrl("/")
                                    .invalidateHttpSession(true)
                                    .deleteCookies("JSESSIONID");
                        }
                ).csrf((csrf) -> csrf.csrfTokenRepository(new HttpSessionCsrfTokenRepository()))
                .build();

    }


    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new GameShopUserDetailsService(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }
}
