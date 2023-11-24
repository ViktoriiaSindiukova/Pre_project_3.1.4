package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entitis.Role;
import ru.kata.spring.boot_security.demo.repositorys.RoleRepository;
import ru.kata.spring.boot_security.demo.utill.UserNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService{
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role getRoleById(int id) {
        return roleRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Role not found"));
    }
}
