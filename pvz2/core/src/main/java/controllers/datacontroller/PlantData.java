package controllers.datacontroller;

import models.entity.PlantTags;

import java.io.Serializable;
import java.util.ArrayList;

public class PlantData implements Serializable {
    private int id;
    private String name;
    private float actionInterval;
    private float hp;
    private float cost;
    private ArrayList<PlantTags> tags;
    private float recharge;
    private ArrayList<Upgrade>  upgrades;
    private float damage;

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public ArrayList<Upgrade> getUpgrades() {
        return upgrades;
    }

    public void setUpgrades(ArrayList<Upgrade> upgrades) {
        this.upgrades = upgrades;
    }

    public float getRecharge() {
        return recharge;
    }

    public void setRecharge(float recharge) {
        this.recharge = recharge;
    }

    public ArrayList<PlantTags> getTags() {
        return tags;
    }

    public void setTags(ArrayList<PlantTags> tags) {
        this.tags = tags;
    }

    public float getActionInterval() {
        return actionInterval;
    }

    public void setActionInterval(float actionInterval) {
        this.actionInterval = actionInterval;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }
}
