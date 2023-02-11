package pl.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.carrental.domain.Car;
import pl.carrental.domain.CarRent;

import java.util.List;

@Repository
public interface CarRentRepository extends JpaRepository<CarRent, Long> {

    public List<CarRent> findByUserId(Long userId);
}
