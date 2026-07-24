package models.factory.plantSkills;

import models.factory.plantSkills.skillDatas.ExplosionData;
import models.games.BaseGame;
import models.entity.Plant;
import models.entity.Zombie;

import java.util.ArrayList;

public class Explosive implements Skill{
    ExplosionData data;
    public Explosive(ExplosionData  data){
        this.data = data;
    }

    @Override
    public void do_skill(Plant plant, BaseGame game) {
        switch (data.type){
            case ALL -> all(plant, game);
            case AOE -> AoE(plant, game);
            case LINE -> oneLine(plant, game);
            case NEXT_TO -> nextTo(plant, game);
            case TOUCH -> touch(plant, game);
            case RANDOM -> random(plant, game , data.randomCount);
        }
    }


    private void oneLine(Plant self, BaseGame game) {
        for (Zombie z : game.getZombies()){
            z.setHp(z.getHp() - self.getDamage());
        }
    }


    public void all(Plant self, BaseGame game) {
        for (Zombie z : game.getCurrentWave().getZombies()){
            z.setHp(0);
        }
        for (Zombie z : game.getPreviousWave().getZombies()){
            z.setHp(0);
        }

        game.getField().getTiles().get(self.getLine())
                .get(self.getTileIndex()).setPlantable(false);
    }

    @Override
    public void setRandom(boolean random) {
        data.type = random ? ExplosionData.ExplosionType.RANDOM : data.type;
    }

    @Override
    public void setAll(boolean all) {
            data.type = all ? ExplosionData.ExplosionType.ALL : data.type;
    }

    private void touch(Plant self, BaseGame game) {
        for (Zombie z : game.getZombies()){
            if(z.getX() <= self.getX() + self.getWidth()){
                z.setHp(data.instaKill ? 0 : z.getHp() - self.getDamage());
                return;
            }
        }


    }

    private void nextTo(Plant self, BaseGame game) {
        for (Zombie z : game.getCurrentWave().getZombies()) {
            if (z.getX() - self.getX() + self.getWidth() < 20) {
                z.setHp(0);
                self.setHp(0);
                return;
            }
        }


        for (Zombie z : game.getPreviousWave().getZombies()) {
            if (z.getX() - self.getX() + self.getWidth() < 20) {
                z.setHp(0);
                self.setHp(0);
            }
        }
    }




    private void AoE(Plant self, BaseGame game) {
        int x = self.getTileIndex() - ((data.width - 1) / 2);
        int y = self.getLine() + ((data.height - 1) / 2);
        for (int i = y; i < data.height; i++) {
            for (int j = y; j < data.width; j++) {
                for (Zombie z : game.getCurrentWave().getZombies()) {
                    if (z.getLine() == i && z.getTileIndex() == j) {
                        z.setHp(0);
                    }
                }
                    for (Zombie zo : game.getPreviousWave().getZombies()) {
                        if (zo.getLine() == i && zo.getTileIndex() == j) {
                            zo.setHp(0);
                        }
                    }
                }
            }
    }


    @Override
    public ArrayList<Zombie> random(Plant plant, BaseGame game, int numbers) {
        ArrayList<Zombie> randomZombies = Skill.super.random(plant, game, numbers);
        for (Zombie z : randomZombies) {
            z.setHp(0);
            /// heat effect as well
        }
        return  randomZombies;
    }
}
