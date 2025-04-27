package fi.haagahelia.kautonen.vinyylisovellus.repository;

import fi.haagahelia.kautonen.vinyylisovellus.domain.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {
    AppUser findByUsername(String username);
    AppUser findById(long id);
    AppUser findByUsernameAndPassword(String username, String password);

}
