package ba.sum.fsre.ednevnik1.models;

import com.fasterxml.jackson.annotation.JsonTypeId;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;


@Entity

public class user {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue
    private Long id;

    @Size(min=2, max=20, message = "Polje ime mora biti između 3 i 20 znakova")
    @NotBlank(message="Polje ime je obavezno")
    String ime;

    @Size(min=2, max=20, message = "Polje ime mora biti između 3 i 20 znakova")

    @NotBlank(message="Polje prezime je obavezno")
    String prezime;

    @NotBlank(message="Polje email je obavezno")
            @Email(message="Email adresa mora biti ispravnog formata")
    String email;
    @NotBlank(message="Molimo unesite lozinku")
    String lozinka;

    @NotBlank(message="Molimo ponovite lozinku")
    @Transient
    String potvrdaLozinke;

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getPotvrdaLozinke() {
        return potvrdaLozinke;
    }

    public void setPotvrdaLozinke(String potvrdaLozinke) {
        this.potvrdaLozinke = potvrdaLozinke;
    }

    public user() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    Set<Role> roles = new HashSet<>();

    public user(Long id, String ime, String prezime, String email, String lozinka, String potvrdaLozinke) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.lozinka = lozinka;
        this.potvrdaLozinke = potvrdaLozinke;
        roles.add(Role.STUDENT);
    }
    @AssertTrue(message = "Lozinke se moraju podudarati")
    public boolean isPasswordEqual(){
        try{
            return this.lozinka.equals(this.potvrdaLozinke);
        } catch (Exception e){
            return false;
        }
    }

}
