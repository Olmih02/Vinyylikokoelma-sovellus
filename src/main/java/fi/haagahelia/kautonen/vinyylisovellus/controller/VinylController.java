package fi.haagahelia.kautonen.vinyylisovellus.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import fi.haagahelia.kautonen.vinyylisovellus.domain.AppUser;
import fi.haagahelia.kautonen.vinyylisovellus.domain.Vinyl;
import fi.haagahelia.kautonen.vinyylisovellus.repository.AppUserRepository;
import fi.haagahelia.kautonen.vinyylisovellus.repository.VinylRepository;
import fi.haagahelia.kautonen.vinyylisovellus.service.FileStorageService;

/**
 * VinylController vastaa HTTP-pyyntöjen käsittelystä vinyylien
 * listauksen, lisäämisen, muokkaamisen ja poistamisen osalta.
 * 
 * Se myös huolehtii kansikuvien vastaanotosta ja tallennuksesta FileStorageService:n kautta.
 */

@Controller
public class VinylController {

    @Autowired
    private VinylRepository vinylRepository;

    @Autowired
    private FileStorageService storageService;

    @Autowired
    private AppUserRepository userRepo;

    /**
     * Näyttää vinyylilistan pääsivulla.
     * 
     * @param model Thymeleafin Model, johon lisätään listan tiedot
     * @return näkymän nimi "vinyllist"
     */

    @GetMapping({ "/", "vinyllist" })
public String viewList(Model model,
                       @AuthenticationPrincipal org.springframework.security.core.userdetails.User springUser) {
    // Hae AppUser-olio nykyisellä käyttäjätunnuksella
    AppUser owner = userRepo.findByUsername(springUser.getUsername());

    // Hae vain tämän käyttäjän vinyylit
    List<Vinyl> vinyls = vinylRepository.findByOwnerUsername(owner.getUsername());
    model.addAttribute("vinyls", vinyls);

    // Laske kaikkien vinyylien yhteenlaskettu arvo
    double totalValue = vinyls.stream()
                              .mapToDouble(Vinyl::getPrice)
                              .sum();
    model.addAttribute("totalValue", totalValue);

    // Kokoa erillinen lista artisteista suodatusta varten
    List<String> artists = vinyls.stream()
                                 .map(Vinyl::getArtist)
                                 .distinct()
                                 .sorted()
                                 .toList();
    model.addAttribute("artists", artists);

    return "vinyllist";
}



    /**
     * Näyttää lomakkeen uuden vinyylin lisäämiseksi.
     * 
     * @param model Model-olio, johon lisätään tyhjä Vinyl-objekti lomaketta varten
     * @return näkymän nimi "addvinyl"
     */
    @GetMapping("/addvinyl")
    public String addVinyl(Model model) {
        model.addAttribute("vinyl", new Vinyl());
        return "addvinyl";
    }

    /**
     * Käsittelee uuden vinyylin tallennuksen tietokantaan ja kansikuvan tallennuksen levylle.
     * 
     * @param vinyl      lomakkeelta bindattu Vinyl-objekti
     * @param coverFile  MultipartFile, joka sisältää käyttäjän lataaman kansikuvan
     * @return uudelleenohjauksen polku näkymään "/vinyllist"
     */

  @PostMapping("/savevinyl")
public String saveVinyl(@ModelAttribute Vinyl vinyl,
                        @RequestParam("coverFile") MultipartFile coverFile) throws IOException {
    // 1) Tallenna kuva kuten ennen
    if (!coverFile.isEmpty()) {
        String filename = storageService.storeCover(coverFile);
        vinyl.setCoverFilename(filename);
    }

    // 2) Hae kirjautunut käyttäjä SecurityContextHolderista
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    AppUser me = userRepo.findByUsername(auth.getName());
    vinyl.setOwner(me);

    // 3) Tallenna vinyl kannasta
    vinylRepository.save(vinyl);
    return "redirect:/vinyllist";
}

     /**
     * Poistaa vinyylin tietokannasta annetun ID:n perusteella.
     * 
     * @param id poistettavan vinyylin tunniste
     * @return uudelleenohjaus listaussivulle "/vinyllist"
     */

    @GetMapping("/deletevinyl/{id}")
    public String deleteVinyl(@PathVariable("id") Long id) {
        vinylRepository.deleteById(id);
        return "redirect:/vinyllist";
    }

    /**
     * Näyttää muokkauslomakkeen olemassa olevalle vinyylille.
     * 
     * @param id    muokattavan vinyylin tunniste
     * @param model Model-olio, johon lisätään löydetty Vinyl-objekti
     * @return näkymän nimi "editvinyl"
     */

    @GetMapping("/editvinyl/{id}")
    public String editVinyl(@PathVariable("id") Long id, Model model) {
        model.addAttribute("vinyl", vinylRepository.findById(id).orElse(null));
        return "editvinyl";

    }

     /**
     * Käsittelee olemassa olevan vinyylin päivityksen, mukaan lukien uuden kansikuvan.
     * 
     * @param vinyl     lomakkeelta bindattu Vinyl-objekti, sisältäen päivitettävät kentät
     * @param coverFile MultipartFile, mahdollinen uusi kansikuva
     * @return uudelleenohjaus listaussivulle "/vinyllist"
     */

     @PostMapping("/updatevinyl")
     public String updateVinyl(@ModelAttribute Vinyl vinyl,
                               @RequestParam("coverFile") MultipartFile coverFile) throws IOException {
         if (!coverFile.isEmpty()) {
             String filename = storageService.storeCover(coverFile);
             vinyl.setCoverFilename(filename);
         }
     
         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         AppUser me = userRepo.findByUsername(auth.getName());
         vinyl.setOwner(me);
     
         vinylRepository.save(vinyl);
         return "redirect:/vinyllist";
     }

}
