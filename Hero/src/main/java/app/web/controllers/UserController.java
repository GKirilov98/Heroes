package app.web.controllers;

import app.services.models.user.ProfileUserServiceModel;
import app.services.services.HeroService;
import app.services.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ModelAndView profile(ModelAndView modelAndView, HttpSession session){
        String username = session.getAttribute("username").toString();
        ProfileUserServiceModel profileUserServiceModel = this.userService.getUserByUsername(username);
        modelAndView.addObject("userProfile", profileUserServiceModel);
        modelAndView.setViewName("users/profile");
        return modelAndView;
    }
}
