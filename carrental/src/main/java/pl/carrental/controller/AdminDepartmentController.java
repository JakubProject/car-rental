package pl.carrental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.carrental.domain.Department;
import pl.carrental.service.CarService;
import pl.carrental.service.DepartmentService;

@Controller
@RequestMapping("/admin/departments")
public class AdminDepartmentController {

    private final DepartmentService departmentService;

    public AdminDepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @GetMapping("/")
    public String showAll(Model model) {
        model.addAttribute("departments", departmentService.getAll());
        return "adminDepartmentsPage";
    }

    @GetMapping("/add")
    public String add(Model model) {
        Department department = new Department();

        model.addAttribute("department", department);

        return "adminAddDepartmentPage";
    }

    @GetMapping("/edit/{id}")
    public String add(@PathVariable Long id, Model model) {
        try {
            Department department = departmentService.getById(id);
            model.addAttribute("department", department);

        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
        }

        return "adminEditDepartmentPage";
    }

    @PostMapping("/save")
    public String saveMovie(@ModelAttribute Department department, Model model) {
        try{
            departmentService.save(department);
            model.addAttribute("movies", departmentService.getAll());
        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/admin/departments/";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable Long id, Model model) {
        try{
            departmentService.remove(id);
        } catch (Exception e){
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/admin/departments/";
    }
}
