package pl.carrental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.carrental.domain.Car;
import pl.carrental.domain.CarRent;
import pl.carrental.domain.User;
import pl.carrental.service.CarRentService;
import pl.carrental.service.CarService;
import pl.carrental.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {

    private final CarService carService;
    private final UserService userService;
    private final CarRentService carRentService;
    public ClientController(CarService carService, UserService userService, CarRentService carRentService) {
        this.carService = carService;
        this.userService = userService;
        this.carRentService = carRentService;
    }

    @GetMapping("/home")
    public String home(Model model) {
        List<Car> cars = carService.getAll();
        model.addAttribute("cars", cars);
        return "clientPage";
    }

    @GetMapping("/rent/{id}")
    public String rent(@PathVariable Long id, Model model, Principal principal){
        try{
            Car car = carService.getById(id);
            String username = principal.getName();
            User user = userService.findByUsername(username);
            CarRent rent = new CarRent();
            rent.setCar(car);
            rent.setUser(user);
            model.addAttribute("car", car);
            model.addAttribute("rent", rent);
        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "errorPage";
        }
        return "carRentPage";
    }

    @PostMapping("/rent")
    public String rent(@ModelAttribute("rent") CarRent carRent, Model model){
        try{
            carRentService.save(carRent);
        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "errorPage";
        }

        return "redirect:/client/home/";
    }

    @GetMapping("/rent/history")
    public String history(Model model, Principal principal){
        try{
            String username = principal.getName();
            User user = userService.findByUsername(username);
            List<CarRent> carRents = carRentService.findByUserId(user.getId());
            model.addAttribute("carRents", carRents);
        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "errorPage";
        }
        return "carHistoryRentPage";
    }

}
