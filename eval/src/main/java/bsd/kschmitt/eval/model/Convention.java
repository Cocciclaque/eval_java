package bsd.kschmitt.eval.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

@Entity
public class Convention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    @PositiveOrZero
    private float subvention;

    @Column(nullable = false)
    @Min(1)
    private int salarieMaximum;

    @ManyToOne
    @JoinColumn(name = "entreprise_id", nullable = false)
    @JsonIgnoreProperties("conventions")
    private Entreprise entreprise;

    @OneToMany(mappedBy = "convention", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("conventions")
    private List<Salarie> salaries;

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

    public float getSubvention() {
        return subvention;
    }

    public void setSubvention(float subvention) {
        this.subvention = subvention;
    }

    public int getSalarieMaximum() {
        return salarieMaximum;
    }

    public void setSalarieMaximum(int salarieMaximum) {
        this.salarieMaximum = salarieMaximum;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public List<Salarie> getSalaries() {
        return salaries;
    }

    public void setSalaries(List<Salarie> salaries) {
        this.salaries = salaries;
    }


}
