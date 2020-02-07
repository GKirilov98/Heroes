package app.services.services.implementations;

import app.data.models.Hero;
import app.data.models.Item;
import app.data.models.Slot;
import app.data.models.base.BaseEntity;
import app.data.repositories.HeroRepository;
import app.data.repositories.ItemRepository;
import app.errors.HeroNotFoundException;
import app.services.models.item.ItemServiceModel;
import app.services.services.ItemService;
import app.web.models.item.CreateItemControlModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final HeroRepository heroRepository;
    private final ModelMapper modelMapper;

    public ItemServiceImpl(ItemRepository itemRepository, HeroRepository heroRepository, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.heroRepository = heroRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createItem(CreateItemControlModel model) {
        if (model.getName().trim().length() == 0) {
            throw new IllegalArgumentException("Item name is empty!");
        } else if (this.itemRepository.existsItemByName(model.getName())) {
            throw new IllegalArgumentException("Item name is already exist");
        }

        Item item = this.modelMapper.map(model, Item.class);
        item.setSlot(Slot.valueOf(model.getSlot().toUpperCase()));
        this.itemRepository.save(item);
    }

    @Override
    public List<ItemServiceModel> findAll() {
        return this.itemRepository
                .findAll()
                .stream()
                .map(i -> {
                    ItemServiceModel itemServiceModel = this.modelMapper.map(i, ItemServiceModel.class);
                    i.getHeroes().forEach(h -> itemServiceModel.getHeroesId().add(h.getId()));
                    return itemServiceModel;
                })
                .collect(Collectors.toList());

    }

    @Override
    public void addItemToHero(String itemName, String heroId) {
        Item item = this.itemRepository.findItemByName(itemName);
        Hero hero = this.heroRepository.findById(heroId).orElse(null);
        hero.getItems().add(item);
        hero.setAttack(item.getAttack() + hero.getAttack());
        hero.setDefence(item.getDefence() + hero.getDefence());
        hero.setStrength(item.getStrength() + hero.getStrength());
        hero.setStamina(item.getStamina() + hero.getStamina());
        this.heroRepository.saveAndFlush(hero);
        item.getHeroes().add(hero);
        this.itemRepository.saveAndFlush(item);
    }

    @Override
    public List<ItemServiceModel> findMerchant(String heroId) {
        return this.findAll()
                .stream()
                .peek(i -> {
                    if (i.getHeroesId().contains(heroId)) {
                        i.setBought(true);
                    } else {
                        i.setBought(false);
                    }
                })
                .collect(Collectors.toList());
    }
}
