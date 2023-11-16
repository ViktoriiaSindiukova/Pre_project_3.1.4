package ru.kata.spring.boot_security.demo.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entitis.User;
import ru.kata.spring.boot_security.demo.repositorys.UserRepository;
import ru.kata.spring.boot_security.demo.utill.UserNotFoundException;

@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new UserNotFoundException(String.format("User '%s' not found", username)));

        return new UserSecurity(user);
    }
}
