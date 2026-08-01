package models.entity;

import models.Constants;
import models.gamePanes.Tile;
import models.games.BaseGame;

public class Sun extends Entity{
    Plant producer;
     float velocity = 70f;
    static float width = 50;
    static float height = 50;
    private int price;
    private boolean radioActive = false;
    boolean ground = false;
    private float remainingTime;
    public Sun(){}
    public Sun(int price, float remainingTime, float x, float y) {
        this.price = price;
        this.remainingTime = remainingTime;
        this.x = x;
        this.y = y;
    }


    public Sun(int price, int remainingTime){}



    public String land(float delta ,  BaseGame game){
        if(producer != null){
            producer.t = producer.getActionInterval();
        }
        if(!ground)
        {
            this.y -= delta * Constants.SunDroppingVelocity;
        if(this.y + Sun.height / 2 <= this.line * Tile.getHeight()
                + Tile.getHeight() / 2 ){
            ground = true;
            if(radioActive) {
               radioActive = false;
            }
            return "Sun landed at " + this.tileIndex +
                    " , " + this.line;
        }
        return null;
        }
        if(ground){
            remainingTime  -= delta;
        }
        return "sun is waiting in (" + this.tileIndex + " , " + this.line + ")" +
                "\n time remaining: " + remainingTime ;
    }

    public void dispose(BaseGame game){

        if(radioActive) {
            float centreX = this.x + Sun.width / 2;
            float centreY = this.y + Sun.height / 2;
            for (Zombie zombie : game.getZombies()) {
                float zCentreX = zombie.getX() + zombie.getWidth() / 2;
                float zCentreY = zombie.getY() + zombie.getHeight() / 2;
                if (Math.abs(centreX - zCentreX) <= Tile.getWidth() * 2 &&
                        Math.abs(centreY - zCentreY) <= Tile.getHeight() * 2) {
                    zombie.setHp(zombie.getHp() - 150);
                }
            }
            for (Plant x : game.getPlants_inField()) {
                float pCentreX = x.getX() + x.getWidth() / 2;
                float pCentreY = x.getY() + x.getHeight() / 2;
                if (Math.abs(centreX - pCentreX) <= Tile.getWidth() &&
                        Math.abs(centreY - pCentreY) <= Tile.getHeight()) {
                    x.setHp(x.getHp() - 80);
                }
            }
        }

            game.getSuns().remove(this);

    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isRadioActive() {
        return radioActive;
    }

    public void setRadioActive(boolean radioActive) {
        this.radioActive = radioActive;
    }

    public float getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(float remainingTime) {
        this.remainingTime = remainingTime;
    }



    public void setLine(int line) {
        this.line = line;
        this.y = line * Tile.getHeight();
    }

    public Plant getProducer() {
        return producer;
    }

    public void setProducer(Plant producer) {
        this.producer = producer;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }



    public boolean isGround() {
        return ground;
    }

    public void setGround(boolean ground) {
        this.ground = ground;
    }

    public int getTileIndex() {
        return tileIndex;
    }

    public void setTileIndex(int tileIndex) {
        this.tileIndex = tileIndex;
        this.x =  tileIndex * Tile.getWidth();
    }
}

