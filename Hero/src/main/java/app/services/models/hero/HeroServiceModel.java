package app.services.models.hero;

import app.services.services.ItemService;

import java.util.ArrayList;
import java.util.List;

public class HeroServiceModel {
    private String id;
    private String name;
    private String gender;
    private List<String> items;

    public HeroServiceModel() {
        items = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}
