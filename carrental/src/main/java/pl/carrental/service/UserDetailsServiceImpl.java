package pl.carrental.service;

import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;


import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.carrental.constant.ErrorConstant;
import pl.carrental.repository.UserRepository;


@Service
@Transactional
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {



    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        return userRepository.findByUsername(username)
                .map(user -> new User(user.getUsername(), user.getPassword(),
                        user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
                                .collect(Collectors.toList())))
                .orElseThrow(() -> new EntityExistsException(ErrorConstant.USER_NOT_FOUND));
    }

}
