package app.services.models.item;

import java.util.HashSet;
import java.util.Set;

public class ItemServiceModel {
    private String name;
    private String slot;
    private int stamina;
    private int strength;
    private int attack;
    private int defence;
    private boolean bought;
    private Set<String> heroesId;

    public ItemServiceModel() {
        this.heroesId = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
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

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

    public Set<String> getHeroesId() {
        return heroesId;
    }

    public void setHeroesId(Set<String> heroesId) {
        this.heroesId = heroesId;
    }
}
