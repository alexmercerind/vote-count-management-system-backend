package com.alexmercerind.votecountmanagementsystem.configuration;

import java.util.Arrays;
import java.util.HashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfiguration {

    static private final String ROLE_ADMIN = "ADMIN";
    static private final String ROLE_USER = "USER";

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(
                        authorizeRequests -> {
                            authorizeRequests
                                    .requestMatchers("/rounds/")
                                    .permitAll()
                                    .requestMatchers("/candidates/image/*")
                                    .permitAll()
                                    .requestMatchers("/candidates/*")
                                    .hasRole(ROLE_ADMIN)
                                    .requestMatchers("/rounds/*")
                                    .hasRole(ROLE_USER)
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

        final HashMap<String, String> users = new HashMap<String, String>();

        final UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode(users.get("qiKdmpKKhl")))
                .roles(ROLE_USER, ROLE_ADMIN)
                .build();
        manager.createUser(admin);

        users.put("lalkuan", "Fcwf5IezsG");
        users.put("bhimtal", "HhcGT7fipb");
        users.put("nainital", "dS13Fpc5Ia");
        users.put("haldwani", "AH2VDN1cHy");
        users.put("kaladhungi", "qiitEXqrwx");

        users.put("jaspur", "fDTf7tgU7M");
        users.put("kashipur", "LhTW0e4OCV");
        users.put("bajpur", "7FWDxLI8eh");
        users.put("gadarpur", "5wDpgPgQk3");
        users.put("rudrapur", "cL4V8ucRYU");
        users.put("kiccha", "8yUUVZzK16");
        users.put("sitarganj", "ODZHfMdFCm");
        users.put("nanakmatta", "gZKLGdr0cV");
        users.put("khatima", "ewYx0J2X4t");

        for (String username : users.keySet()) {
            final UserDetails user = User.withUsername(username)
                    .password(passwordEncoder.encode(users.get(username)))
                    .roles(ROLE_USER)
                    .build();
            manager.createUser(user);
        }
        return manager;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
