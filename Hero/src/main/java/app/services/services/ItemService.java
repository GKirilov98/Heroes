package app.services.services;

import app.services.models.item.ItemServiceModel;
import app.web.models.item.CreateItemControlModel;

import java.util.List;

public interface ItemService {
    void createItem(CreateItemControlModel model);
    List<ItemServiceModel> findAll();

    void addItemToHero(String itemName, String heroId);

    List<ItemServiceModel> findMerchant(String heroId);
}
