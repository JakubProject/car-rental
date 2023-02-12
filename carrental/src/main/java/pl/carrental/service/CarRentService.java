package pl.carrental.service;

import org.springframework.stereotype.Service;
import pl.carrental.constant.ErrorConstant;
import pl.carrental.domain.CarRent;
import pl.carrental.exception.CarRentException;
import pl.carrental.repository.CarRentRepository;
import pl.carrental.repository.CarRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class CarRentService {

    private final CarRentRepository carRentRepository;

    public CarRentService(CarRentRepository carRentRepository) {
        this.carRentRepository = carRentRepository;
    }

    public List<CarRent> findByUserId(Long userId) {
        return carRentRepository.findByUserId(userId);
    }

    public List<CarRent> findAll() {
        return carRentRepository.findAll();
    }

    public void save(CarRent carRent) throws CarRentException {
        LocalDate today = LocalDate.now();
        boolean isInvalidDate = today.isAfter(carRent.getStartDate()) || carRent.getStartDate().isAfter(carRent.getEndDate());
        if (isInvalidDate) {
            throw new CarRentException(ErrorConstant.BAD_RANGE);
        }
        long until = carRent.getStartDate().until(carRent.getEndDate(), ChronoUnit.DAYS);
        carRent.setTotalCost(carRent.getCar().getPerDay().multiply(BigDecimal.valueOf(until)));
        carRent.setStatus("NOWE ZGŁOSZENIE");
        carRentRepository.save(carRent);
    }

    public void editStatus(Long id, String status) throws CarRentException {
        Optional<CarRent> rent = carRentRepository.findById(id);
        if(rent.isEmpty()){
            throw new CarRentException(ErrorConstant.CAR_RENT_NOT_FOUND);
        }
        CarRent carRent = rent.get();
        carRent.setStatus(status);
        carRentRepository.save(carRent);
    }

}
