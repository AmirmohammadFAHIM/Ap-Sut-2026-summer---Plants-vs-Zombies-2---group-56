package models.games.minigames;

import models.Constants;
import models.entity.Entity;
import models.entity.Zombie;
import models.gamepanes.Tile;
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
        else if(hit(game.getZombies())){
            if(explosive){
                dispose(game);
                return;
            }
            velocityY *= -1;
        }
        if(this.x >= 9 * Tile.getWidth()) dispose(game);
    }

    protected void dispose(BaseGame game) {
        WallnutBowling wallnutBowling = (WallnutBowling) game;
        wallnutBowling.nuts.remove(this);
        if(explosive){
            for (Zombie zombie : game.getZombies()) {
                int dx = Math.abs(this.tileIndex - zombie.getTileIndex());
                int dy = Math.abs(this.line - zombie.getLine());
                if(dx <= 1 && dy <= 1){
                    zombie.setHp(zombie.getHp() - damage);
                    zombie.takeDamage((int) damage);
                    zombie.setHurt(true);
                }
            }
        }

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
                z.takeDamage((int) this.damage);
                z.setHurt(true);
                z.setAlive(false);
                return true;
            }
        }
        return false;
    }


}
