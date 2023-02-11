package pl.carrental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.carrental.controller.validator.CarValidator;
import pl.carrental.domain.Car;
import pl.carrental.exception.CarException;
import pl.carrental.exception.DepartmentException;
import pl.carrental.service.CarService;
import pl.carrental.service.DepartmentService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
@RequestMapping("/admin/cars")
public class AdminCarController {

    private final CarService carService;
    private final DepartmentService departmentService;
    private final CarValidator carValidator;
    public AdminCarController(CarService carService, DepartmentService departmentService, CarValidator carValidator) {
        this.carService = carService;
        this.departmentService = departmentService;
        this.carValidator = carValidator;
    }


    @GetMapping("/")
    public String showAll(Model model) {
        model.addAttribute("cars", carService.getAll());
        return "adminCarsPage";
    }

    @GetMapping("/add")
    public String add(Model model) {
        Car car = new Car();

        model.addAttribute("car", car);
        model.addAttribute("departments", departmentService.getAll());
        return "adminAddCarPage";
    }

    @GetMapping("/edit/{id}")
    public String add(@PathVariable Long id, Model model) {
        try {
            Car car = carService.getById(id);
            model.addAttribute("car", car);
            model.addAttribute("departments", departmentService.getAll());
        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "errorPage";
        }

        return "adminEditCarPage";
    }

    @PostMapping("/save")
    public String saveMovie(@ModelAttribute Car car, Model model, BindingResult bindingResult) throws DepartmentException {
        try{
            carValidator.validate(car, bindingResult);

            if (bindingResult.hasErrors()) {
                model.addAttribute("departments", departmentService.getAll());
                if(Objects.nonNull(car.getId())){
                    return "adminEditCarPage";
                }
                return "adminAddCarPage";
            }
                carService.save(car);
                model.addAttribute("cars", carService.getAll());
        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/admin/cars/";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable Long id, Model model) throws CarException {
        try{
        carService.remove(id);
        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "errorPage";
        }
        return "redirect:/admin/cars/";
    }
}
