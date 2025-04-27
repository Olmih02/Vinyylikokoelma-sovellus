package fi.haagahelia.kautonen.vinyylisovellus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import fi.haagahelia.kautonen.vinyylisovellus.domain.AppUser;
import fi.haagahelia.kautonen.vinyylisovellus.repository.AppUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepo.findByUsername(username);
        if (appUser == null) {
            throw new UsernameNotFoundException("Käyttäjää ei löytynyt: " + username);
        }
        return User.withUsername(appUser.getUsername())
                   .password(appUser.getPassword())     // tallennettu on jo bcryptattu
                   .roles(appUser.getRole())           // esim. "USER" tai "ADMIN"
                   .build();
    }
}