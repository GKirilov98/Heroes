package app.services.services;

import app.services.models.auth.LoginUserServiceModel;
import app.services.models.auth.RegisterUserServiceModel;
import app.web.models.auth.LoginUserFormModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService  extends UserDetailsService {
    void register(RegisterUserServiceModel model);

    LoginUserServiceModel login(LoginUserFormModel model);
}
