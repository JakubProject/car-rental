package pl.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.carrental.domain.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}
