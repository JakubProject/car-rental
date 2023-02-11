package pl.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.carrental.domain.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {

}
