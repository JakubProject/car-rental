package pl.carrental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.carrental.domain.Car;
import pl.carrental.service.CarService;

import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {

    private final CarService carService;

    public ClientController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/home")
    public String home(Model model) {
        List<Car> cars = carService.getAll();
        model.addAttribute("cars", cars);
        return "clientPage";
    }

}
