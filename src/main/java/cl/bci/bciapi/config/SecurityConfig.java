package cl.bci.bciapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests((authz) -> authz
                    .requestMatchers("/api/users").permitAll()
                    .requestMatchers("/api/users/**").permitAll()
                    .requestMatchers("v3/api-docs/**").permitAll()
                    .requestMatchers("swagger-ui/**").permitAll()
                    .anyRequest().authenticated()
            );
        return http.build();
    }
}
