package models.entity;

import models.App;
import models.factory.builder.PlantType;
import models.factory.plantSkills.Explosive;
import models.factory.plantSkills.Skill;
import models.factory.plantSkills.skillDatas.ExplosionData;
import models.factory.plantSkills.skillDatas.PlantArmor;
import models.factory.plantSkills.skillobserver.Observer;
import models.gamePanes.Tile;
import models.games.BaseGame;

import java.util.ArrayList;

public class Plant extends Entity {
    private float damage;
    private int cost;
    private float ActionInterval;
    public float t;
    private PlantCategory category;
    private PlantType type;
    private ArrayList<PlantTags> tags;
    private ArrayList<Skill> baseSkill;
    private ArrayList<Skill> plantfoodSkill;
    private boolean frozen = false;
    private boolean cat = false;
    public boolean onLilyPad = false;
    private float lifeTime;
    private int freezeLevel = 0;
    private ArrayList<PlantArmor> armor;
    private Observer skillObserver;

    public ArrayList<PlantArmor> getArmor() {
        return armor;
    }

    public void setArmor(ArrayList<PlantArmor> armor) {
        this.armor = armor;
    }

    public Plant(float actionInterval) {
        ActionInterval = actionInterval;
    }
    public Plant(){

    }

    public void boost(){}

    public PlantCategory getCategory() {
        return category;
    }

    public ArrayList<PlantTags> getTags() {
        return tags;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
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

    public ArrayList<Skill> getBaseSkill() {
        return baseSkill;
    }

    public void setBaseSkill(ArrayList<Skill> baseSkill) {
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
        if(freezeLevel >= 3 || cat || frozen) heat(game ,delta); ;
        if(t <= 0){
            t = ActionInterval;
           if(Trap(game)) {
               if(skillObserver.observe(this , game)){
                   for (Skill x : baseSkill) x.do_skill(this , game);
               }
               if(tags.contains(PlantTags.ONCE_USAGE)){
                   dispose(game);
               }
           }

        }
        else{
            t -= delta;
        }

        if(plantFood){
            for (Skill x : plantfoodSkill) x.do_skill(this , game);
            plantFood = false;
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
        if(type == PlantType.LILY_PAD){
            Tile tile = game.getField().getTileByCoordinats(tileIndex , line);
            tile.setPlantable(false);
        }
        if(tags.contains(PlantTags.EXPLOSIVE)){
            ExplosionData data = new ExplosionData( 3 ,3);
            new Explosive(data).do_skill(this , game);
        }
        game.getPlants_inField().remove(this);

    }

    public void setPlantFood(boolean plantFood , BaseGame game) {
        for (Skill x : plantfoodSkill){
            x.do_skill(this , game);
        }
    }
    boolean plantFood;
    public void setPlantFood(boolean plantFood) {
        this.plantFood = plantFood;
    }


    public int getFreezeLevel() {
        return freezeLevel;
    }

    public void setFreezeLevel(int freezeLevel) {
        if(freezeLevel >= 3){
            freezeLevel = 3;
            frozen = true;
            if(freezeHp <= 0) freezeHp = 700;
        }
        this.freezeLevel = freezeLevel;
    }

    public void setHp(float hp , Zombie eater , BaseGame game){
        this.hp = hp;
        if(hp <= 0){
            dispose(eater , game);
        }
    }
    public void setHP(float hp){
        this.hp = hp;
    }

    private void dispose(Zombie eater , BaseGame game){
        if(tags.contains(PlantTags.SHROOM) && tags.contains(PlantTags.MAGICAL)){
           eater.setHypnotized(true);
        }
        else if(tags.contains(PlantTags.EXPLOSIVE)){
            if(App.getCurrentuser().getLevels().get(this.type) >= 3) damage += 200;
            ExplosionData data = new ExplosionData(3 , 3);
            Explosive boom = new Explosive(data);
            boom.do_skill(this ,game );
        }
    }

    float freezeHp;

    public void setFreezeHp(float freezeHp) {
        if(freezeHp <= 0){
            frozen = false;
            freezeLevel = 0;
        }
        this.freezeHp = freezeHp;
    }

    private void heat(BaseGame game , float delta){
        for (Plant x : game.getPlants_inField()){
            float dx =  Math.abs(x.getX() - this.x);
            float dy = Math.abs(x.getY() - this.y);
            if(dx <= Tile.getWidth() * 1 && dy  <= Tile.getHeight() * 1){
                setFreezeHp(freezeHp - 60 * delta);
            }
        }
    }

    public void setActionInterval(float actionInterval) {
        ActionInterval = actionInterval;
    }

    public void setSkillObserver(Observer skillObserver) {
        this.skillObserver = skillObserver;
    }
}

