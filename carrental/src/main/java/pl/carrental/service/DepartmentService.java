package pl.carrental.service;

import org.springframework.stereotype.Service;
import pl.carrental.constant.ErrorConstant;
import pl.carrental.domain.Car;
import pl.carrental.domain.Department;
import pl.carrental.exception.DepartmentException;
import pl.carrental.repository.CarRepository;
import pl.carrental.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAll(){
        return departmentRepository.findAll();
    }

    public Department getById(Long id) throws DepartmentException {
        Optional<Department> department = departmentRepository.findById(id);
        if(department.isPresent()){
            return department.get();
        }else {
            throw new DepartmentException(ErrorConstant.USER_NOT_FOUND);
        }
    }
    public void save(Department department){
        departmentRepository.save(department);
    }


    public void remove(Long id) throws DepartmentException {
        try{
            departmentRepository.deleteById(id);
        }catch (Exception e){
            throw new DepartmentException(e.getMessage());
        }

    }
}
