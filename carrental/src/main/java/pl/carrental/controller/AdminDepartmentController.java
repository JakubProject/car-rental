package pl.carrental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.carrental.controller.validator.DepartmentValidator;
import pl.carrental.domain.Department;
import pl.carrental.service.DepartmentService;

import java.util.Objects;

@Controller
@RequestMapping("/admin/departments")
public class AdminDepartmentController {

    private final DepartmentService departmentService;
    private final DepartmentValidator departmentValidator;
    public AdminDepartmentController(DepartmentService departmentService, DepartmentValidator departmentValidator) {
        this.departmentService = departmentService;
        this.departmentValidator = departmentValidator;
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
    public String edit(@PathVariable Long id, Model model) {
        try {
            Department department = departmentService.getById(id);
            model.addAttribute("department", department);

        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "errorPage";
        }

        return "adminEditDepartmentPage";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Department department, Model model, BindingResult bindingResult) {
        try{
            departmentValidator.validate(department, bindingResult);

            if (bindingResult.hasErrors()) {
                model.addAttribute("departments", departmentService.getAll());
                if(Objects.nonNull(department.getId())){
                    return "adminEditDepartmentPage";
                }
                return "adminAddDepartmentPage";
            }
            departmentService.save(department);
            model.addAttribute("movies", departmentService.getAll());
        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "errorPage";
        }
        return "redirect:/admin/departments/";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable Long id, Model model) {
        try{
            departmentService.remove(id);
        } catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "errorPage";
        }
        return "redirect:/admin/departments/";
    }
}
