package app.services.services.implementations;

import app.data.models.Role;
import app.data.repositories.RoleRepository;
import app.services.services.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void seedRoleInDb() {
        this.roleRepository.saveAndFlush(new Role("ADMIN"));
        this.roleRepository.saveAndFlush(new Role("USER"));
    }
}
