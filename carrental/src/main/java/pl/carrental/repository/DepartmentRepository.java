package pl.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.carrental.domain.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
