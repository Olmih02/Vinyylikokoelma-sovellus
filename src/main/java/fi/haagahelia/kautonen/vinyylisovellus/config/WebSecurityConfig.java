package fi.haagahelia.kautonen.vinyylisovellus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
          .authorizeHttpRequests(auth -> auth
            .requestMatchers("/css/**","/register**").permitAll()
            .anyRequest().authenticated()
          )
          .formLogin(login -> login
            .loginPage("/login")
            .defaultSuccessUrl("/vinyllist", true)
            .permitAll()
          )
          .logout(logout -> logout.permitAll());

     

        return http.build();
    }
}