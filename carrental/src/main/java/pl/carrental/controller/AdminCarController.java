package pl.carrental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.carrental.domain.Car;
import pl.carrental.domain.Department;
import pl.carrental.exception.CarException;
import pl.carrental.exception.DepartmentException;
import pl.carrental.service.CarService;
import pl.carrental.service.DepartmentService;

@Controller
@RequestMapping("/admin/cars")
public class AdminCarController {

    private final CarService carService;
    private final DepartmentService departmentService;
    public AdminCarController(CarService carService, DepartmentService departmentService) {
        this.carService = carService;
        this.departmentService = departmentService;
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
        }

        return "adminEditCarPage";
    }

    @PostMapping("/save")
    public String saveMovie(@ModelAttribute Car car, Model model) throws DepartmentException {
        try{
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
        }
        return "redirect:/admin/cars/";
    }
}
