package pl.carrental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.carrental.repository.StatusRepository;
import pl.carrental.service.CarRentService;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CarRentService carRentService;
    private final StatusRepository statusRepository;
    public AdminController(CarRentService carRentService, StatusRepository statusRepository) {
        this.carRentService = carRentService;
        this.statusRepository = statusRepository;
    }

    @GetMapping("/home")
    public String registration(Model model) {
        model.addAttribute("carRents", carRentService.findAll());
        return "adminPage";
    }

    @GetMapping("/rent/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("statuses", statusRepository.findAll());
        model.addAttribute("id", id);
        return "adminEditRentPage";
    }

    @PostMapping("/rent/edit")
    public String edit(@RequestParam Long id, @RequestParam String status, Model model){
        try{
            carRentService.editStatus(id, status);
        }catch (Exception e){
           model.addAttribute("error", e.getMessage());
        }
        return "redirect:/admin/home/";

    }
}
