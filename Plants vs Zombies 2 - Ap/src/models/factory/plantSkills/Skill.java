package models.factory.plantSkills;

import models.games.BaseGame;
import models.npc.Plant;
import models.npc.Zombie;

import java.util.ArrayList;
import java.util.Random;

public interface Skill {
    public void do_skill(Plant plant , BaseGame game);

    public default ArrayList<Zombie> random(Plant plant , BaseGame game , int numbers){
        ArrayList<Zombie> randomZombies = new ArrayList<>();
        int ZombiesCount = Math.min(numbers, game.getZombies().size());
        Random rand  = new Random();
        for (int i = 0; i < ZombiesCount; i++) {
            int index =  rand.nextInt(game.getZombies().size());
            Zombie z = game.getZombies().get(index);
            if(randomZombies.contains(z)){
                i--; /// for avoiding skipping
            }
            else{
                randomZombies.add(z);
            }
        }
        return randomZombies;
    }

    public void all(Plant plant , BaseGame game);

    //public void plantFoodSkill(Plant plant , BaseGame game);
}
