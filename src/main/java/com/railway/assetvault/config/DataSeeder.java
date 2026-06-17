package com.railway.assetvault.config;

import com.railway.assetvault.entity.*;
import com.railway.assetvault.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired RoleRepository roleRepository;
    @Autowired UserRepository userRepository;
    @Autowired PasswordEncoder encoder;
    @Autowired ZoneRepository zoneRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role(RoleName.ROLE_SUPER_ADMIN));
            roleRepository.save(new Role(RoleName.ROLE_ADMIN));
            roleRepository.save(new Role(RoleName.ROLE_EMPLOYEE));
            roleRepository.save(new Role(RoleName.ROLE_AUDITOR));
        }

        if (zoneRepository.count() == 0) {
            Zone zone = new Zone();
            zone.setName("Northern Railway");
            zone.setCode("NR");
            zoneRepository.save(zone);
        }

        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setEmployeeId("EMP001");
            admin.setName("System Admin");
            admin.setEmail("admin@railway.gov");
            admin.setPassword(encoder.encode("password"));
            admin.setPhone("1234567890");
            admin.setDesignation("Chief Admin");
            admin.setActive(true);
            
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName(RoleName.ROLE_ADMIN).get());
            admin.setRoles(roles);
            
            userRepository.save(admin);

            // Seed Employees
            User emp1 = new User();
            emp1.setEmployeeId("EMP101");
            emp1.setName("Ravi Kumar");
            emp1.setEmail("ravi.kumar@railway.gov");
            emp1.setPassword(encoder.encode("password"));
            emp1.setPhone("9876543210");
            emp1.setDesignation("Loco Pilot");
            emp1.setActive(true);
            Set<Role> empRoles1 = new HashSet<>();
            empRoles1.add(roleRepository.findByName(RoleName.ROLE_EMPLOYEE).get());
            emp1.setRoles(empRoles1);
            userRepository.save(emp1);

            User emp2 = new User();
            emp2.setEmployeeId("EMP102");
            emp2.setName("Sunita Sharma");
            emp2.setEmail("sunita.s@railway.gov");
            emp2.setPassword(encoder.encode("password"));
            emp2.setPhone("9876543211");
            emp2.setDesignation("Station Master");
            emp2.setActive(true);
            Set<Role> empRoles2 = new HashSet<>();
            empRoles2.add(roleRepository.findByName(RoleName.ROLE_EMPLOYEE).get());
            emp2.setRoles(empRoles2);
            userRepository.save(emp2);

            User emp3 = new User();
            emp3.setEmployeeId("EMP103");
            emp3.setName("Anil Desai");
            emp3.setEmail("anil.d@railway.gov");
            emp3.setPassword(encoder.encode("password"));
            emp3.setPhone("9876543212");
            emp3.setDesignation("Track Inspector");
            emp3.setActive(true);
            Set<Role> empRoles3 = new HashSet<>();
            empRoles3.add(roleRepository.findByName(RoleName.ROLE_EMPLOYEE).get());
            emp3.setRoles(empRoles3);
            userRepository.save(emp3);
        }
    }
}
