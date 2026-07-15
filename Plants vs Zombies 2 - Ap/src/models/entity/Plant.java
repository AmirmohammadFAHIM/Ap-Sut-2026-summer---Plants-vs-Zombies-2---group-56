package models.entity;

import models.factory.plantSkills.Skill;
import models.games.BaseGame;

import java.util.ArrayList;

public class Plant extends Entity {
    private int Damage;
    private int cost;
    private final float ActionInterval;
    private float t;
    private PlantType plantType;
    private ArrayList<PlantTags> tags;
    private Skill baseSkill;
    private ArrayList<Skill> plantfoodSkill;
    private boolean frozen = false;
    private boolean cat = false;
    private float lifeTime;

    public Plant(float actionInterval) {
        ActionInterval = actionInterval;
    }

    public void boost(){}

    public PlantType getPlantType() {
        return plantType;
    }

    public ArrayList<PlantTags> getTags() {
        return tags;
    }

    public int getDamage() {
        return Damage;
    }

    public void setDamage(int damage) {
        Damage = damage;
    }


    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setPlantType(PlantType plantType) {
        this.plantType = plantType;
    }

    public void setTags(ArrayList<PlantTags> tags) {
        this.tags = tags;
    }

    public Skill getBaseSkill() {
        return baseSkill;
    }

    public void setBaseSkill(Skill baseSkill) {
        this.baseSkill = baseSkill;
    }

    public ArrayList<Skill> getPlantfoodSkill() {
        return plantfoodSkill;
    }

    public void setPlantfoodSkill(ArrayList<Skill> plantfoodSkill) {
        this.plantfoodSkill = plantfoodSkill;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public boolean isCat() {
        return cat;
    }

    public void setCat(boolean cat) {
        this.cat = cat;
    }

    public void update(float delta , BaseGame game){
        if(t <= 0){
            t = ActionInterval;
            baseSkill.do_skill(this , game);
            if(tags.contains(PlantTags.ONCE_USAGE)){
                dispose(game);
            }
        }
        else{
            t -= delta;
        }

        if(lifeTime <= 0 && lifeTime >= -1){
            dispose(game);
        }
        else if(lifeTime > 0) lifeTime -= delta;
    }



    public void dispose(BaseGame game){
        game.getPlants().remove(this);

        /// TO DO: Check for two tags : 1-Explosive , 2-MoveZombies
    }

    public void setPlantFood(boolean plantFood , BaseGame game) {
        for (Skill x : plantfoodSkill){
            x.do_skill(this , game);
        }
    }
}

