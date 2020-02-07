package app.web.controllers;

import app.services.models.hero.HeroServiceModel;
import app.services.models.user.ProfileUserServiceModel;
import app.services.services.HeroService;
import app.services.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    private final HeroService heroService;
    private final UserService userService;

    public HomeController(HeroService heroService, UserService userService) {
        this.heroService = heroService;
        this.userService = userService;
    }

    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") != null) {
            modelAndView.setViewName("redirect:/home");
        } else {
            modelAndView.setViewName("home/index.html");
        }
        return modelAndView;
    }

    @GetMapping("/home")
    public ModelAndView home(ModelAndView modelAndView, Principal principal, HttpSession session) {
        try {
            ProfileUserServiceModel user = userService.getUserByUsername(principal.getName());
            session.setAttribute("username", user.getUsername());
            if (user.getHeroName() != null) {
                session.setAttribute("heroName", user.getHeroName());
                session.setAttribute("heroId", user.getHeroId());
                session.setAttribute("heroGender", user.getHeroGender());
                List<HeroServiceModel> heroFight = heroService.getAllHeroExcept(user.getHeroName());
                modelAndView.addObject("heroFight", heroFight);
            }
        } catch (NullPointerException e) {
            // TODO: 1/23/2020
        }

        modelAndView.setViewName("home/home.html");
        return modelAndView;
    }
}
