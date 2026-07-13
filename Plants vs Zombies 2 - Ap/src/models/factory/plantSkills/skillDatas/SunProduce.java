package models.factory.plantSkills.skillDatas;

import models.factory.plantSkills.Skill;
import models.games.BaseGame;
import models.npc.Plant;
import models.npc.Sun;

public class SunProduce implements Skill {
    private SunProduceData normalData;
    private SunProduceData plantFoodData;


    @Override
    public void do_skill(Plant producer, BaseGame game) {
        produce(producer , normalData , game);
    }

    @Override
    public void plantFoodSkill(Plant producer, BaseGame game) {
        produce(producer , plantFoodData , game);
    }

    private void produce(Plant producer,SunProduceData data , BaseGame game) {
        Sun sun = new Sun(data.getSunType().getAmount() , data.getSunType().getRemainingTime() ,
                producer.getX(), producer.getY());{
            game.getSuns().add(sun);
        }
    }


}
