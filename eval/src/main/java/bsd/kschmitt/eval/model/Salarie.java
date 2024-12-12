package bsd.kschmitt.eval.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
public class Salarie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 10)
    @Size(min = 3, max = 10)
    private String matricule;

    @Column(nullable = false)
    private String codeBarre;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getCodeBarre() {
        return codeBarre;
    }

    public void setCodeBarre(String codeBarre) {
        this.codeBarre = codeBarre;
    }

    public Convention getConvention() {
        return convention;
    }

    public void setConvention(Convention convention) {
        this.convention = convention;
    }

    @ManyToOne
    @JoinColumn(name = "convention_id", nullable = false)
    private Convention convention;


}
