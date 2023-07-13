package com.yongkonhahn.homefood.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity  {
    final String USER_ROLE = "USER";
    final String ADMIN_ROLE = "ADMIN";

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeHttpRequests(
                        auth -> auth
                            .requestMatchers("/index","/js/**",
                                                     "/css/**","/lib/**","/scss/**",
                                                     "/img/**").permitAll()
                            .requestMatchers("/menu","/js/**",
                                    "/css/**","/lib/**","/scss/**",
                                    "/img/**").permitAll()
                            .requestMatchers("/contact","/js/**",
                                    "/css/**","/lib/**","/scss/**",
                                    "/img/**").permitAll()
                            .requestMatchers("/register/**","/js/**",
                                    "/css/**","/lib/**","/scss/**",
                                    "/img/**").permitAll()
                            .requestMatchers("/book/**","/js/**",
                                    "/css/**","/lib/**","/scss/**",
                                    "/img/**").permitAll()
                            .requestMatchers("/order","/js/**",
                                    "/css/**","/lib/**","/scss/**",
                                    "/img/**").permitAll()
                            .requestMatchers("/cart/**","/js/**",
                                    "/css/**","/lib/**","/scss/**",
                                    "/img/**").permitAll()
                            .anyRequest().authenticated()
                )
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .defaultSuccessUrl("/cart")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll());
        return http.build();
    }
}
