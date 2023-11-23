package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entitis.Role;
import ru.kata.spring.boot_security.demo.entitis.User;
import ru.kata.spring.boot_security.demo.repositorys.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = encoder;
    }

    @Override
    @Transactional
    public User findByName(String name) {
        return userRepository.findUserByUsername(name).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        if (findByName(user.getUsername()) != null) {
            throw new RuntimeException("User already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User user, Long id) {
        User userDb = findUserById(id);
        if (userDb == null) {
            throw new UsernameNotFoundException("Incorrect data");
        }

        if (!user.getPassword().equals(userDb.getPassword())) {
            userDb.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userDb.setUsername(user.getUsername());
        userDb.setLastName(user.getLastName());
        userDb.setAge(user.getAge());
        userDb.setEmail(user.getEmail());
        userDb.setPassword(passwordEncoder.encode(user.getPassword()));
        userDb.setRoles(user.getRoles());

        userRepository.flush();
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
