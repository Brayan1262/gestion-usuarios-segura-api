package com.brayan.security.config;

import com.brayan.security.entity.Role;
import com.brayan.security.entity.RoleName;
import com.brayan.security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Objects;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Autowired
    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        createRoleIfNotExists(RoleName.ADMIN, "Administrador del sistema");
        createRoleIfNotExists(RoleName.USER, "Usuario general");
        createRoleIfNotExists(RoleName.SUPPORT, "Soporte técnico");
    }

    private void createRoleIfNotExists(RoleName name, String description) {
        if (!roleRepository.existsByName(name)) {
                Role role = Role.builder()
                    .name(name)
                    .description(description)
                    .build();
                roleRepository.save(Objects.requireNonNull(role));
        }
    }
}
