package app.services.services;

import app.services.models.user.ProfileUserServiceModel;

public interface UserService {
    ProfileUserServiceModel getUserByUsername(String username);
}
