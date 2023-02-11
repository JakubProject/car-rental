package pl.carrental.service;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.carrental.domain.Role;
import pl.carrental.domain.Status;
import pl.carrental.domain.User;
import pl.carrental.repository.RoleRepository;
import pl.carrental.repository.StatusRepository;
import pl.carrental.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class InitService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StatusRepository statusRepository;
    public InitService(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, StatusRepository statusRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.statusRepository = statusRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        statusRepository.deleteAll();
        Status status1 = new Status();
        status1.setId(1L);
        status1.setName("NOWE ZGŁOSZENIE");
        Status status2 = new Status();
        status2.setId(2L);
        status2.setName("POTWIERDZONE");
        Status status3 = new Status();
        status3.setId(3L);
        status3.setName("ANULOWANE ");

        statusRepository.saveAll(List.of(status1, status2, status3));

        Optional<User> admin = userRepository.findByUsername("admin");
        if(admin.isEmpty()){
            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setCreatedDate(LocalDateTime.now());
            user.setEmail("admin@carrental.com");
            user.setActive(true);

            Optional<Role> roleAdmin = roleRepository.findByName("ADMIN");
            if(roleAdmin.isEmpty()){
                Role role = new Role();
                role.setName("ADMIN");
                role.setDescription("Default role admin");
                Role savedRole = roleRepository.save(role);
                user.addRoles(savedRole);
            }else {
                user.addRoles(roleAdmin.get());
            }

            userRepository.save(user);
        }
    }
}
