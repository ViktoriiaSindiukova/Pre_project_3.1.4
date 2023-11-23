package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.entitis.Role;
import ru.kata.spring.boot_security.demo.repositorys.RoleRepository;

import java.util.List;

public interface RoleService {
    public List<Role> findAll();

    void saveRole(Role role);

    Role getRoleById(int id);
}
