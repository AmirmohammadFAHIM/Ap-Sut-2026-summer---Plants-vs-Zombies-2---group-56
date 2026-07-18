package models.entity;

import models.factory.builder.PlantType;
import models.factory.plantSkills.Explosive;
import models.factory.plantSkills.Skill;
import models.factory.plantSkills.skillDatas.ExplosionData;
import models.factory.plantSkills.skillDatas.PlantArmor;
import models.games.BaseGame;

import java.util.ArrayList;

public class Plant extends Entity {
    private int Damage;
    private int cost;
    private final float ActionInterval;
    private float t;
    private PlantCategory category;
    private PlantType type;
    private ArrayList<PlantTags> tags;
    private Skill baseSkill;
    private ArrayList<Skill> plantfoodSkill;
    private boolean frozen = false;
    private boolean cat = false;
    public boolean onLilyPad = false;
    private float lifeTime;
    private int freezeLevel = 0;
    private ArrayList<PlantArmor> armor;

    public ArrayList<PlantArmor> getArmor() {
        return armor;
    }

    public void setArmor(ArrayList<PlantArmor> armor) {
        this.armor = armor;
    }

    public Plant(float actionInterval) {
        ActionInterval = actionInterval;
    }

    public void boost(){}

    public PlantCategory getCategory() {
        return category;
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

    public void setCategory(PlantCategory category) {
        this.category = category;
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

    public float getActionInterval() {
        return ActionInterval;
    }

    public float getT() {
        return t;
    }

    public void setT(float t) {
        this.t = t;
    }

    public PlantType getType() {
        return type;
    }

    public void setType(PlantType type) {
        this.type = type;
    }

    public float getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(float lifeTime) {
        this.lifeTime = lifeTime;
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
           if(Trap(game)) {
               baseSkill.do_skill(this , game);
               if(tags.contains(PlantTags.ONCE_USAGE)){
                   dispose(game);
               }
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

    private boolean Trap(BaseGame game){
        if(!this.tags.contains(PlantTags.TRAP)){
            return true;
        }

        for (Zombie x : game.getZombies()) {
            if(Math.abs(x.getX() - this.x) < 20){
                return true;
            }
        }
        return false;
    }

    public void dispose(BaseGame game){
        game.getPlants_inField().remove(this);

        /// TO DO: Check for two tags : 1-Explosive , 2-MoveZombies: for each in skills , see if theirs disposable or no
    }

    public void setPlantFood(boolean plantFood , BaseGame game) {
        for (Skill x : plantfoodSkill){
            x.do_skill(this , game);
        }
    }


    public int getFreezeLevel() {
        return freezeLevel;
    }

    public void setFreezeLevel(int freezeLevel) {
        if(freezeLevel >= 3) freezeLevel = 3;
        this.freezeLevel = freezeLevel;
    }

    public void setHp(float hp , Zombie eater , BaseGame game){
        this.hp = hp;
        if(hp <= 0){
            dispose(eater , game);
        }
    }

    private void dispose(Zombie eater , BaseGame game){
        if(tags.contains(PlantTags.SHROOM) && tags.contains(PlantTags.MAGICAL)){
            /// TODO: make the zombie an opponent of other zombies
        }
        else if(tags.contains(PlantTags.EXPLOSIVE)){
            ExplosionData data = new ExplosionData(3 , 3);
            Explosive boom = new Explosive(data);
            boom.do_skill(this ,game );
        }
    }

    private float nextStg = 24f;
    private void grow(float delta){
        if(nextStg <= 0){
            if(tags.contains(PlantTags.SHROOM)){

            }
            else if(tags.contains(PlantTags.AoE)){

            }
        }
    }
}

