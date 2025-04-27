package fi.haagahelia.kautonen.vinyylisovellus.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Palveluluokka vastaa vinyylien kansikuvien tallennuksesta palvelimen tiedostojärjestelmään.
 * Luo tarvittaessa hakemistot ja säilyttää ladatut kuvat uniikeilla nimillä.
 */
@Service
public class FileStorageService {

    /**
     * Polku hakemistoon, johon kansikuvat tallennetaan.
     * Tämä kansio tulee olla resurssipolun ulkopuolella ja sovellus servaa sen WebMvcConfigurerin kautta.
     */
    private final Path coverLocation = Paths.get("uploads/covers");

    /**
     * Oletuskonstruktori, joka varmistaa, että upload-kansio on olemassa.
     * @throws IOException jos kansion luominen epäonnistuu
     */
    public FileStorageService() throws IOException {
        Files.createDirectories(coverLocation);
    }
    
    /**
     * Tallentaa MultipartFile-kuvan file-objektista levylle ja palauttaa tallennetun tiedoston nimen.
     * @param file MultipartFile, joka sisältää ladattavan kuvatiedoston
     * @return tallennetun tiedoston uniikki nimi
     * @throws IOException jos tiedoston kopiointi epäonnistuu
     */
    public String storeCover(MultipartFile file) throws IOException {
        // Puhdistetaan alkuperäinen tiedostonimi haittaohjelmien varalta
        String original = StringUtils.cleanPath(file.getOriginalFilename());

        // Lisätään ajastin luotujen tiedostojen nimeen, jotta sama nimi ei korvaa toista
        String filename = System.currentTimeMillis() + "_" + original;
        Path targetLocation = coverLocation.resolve(filename);

        // Kopioidaan tiedoston sisältö turvallisesti haluttuun kohteeseen
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        return filename;
    }
}
