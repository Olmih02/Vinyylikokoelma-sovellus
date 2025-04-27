package fi.haagahelia.kautonen.vinyylisovellus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Konfiguraatioluokka staattisten resurssien (kansikuvat) servaamiseen
 * ulkoisesta hakemistosta.
 */
@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {


    /**
     * Rekisteröidään resurssikäsittelijä, joka vastaa URL-polkuun
     * /images/covers/** kohdistuvista HTTP-pyynnöistä. Näiden pyyntöjen
     * resurssit haetaan levyasemalta hakemistosta uploads/covers/.
     *
     * @param registry resurssikäsittelijöiden säilö, johon lisätään uusi kartoitus
     */

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Kartoitus URL-polulle /images/covers/**
        registry.addResourceHandler("/images/covers/**")
                .addResourceLocations("file:uploads/covers/");
                // Palvelinlevyllä oleva hakemisto, josta palvelimet noutavat tiedostot
    }




}
