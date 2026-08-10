package models.gameadventure;

import models.Constants;
import models.entity.Zombie;
import models.games.BaseGame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Tornado implements ChapterSpecialEvent{
    ArrayList<Zombie> zombies;
    int[] destinations;
    public Tornado(BaseGame game) {
        zombies = new ArrayList<>();
        Random rand = new Random();
        int count = rand.nextInt(game.getCurrentWave().getHardness()
                * Constants.DISASTER_ZOMBIES_BASE_COUNT);
        Iterator<Zombie> iterator = game.getCurrentWave().getZombies().iterator();

        while (iterator.hasNext() && count > 0) {
            Zombie zombie =  iterator.next();
            zombies.add(zombie);
            iterator.remove();
            count -= 1;
        }

        destinations = new int[zombies.size()];
        for (int i = 0; i < zombies.size(); i++) {
            int dest =  rand.nextInt(4) + 1;
            destinations[i] = 9 -  dest;
        }
    }
    @Override
    public void run(BaseGame game  , float delta) {
        int i = 0;
        Iterator iterator = game.getZombies().iterator();
        while (iterator.hasNext()){
            Zombie zombie = (Zombie) iterator.next();
            zombie.setX(zombie.getX() - Constants.TORNADO_VELOCITY * delta );
            /// TODO: update zombies tile index , and change this to Iterator
            if(zombie.getTileIndex() == destinations[i]) {
                game.getCurrentWave().getZombies().add(zombie);
                iterator.remove();
            }
            i++;
        }

        if(zombies.isEmpty()){
            dispose(game);
        }

    }

    @Override
    public void dispose(BaseGame game) {
        game.setEvent(null);
    }
}
