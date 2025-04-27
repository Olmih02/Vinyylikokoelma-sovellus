package fi.haagahelia.kautonen.vinyylisovellus.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
  
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
          // 1) Salli staattiset resurssit ja login‐sivun GET ilman autentikaatiota
          .authorizeHttpRequests(auth -> auth
            .requestMatchers("/css/**", "/images/**", "/login", "/register").permitAll()
            .anyRequest().authenticated()
          )
          // 2) Form‐login /login –sivulla
          .formLogin(form -> form
            .loginPage("/login")                // oma login‐sivu
            .loginProcessingUrl("/login")       // post‐action
            .defaultSuccessUrl("/vinyllist", true)
            .permitAll()
          )
          // 3) Logout sallittu kaikille
          .logout(logout -> logout.permitAll());

        return http.build();
    }
}