package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.entitis.User;

import java.util.List;
import java.util.Optional;

public interface UserService{

    public User findByName(String name);

    public List<User> getAllUsers();

    public User findUserById(Long id);

    public void saveUser(User user);

    public void updateUser(User user, Long id);

    public void deleteUser(Long id);
}
