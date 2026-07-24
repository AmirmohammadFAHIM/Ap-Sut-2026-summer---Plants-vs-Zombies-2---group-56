package models.factory.plantSkills.skillDatas;

import models.entity.Zombie;
import models.factory.plantSkills.Skill;
import models.games.BaseGame;
import models.entity.Plant;
import models.entity.Sun;

import java.util.ArrayList;

public class SunProduce implements Skill {
    private SunProduceData data;



    @Override
    public void do_skill(Plant producer, BaseGame game) {
        produce(producer , data, game);
    }

    @Override
    public ArrayList<Zombie> random(Plant plant, BaseGame game, int numbers) {
        return Skill.super.random(plant, game, numbers);
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

    @Override
    public void dispose(Plant self, BaseGame game) {
        Skill.super.dispose(self, game);
    }

    @Override
    public boolean disposable() {
        return Skill.super.disposable();
    }



    private void produce(Plant producer,SunProduceData data , BaseGame game) {
        Sun sun = new Sun(data.getSunType().getAmount() , data.getSunType().getRemainingTime() ,
                producer.getX(), producer.getY());{
            game.getSuns().add(sun);
        }
    }



    @Override
    public void update(){
        data.amount += 25;
    }


}
