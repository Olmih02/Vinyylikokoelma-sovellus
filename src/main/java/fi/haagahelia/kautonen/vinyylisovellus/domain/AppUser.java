package fi.haagahelia.kautonen.vinyylisovellus.domain;

import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name = "app_user")

public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String role;

    @OneToMany(mappedBy = "owner")
    private List<Vinyl> vinyls;

    public AppUser(Long id, String username, String password, List<Vinyl> vinyls) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.vinyls = vinyls;
    }

    public AppUser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Vinyl> getVinyls() {
        return vinyls;
    }

    public void setVinyls(List<Vinyl> vinyls) {
        this.vinyls = vinyls;
    }

    public String getRole() {
        return role; // Getter for the role field
    }

    public void setRole(String role) {
        this.role = role; // Setter for the role field
    }


    
 





}
