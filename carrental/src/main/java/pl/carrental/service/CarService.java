package pl.carrental.service;

import org.springframework.stereotype.Service;
import pl.carrental.constant.ErrorConstant;
import pl.carrental.domain.Car;
import pl.carrental.domain.Department;
import pl.carrental.exception.CarException;
import pl.carrental.exception.DepartmentException;
import pl.carrental.repository.CarRepository;
import pl.carrental.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final DepartmentRepository departmentRepository;
    public CarService(CarRepository carRepository, DepartmentRepository departmentRepository) {
        this.carRepository = carRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<Car> getAll(){
        return carRepository.findAll();
    }

    public void save(Car car) throws DepartmentException {
        Optional<Department> department = departmentRepository.findById(car.getDepartmentId());
        if(department.isEmpty()){
            throw new DepartmentException(ErrorConstant.DEPARTMENT_NOT_FOUND);
        }
        car.setDepartment(department.get());
        carRepository.save(car);
    }

    public Car getById(Long id) throws CarException {
        Optional<Car> car = carRepository.findById(id);
        if(car.isEmpty()){
            throw new CarException(ErrorConstant.CAR_NOT_FOUND);
        }
        return car.get();
    }

    public void remove(Long id) throws CarException {
        try{
            carRepository.deleteById(id);
        }catch (Exception e){
            throw new CarException(e.getMessage());
        }
    }

}
