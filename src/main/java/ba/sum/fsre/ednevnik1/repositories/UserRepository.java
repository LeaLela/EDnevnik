package ba.sum.fsre.ednevnik1.repositories;


import ba.sum.fsre.ednevnik1.models.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<user, Long> {
    @Query("SELECT u FROM user u WHERE u.email = ?1")
    public user findByEmail(String email);
}
