package controllers.dataController;

import models.entity.PlantTags;

import java.util.ArrayList;

public class PlantData {
    private float actionInterval;
    private float hp;
    private ArrayList<PlantTags> tags;
    private float recharge;

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
