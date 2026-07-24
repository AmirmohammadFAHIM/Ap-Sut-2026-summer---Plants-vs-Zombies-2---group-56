package models.games.miniGames;

import models.Constants;
import models.entity.Entity;
import models.entity.Plant;
import models.entity.Zombie;
import models.gamePanes.Tile;
import models.games.BaseGame;

import java.util.ArrayList;

public class BowlingNut extends Entity {
    private float damage;
    private boolean explosive;
    public BowlingNut(float damage, boolean explosive) {
        this.damage = damage;
    }
    public BowlingNut(float damage) {
        this.damage = damage;
    }

    public void go(float delta, BaseGame game) {
        this.x += velocityX * delta;
        this.y += velocityY * delta;
        if(block()) velocityY *= -1;
        else if(hit(game.getZombies())) velocityY *= -1;
        if(this.x >= 9 * Tile.getWidth()) dispose(game);
    }

    private void dispose(BaseGame game) {
        WallnutBowling wallnutBowling = (WallnutBowling) game;
        wallnutBowling.nuts.remove(this);
    }

    private boolean block(){
        if(this.y <= 0){
            return true;
        }
        else return this.y + this.height >= 5 * Tile.getHeight();
    }

    private boolean hit(ArrayList<Zombie> zombies){
        for (Zombie z : zombies) {
            if(Constants.overlap(this , z)){
                // TODO: deal damage
                return true;
            }
        }
        return false;
    }


}
