package app.services.services.implementations;

import app.data.models.User;
import app.data.repositories.RoleRepository;
import app.data.repositories.UserRepository;
import app.services.models.auth.LoginUserServiceModel;
import app.services.models.auth.RegisterUserServiceModel;
import app.services.services.AuthService;
import app.services.services.AuthValidationService;
import app.services.services.RoleService;
import app.web.models.auth.LoginUserFormModel;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private final ModelMapper modelMapper;
    private final UserRepository usersRepository;
    private final AuthValidationService authValidationService;
    private final RoleService roleService;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthServiceImpl(ModelMapper modelMapper, UserRepository usersRepository,
                           AuthValidationService authValidationService, RoleService roleService, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.modelMapper = modelMapper;
        this.usersRepository = usersRepository;
        this.authValidationService = authValidationService;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void register(RegisterUserServiceModel model) {
        if (!this.authValidationService.isValid(model)) {
            throw new IllegalArgumentException("Inputs are not correct or empty!");
        }

        User user = this.modelMapper.map(model, User.class);
        if (this.usersRepository.count() == 0){
            this.roleService.seedRoleInDb();
            user.setAuthorities( new HashSet<>(this.roleRepository.findAll()));
        } else {
            user.setAuthorities(new HashSet<>(Set.of(this.roleRepository.findByAuthority("USER"))));
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        this.usersRepository.saveAndFlush(user);
    }

    @Override
    public LoginUserServiceModel login(LoginUserFormModel model) {
        User user = this.usersRepository.findUserByUsernameAndPassword(model.getUsername(),
                DigestUtils.sha256Hex(model.getPassword())).orElse(null);
        if (user == null){
            throw new IllegalArgumentException("Invalid username or password!");
        }
        return this.modelMapper.map(user, LoginUserServiceModel.class);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.usersRepository.findUserByUsername(username);
    }
}
