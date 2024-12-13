package bsd.kschmitt.eval.model;

import jakarta.persistence.*;
import org.springframework.cache.interceptor.CacheAspectSupport;

import java.util.List;

@Entity
public class Entreprise {

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public List<Convention> getConventions() {
        return conventions;
    }

    public void setConventions(List<Convention> conventions) {
        this.conventions = conventions;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String nom;

    @OneToMany(mappedBy = "entreprise", cascade = CascadeType.ALL)
    private List<Users> users;

    @OneToMany(mappedBy = "entreprise", cascade = CascadeType.ALL)
    private List<Convention> conventions;
}
