package com.railway.assetvault.controller;

import com.railway.assetvault.dto.request.EmployeeRequest;
import com.railway.assetvault.dto.response.EmployeeDTO;
import com.railway.assetvault.entity.Role;
import com.railway.assetvault.entity.RoleName;
import com.railway.assetvault.entity.User;
import com.railway.assetvault.repository.RoleRepository;
import com.railway.assetvault.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRoles().stream().anyMatch(r -> r.getName() == RoleName.ROLE_EMPLOYEE))
                .map(EmployeeDTO::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public EmployeeDTO addEmployee(@RequestBody EmployeeRequest request) {
        User user = new User();
        user.setEmployeeId("EMP-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());
        user.setName(request.name);
        user.setEmail(request.email);
        user.setPhone(request.phone);
        user.setDesignation(request.designation);
        user.setPassword(passwordEncoder.encode("password")); // Default password
        user.setActive(true);

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(RoleName.ROLE_EMPLOYEE).get());
        user.setRoles(roles);

        user = userRepository.save(user);
        return new EmployeeDTO(user);
    }
}
