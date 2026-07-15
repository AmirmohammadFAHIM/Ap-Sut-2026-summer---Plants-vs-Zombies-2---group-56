package models.factory.plantSkills;

import models.entity.Plant;
import models.games.BaseGame;

public class ExtraHP implements Skill{
    public enum Type{CLONE , ARMOR , HEAL , LIFE_RESET}
    Type type;
    public ExtraHP(Type t){
        type = t;
    }
    @Override
    public void do_skill(Plant plant, BaseGame game) {

    }

    @Override
    public void all(Plant plant, BaseGame game) {

    }

    @Override
    public void setRandom(boolean random) {

    }

    @Override
    public void setAll(boolean all) {

    }

    public int cloneNumber;
    private void clone(Plant plant, BaseGame game) {
        for (int i = 0; i < cloneNumber; i++) {
            /// TO DO: find an empty place to throw the clones to
        }
    }

    private void armor(Plant plant, BaseGame game) {

    }

    private void heal(Plant plant, BaseGame game) {

    }

    private void reset(Plant plant, BaseGame game) {

    }
}
