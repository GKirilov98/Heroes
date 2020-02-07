package app.web.controllers;

import app.services.models.item.ItemServiceModel;
import app.services.services.HeroService;
import app.services.services.ItemService;
import app.web.models.item.CreateItemControlModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;
    private final HeroService heroService;
    private final ModelMapper modelMapper;

    public ItemController(ItemService itemService, HeroService heroService, ModelMapper modelMapper) {
        this.itemService = itemService;
        this.heroService = heroService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/create")
    public ModelAndView createItem(ModelAndView modelAndView){
        modelAndView.setViewName("items/create-item.html");
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createItemConfirm(@ModelAttribute CreateItemControlModel model,  ModelAndView modelAndView){

        try{
            this.itemService.createItem(model);
            modelAndView.setViewName("redirect:/items/merchant");
        } catch (IllegalArgumentException e){
            modelAndView.setViewName("redirect:/items/create");
        }

        return modelAndView;
    }

    @GetMapping("/merchant")
    public ModelAndView merchant(ModelAndView modelAndView,  HttpSession session){
        String heroId = session.getAttribute("heroId").toString();
        List<ItemServiceModel> merchantItems  = this.itemService.findMerchant(heroId);
        modelAndView.addObject("itemList", merchantItems);
        modelAndView.setViewName("items/merchant");
        return modelAndView;
    }

    @PostMapping("/merchant/{name}")
    public ModelAndView merchantConfirm(ModelAndView modelAndView, @PathVariable String name, HttpSession session){
        String heroId = session.getAttribute("heroId").toString();
        this.itemService.addItemToHero(name, heroId);
        modelAndView.setViewName("redirect:/items/merchant");
        return modelAndView;
    }
}
