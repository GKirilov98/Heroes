package app.web.controllers;

import app.errors.HeroNotFoundException;
import app.services.models.hero.CreateHeroServiceModel;
import app.services.models.hero.DetailsHeroServiceModel;
import app.services.services.HeroService;
import app.services.services.UserService;
import app.web.models.hero.CreateHeroFormModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/heroes")
public class HeroController {
    private HeroService heroService;
    private UserService userService;
    private ModelMapper modelMapper;

    public HeroController(HeroService heroService, UserService userService, ModelMapper modelMapper) {
        this.heroService = heroService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/create")
    public ModelAndView createHero(ModelAndView modelAndView) {
        modelAndView.setViewName("heroes/create-hero.html");
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createHeroConfirm(@ModelAttribute CreateHeroFormModel model, ModelAndView modelAndView, HttpSession session) {
        CreateHeroServiceModel heroServiceModel = this.modelMapper.map(model, CreateHeroServiceModel.class);
        heroServiceModel.setUsername(session.getAttribute("username").toString());
        try {
            String heroId = this.heroService.createHero(heroServiceModel);
            session.setAttribute("heroName", model.getName());
            session.setAttribute("heroId", heroId);
            modelAndView.setViewName("redirect:/heroes/details/" + model.getName());
        } catch (IllegalArgumentException e) {
            modelAndView.setViewName("redirect:/heroes/create");
        }

        return modelAndView;
    }

    @GetMapping("/details/{name}")
    public ModelAndView heroDetails(@PathVariable String name, ModelAndView modelAndView) {
        DetailsHeroServiceModel detailsHero = this.heroService.findHeroByName(name);
        modelAndView.addObject("detailsHero", detailsHero);
        modelAndView.setViewName("heroes/hero-details");
        return modelAndView;
    }

    @GetMapping("/fight/{name}")
    public ModelAndView fight(@PathVariable String name, ModelAndView modelAndView, HttpSession session) {
        DetailsHeroServiceModel attackHero = this.heroService.findHeroByName(session.getAttribute("heroName").toString());
        DetailsHeroServiceModel defenceHero = this.heroService.findHeroByName(name);

        //heroAttack + heroStrength * 4 – opponentDefence + oponnentStamina * 2
        int figth = (attackHero.getAttack() + attackHero.getStrength() * 4) -
                    (defenceHero.getDefence() + defenceHero.getStamina() * 2);
        if (figth > 0){
            this.heroService.increaseWinnerFight(attackHero.getName());
            modelAndView.addObject("winner", attackHero.getName() + " - winner");
        } else if ( figth < 0){
            this.heroService.increaseWinnerFight(defenceHero.getName());
            modelAndView.addObject("winner", defenceHero.getName() + " - winner");
        } else {
            modelAndView.addObject("winner", "DRAW");
        }

        modelAndView.addObject("attackHero", attackHero);
        modelAndView.addObject("defenceHero", defenceHero);
        modelAndView.setViewName("heroes/fight");
        return modelAndView;
    }



    @ExceptionHandler(HeroNotFoundException.class)
    public ModelAndView handleException ( HeroNotFoundException exception){
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", exception.getMessage());

        return modelAndView;
    }
}
