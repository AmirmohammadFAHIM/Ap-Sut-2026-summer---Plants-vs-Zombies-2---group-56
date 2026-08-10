package models.games.minigames;

import models.Constants;
import models.entity.Zombie;
import models.gamepanes.Tile;
import models.games.BaseGame;

import java.util.ArrayList;

public class BigNut extends BowlingNut{
    public BigNut(float damage, boolean explosive) {
        super(damage, explosive);
    }

    public BigNut(float damage) {
        super(damage);
    }

    @Override
    public void go(float delta, BaseGame game) {
        x += velocityX * delta;
        y += velocityY * delta;
        if(this.x > 10 * Tile.getWidth()){
            dispose(game);
        }
        hit(game.getZombies());
    }

    private void hit(ArrayList<Zombie> zombies){
        for (Zombie z : zombies) {
            if(Constants.overlap(this , z)){
                z.setHp(0);
                z.setHurt(true);
                z.setAlive(false);
            }
        }
    }
}
