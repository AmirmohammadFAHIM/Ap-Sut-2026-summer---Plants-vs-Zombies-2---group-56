package models.factory.plantSkills;

import models.entity.Plant;
import models.entity.PlantCategory;
import models.games.BaseGame;

public class Mint implements Skill{
    public PlantCategory category;
    public Mint(PlantCategory category) {
        this.category = category;
    }


    @Override
    public void do_skill(Plant plant, BaseGame game) {
        for (Plant x: game.getPlantsInField()) {
            if(x.getCategory() == category) {
                x.setPlantFood(true);
            }
        }
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
}
