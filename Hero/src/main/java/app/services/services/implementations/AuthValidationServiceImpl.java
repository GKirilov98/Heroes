package app.services.services.implementations;

import app.data.repositories.UserRepository;
import app.services.models.auth.RegisterUserServiceModel;
import app.services.services.AuthValidationService;
import org.springframework.stereotype.Service;

@Service
public class AuthValidationServiceImpl implements AuthValidationService {
    private final UserRepository usersRepository;

    public AuthValidationServiceImpl(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public boolean isValid(RegisterUserServiceModel user) {
        return this.isEmailValid(user.getEmail()) &&
                this.arePasswordValid(user.getPassword(), user.getConfirmPassword()) &&
                this.isUsernameFree(user.getUsername());
    }

    private boolean isUsernameFree(String username) {
        return !this.usersRepository.existsUserByUsername(username);
    }

    private boolean arePasswordValid(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    private boolean isEmailValid(String email) {
        // TODO: 11/19/2019
        return true;
    }
}
