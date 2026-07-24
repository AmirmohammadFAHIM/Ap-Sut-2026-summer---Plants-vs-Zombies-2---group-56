package models.factory.plantSkills.skillDatas;

import models.entity.Plant;
import models.games.BaseGame;
import models.games.Game;

public class PlantArmor {
    private float hp;
    private boolean broken = false;
    public boolean pumpkin = false;
    private boolean explosive ;
    public  PlantArmor(float hp) {
        this.hp = hp;
        explosive = false;
    }

    public PlantArmor(float hp, boolean explosive) {
        this.hp = hp;
        this.explosive = explosive;
    }
    public float getHp() {
        return hp;
    }
    public void setHp(float hp) {
        this.hp = hp;
    }
    public boolean isBroken() {
        return broken;
    }
    public void setBroken(boolean broken) {
        this.broken = broken;
    }

    public void dispose(BaseGame game) {

    }
    public boolean isExplosive() {
        return explosive;
    }
}
