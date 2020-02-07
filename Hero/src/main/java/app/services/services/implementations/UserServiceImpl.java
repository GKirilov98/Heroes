package app.services.services.implementations;

import app.data.models.User;
import app.data.repositories.UserRepository;
import app.services.models.user.ProfileUserServiceModel;
import app.services.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProfileUserServiceModel getUserByUsername(String username) {
        User user = this.userRepository.findUserByUsername(username);
        ProfileUserServiceModel pusm = this.modelMapper.map(user, ProfileUserServiceModel.class);
        return pusm;
    }
}
