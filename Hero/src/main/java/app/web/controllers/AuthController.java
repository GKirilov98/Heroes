package app.web.controllers;

import app.services.models.auth.LoginUserServiceModel;
import app.services.models.auth.RegisterUserServiceModel;
import app.services.services.AuthService;
import app.web.models.auth.LoginUserFormModel;
import app.web.models.auth.RegisterUserFormModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class AuthController {
    private final AuthService authService;
    private final ModelMapper modelMapper;

    public AuthController(AuthService authService, ModelMapper modelMapper) {
        this.authService = authService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/register")
    public ModelAndView register(ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") != null) {
            modelAndView.setViewName("redirect:/home");
        } else {
            modelAndView.setViewName("auth/register.html");
        }
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute RegisterUserFormModel model, ModelAndView modelAndView) {
        RegisterUserServiceModel serviceModel = this.modelMapper.map(model, RegisterUserServiceModel.class);
        try {
            this.authService.register(serviceModel);
            modelAndView.setViewName("redirect:/users/login");
        } catch (IllegalArgumentException e){
            modelAndView.setViewName("redirect:/users/register");
        }



        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") != null) {
            modelAndView.setViewName("redirect:/home");
        } else {
            modelAndView.setViewName("auth/login.html");
        }
        return modelAndView;
    }


}


