package fi.haagahelia.kautonen.vinyylisovellus.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import fi.haagahelia.kautonen.vinyylisovellus.domain.Vinyl;

/**
 * Repository-rajapinta Vinyl-entiteetin CRUD-toimintoja varten.
 * Spring Data luo automaattisesti toteutuksen tämän rajapinnan metodeille.
 */
public interface VinylRepository extends CrudRepository<Vinyl, Long> {

    /**
     * Hakee tietokannasta kaikki vinyylit, joiden artisti täsmää parametrin arvoon.
     * @param artist artistin nimi
     * @return lista vinyylejä kyseiseltä artistilta
     */
    List<Vinyl> findByArtist(String artist);

    /**
     * Hakee kaikki vinyylit, joiden title-kenttä vastaa annettua nimeä.
     * @param title albumin tai levyn nimi
     * @return lista vinyylejä, joiden nimi täsmää
     */
    List<Vinyl> findByTitle(String title);

    /**
     * Hakee vinyylit julkaisuvuoden mukaan. Koskee myös vapaatekstikenttää,
     * jolloin esimerkiksi 'Re-issue' toimii.
     * @param releaseYear julkaisuvuosi tai lisätieto
     * @return lista vinyylejä kyseiseltä vuodelta/merkinnällä
     */
    List<Vinyl> findByReleaseYear(String releaseYear);

    /**
     * Hakee vinyylit tarkan ostohinnan perusteella.
     * @param price ostohinta euroissa
     * @return lista vinyylejä, jotka on ostettu samalla hinnalla
     */
    List<Vinyl> findByPrice(double price);

    List<Vinyl> findByOwnerUsername(String username);

}
