package pl.carrental.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.carrental.controller.validator.UserValidator;
import pl.carrental.domain.User;
import pl.carrental.service.SecurityService;
import pl.carrental.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserController {

    private UserService userService;
    private SecurityService securityService;
    private UserValidator userValidator;

    public UserController(UserService userService, SecurityService securityService, UserValidator userValidator) {
        super();
        this.userService = userService;
        this.securityService = securityService;
        this.userValidator = userValidator;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }

        model.addAttribute("userForm", new User());

        return "registrationForm";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registrationForm";
        }
        try{
            userService.save(userForm);
        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "registrationForm";
        }

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }

        if (error != null)
            model.addAttribute("error", "Login lub hasło nieprawidłowe.");

        if (logout != null)
            model.addAttribute("message", "Wylogowano poprawnie.");

        return "loginForm";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    @GetMapping("/success")
    public String loginPageRedirect(HttpServletRequest request, HttpServletResponse response, Authentication authResult) {

        String role = authResult.getAuthorities().toString();

        if (role.contains("ADMIN")) {
            return "redirect:/admin/home";
        } else if (role.contains("USER")) {
            return "redirect:/user/home";
        } else {
            return "redirect:/login";
        }
    }
}
