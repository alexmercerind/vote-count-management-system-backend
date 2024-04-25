package com.alexmercerind.votecountmanagementsystem.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
@PropertySource("classpath:security.properties")
public class SecurityConfiguration {

    @Value("${security.username}")
    private String username;
    @Value("${security.password}")
    private String password;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(
                        authorizeRequests -> {
                            authorizeRequests
                                    .requestMatchers(HttpMethod.GET, "/results/**")
                                    .permitAll()
                                    .anyRequest()
                                    .authenticated();

                        })
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

    @Bean
    UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        final InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        final UserDetails user = User
                .withUsername(username)
                .password(passwordEncoder.encode(password))
                .authorities("ADMIN")
                .build();
        manager.createUser(user);
        return manager;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
