package app.services.services.implementations;

import app.data.models.Gender;
import app.data.models.Hero;
import app.data.repositories.HeroRepository;
import app.data.repositories.UserRepository;
import app.errors.HeroNotFoundException;
import app.services.models.hero.CreateHeroServiceModel;
import app.services.models.hero.DetailsHeroServiceModel;
import app.services.models.hero.HeroServiceModel;
import app.services.services.HeroService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HeroServiceImpl implements HeroService {
    private final UserRepository userRepository;
    private final HeroRepository heroRepository;
    private final ModelMapper modelMapper;

    public HeroServiceImpl(UserRepository userRepository, HeroRepository heroRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.heroRepository = heroRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public String createHero(CreateHeroServiceModel heroServiceModel) {
        if (heroServiceModel.getName().length() == 0 || heroServiceModel.getGender().length() == 0) {
            throw new IllegalArgumentException("Invalid name or not selected gender!");
        }

        boolean heroExist = (this.heroRepository
                .findHeroByName(heroServiceModel.getName()).orElse(null)) != null;
        if (heroExist) {
            throw new IllegalArgumentException("Hero name is already taken!");
        }

        Hero hero = this.modelMapper.map(heroServiceModel, Hero.class);
        hero.setGender(Gender.valueOf(heroServiceModel.getGender().toUpperCase()));
        hero.setUser(this.userRepository.findUserByUsername(heroServiceModel.getUsername()));
        Hero save = this.heroRepository.save(hero);
        return save.getId();
    }

    @Override
    public DetailsHeroServiceModel findHeroByName(String name) {
        Hero hero = this.heroRepository
                .findHeroByName(name)
                .orElseThrow(() -> new HeroNotFoundException("Hero doesn't exist!"));

        DetailsHeroServiceModel detailsHero = this.modelMapper.map(hero, DetailsHeroServiceModel.class);
        List<String> itemList = hero.getItems().stream().map(i -> i.getSlot().name().toLowerCase()).collect(Collectors.toList());
        detailsHero.setItemsSlot(itemList);
        return detailsHero;
    }

    @Override
    public HeroServiceModel findHeroById(String heroId) {
        Hero hero = this.heroRepository.findById(heroId).orElse(null);
        if (hero == null) {
            throw new NullPointerException("Hero with this id doesn't exist!");
        }

        HeroServiceModel heroServiceModel = this.modelMapper.map(hero, HeroServiceModel.class);
        List<String> itemList = hero.getItems().stream().map(i -> i.getSlot().name()).collect(Collectors.toList());
        heroServiceModel.setItems(itemList);
        return heroServiceModel;
    }

    @Override
    public List<HeroServiceModel> getAllHeroExcept(String heroName) {

        return this.heroRepository.findAll().stream().filter(h -> !h.getName().equals(heroName))
                .map(h -> this.modelMapper.map(h, HeroServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void increaseWinnerFight(String name) {
        Hero hero = this.heroRepository.findHeroByName(name)
                .orElseThrow(() -> new HeroNotFoundException("Hero doesn't exist!"));
        hero.setLevel(hero.getLevel() + 1);
        hero.setStamina(hero.getStamina() + 5);
        hero.setStrength(hero.getStrength() + 5);

        this.heroRepository.saveAndFlush(hero);
    }
}
