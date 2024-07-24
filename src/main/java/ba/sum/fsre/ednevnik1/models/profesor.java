package ba.sum.fsre.ednevnik1.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "profesori")
public class profesor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="profesor_id")
    private Long profesorId;

    @Column(name="ime")
    private String ime;

    @Column(name = "prezime")
    private String prezime;

    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL)
    private List<predmet> predmets;

}
