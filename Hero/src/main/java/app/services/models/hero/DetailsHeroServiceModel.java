package app.services.models.hero;

import java.util.ArrayList;
import java.util.List;

public class DetailsHeroServiceModel {
    private String name;
    private String gender;
    private int level ;
    private int stamina ;
    private int strength;
    private int attack;
    private int defence;
    private List<String> itemsSlot;

    public DetailsHeroServiceModel() {
        itemsSlot = new ArrayList<>();
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public List<String> getItemsSlot() {
        return itemsSlot;
    }

    public void setItemsSlot(List<String> itemsSlot) {
        this.itemsSlot = itemsSlot;
    }
}

