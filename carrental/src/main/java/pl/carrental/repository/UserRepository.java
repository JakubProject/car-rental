package pl.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.carrental.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT user FROM User user WHERE user.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

    @Query(value = "SELECT user FROM User user WHERE user.email = :email")
    Optional<User> findByEmail(@Param("email") String email);



}
