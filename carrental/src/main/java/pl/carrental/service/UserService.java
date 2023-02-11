package pl.carrental.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.carrental.constant.ErrorConstant;
import pl.carrental.domain.Role;
import pl.carrental.domain.User;
import pl.carrental.exception.UserException;
import pl.carrental.repository.RoleRepository;
import pl.carrental.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        super();
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void save(User user) throws UserException {
        Optional<User> byUsername = userRepository.findByUsername(user.getUsername());
        if (byUsername.isPresent()) {
            throw new UserException(ErrorConstant.USERNAME_IS_NOT_UNIQUE);
        }

        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        if (byEmail.isPresent()) {
            throw new UserException(ErrorConstant.EMAIL_IS_NOT_UNIQUE);
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setCreatedDate(LocalDateTime.now());
        user.setActive(true);
        Optional<Role> userRole = roleRepository.findByName("USER");
        if (userRole.isEmpty()) {
            Role role = new Role();
            role.setName("USER");
            role.setDescription("Default user role");
            Role savedRole = roleRepository.save(role);
            user.addRoles(savedRole);
        }else{
            user.addRoles(userRole.get());
        }

    userRepository.save(user);
    }

    public User findByUsername(String username) throws UserException {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            return user.get();
        }else{
            throw new UserException(ErrorConstant.USER_NOT_FOUND);
        }
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }
}
