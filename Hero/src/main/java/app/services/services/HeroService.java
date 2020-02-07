package app.services.services;

import app.services.models.hero.CreateHeroServiceModel;
import app.services.models.hero.DetailsHeroServiceModel;
import app.services.models.hero.HeroServiceModel;

import java.util.List;

public interface HeroService {
    String createHero(CreateHeroServiceModel hero);

    DetailsHeroServiceModel findHeroByName(String name) ;

    HeroServiceModel findHeroById(String heroId);

    List<HeroServiceModel> getAllHeroExcept(String heroName);

    void increaseWinnerFight(String name);
}
