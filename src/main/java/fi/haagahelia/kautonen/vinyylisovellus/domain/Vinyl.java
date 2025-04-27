package fi.haagahelia.kautonen.vinyylisovellus.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Entity-luokka, joka edustaa vinyylilevyä tietokannassa.
 * Jokainen Vinyl-olio tallentuu taulukkoon, jossa on kentät:
 * id, artist, title, releaseYear, price, extraInfo sekä coverFilename.
 */
@Entity
public class Vinyl {

    /** Autogeneroitu primääriavain */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** Artistin nimi */
    private String artist;

    /** Albumin tai levyn nimi */
    private String title;

    /** Julkaisuvuosi tai vapaatekstinä esim. "Re-issue" */
    private String releaseYear;

    /** Oston hinta euroissa */
    private double price;

    /** Lisätiedot, esim. versio tai muu muistiinpano */
    private String extraInfo;

    /** Tiedostonimi, jonne kansikuva on tallennettu uploads/covers -hakemistoon */
    private String coverFilename;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private AppUser owner;

    /**
     * Oletuskonstruktori JPA:ta varten.
     */
    public Vinyl() {}

    /**
     * Rakentaja, jota voi käyttää ohjelmalliseen olion luontiin ilman kuvaa.
     * 
     * @param artist       artistin nimi
     * @param title        albumin nimi
     * @param releaseYear  julkaisuvuosi tai lisäinfo (esim. "Re-issue")
     * @param price        ostohinta
     * @param extraInfo    vapaa lisätiedot-kenttä
     */
    public Vinyl(String artist, String title, String releaseYear, double price, String extraInfo, AppUser owner) {
        this.artist = artist;
        this.title = title;
        this.releaseYear = releaseYear;
        this.price = price;
        this.extraInfo = extraInfo;
        this.owner = owner;
    }

    public AppUser getOwner() {
        return owner;
    }

    public void setOwner(AppUser owner) {
        this.owner = owner;
    }

    /** @return olion uniikki tunniste */
    public Long getId() {
        return id;
    }

    /** @param id asetettava tunniste */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return artistin nimi */
    public String getArtist() {
        return artist;
    }

    /** @param artist asetettava artistin nimi */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /** @return albumin/levyn nimi */
    public String getTitle() {
        return title;
    }

    /** @param title asetettava nimi */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return julkaisuvuosi tai vapaatekstikentän arvo */
    public String getReleaseYear() {
        return releaseYear;
    }

    /** @param releaseYear asetettava julkaisuvuosi tai lisäinfo */
    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    /** @return ostohinta euroina */
    public double getPrice() {
        return price;
    }

    /** @param price asetettava ostohinta */
    public void setPrice(double price) {
        this.price = price;
    }

    /** @return lisätietokentän arvo */
    public String getExtraInfo() {
        return extraInfo;
    }

    /** @param extraInfo asetettava lisätiedot */
    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    /** @return kansikuvan tiedostonimi */
    public String getCoverFilename() {
        return coverFilename;
    }

    /** @param coverFilename asetettava kansikuvan tiedostonimi */
    public void setCoverFilename(String coverFilename) {
        this.coverFilename = coverFilename;
    }

 
}