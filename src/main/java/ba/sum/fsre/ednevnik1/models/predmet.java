package ba.sum.fsre.ednevnik1.models;

import jakarta.persistence.*;

@Entity
@Table(name="predmeti")
public class predmet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "predmet_id")
    private Long predmetId;

    @Column(name="naziv")
    private String naziv;

    @ManyToOne
    @JoinColumn(name = "profesor_id")
    private profesor profesor;
}
