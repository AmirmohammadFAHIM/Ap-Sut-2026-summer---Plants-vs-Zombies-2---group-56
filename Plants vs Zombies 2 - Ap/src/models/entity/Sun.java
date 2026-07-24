package models.entity;

import models.Constants;
import models.gamePanes.Tile;
import models.games.BaseGame;
import models.utils.Result;

public class Sun {
    Plant producer;
     float velocity = 70f;
    static float width;
    static float height;
    private int price;
    private boolean radioActive = false;
    boolean groung = false;
    private float remainingTime;
    private float x;
    private float y;
    private int line;
    private int tileIndex;
    public Sun(){}
    public Sun(int price, float remainingTime, float x, float y) {
        this.price = price;
        this.remainingTime = remainingTime;
        this.x = x;
        this.y = y;
    }


    public Sun(int price, int remainingTime){}
    public void updateTime(float delta , BaseGame game){
        if(this.remainingTime > 0){
            this.remainingTime -= delta;
        }else{
            game.getSuns().remove(this);
        }
    }


    public String land(float delta ,  BaseGame game){
        this.y -= delta * (groung ? Constants.SunDroppingVelocity : 0);
        if(this.y + Sun.height / 2 <= this.line * Tile.getHeight()
                + Tile.getHeight() / 2  && !groung){
            groung = true;
            if(radioActive) {
               radioActive = false;
            }
            return "Sun landed at " + this.tileIndex +
                    " , " + this.line;
        }
        if(groung){
           if(remainingTime <= 0){
               game.getSuns().remove(this);
           }
           else remainingTime  -= delta;
        }
        return null;
    }

    private void dispose(BaseGame game){

            float centreX = this.x + Sun.width / 2;
            float centreY = this.y + Sun.height / 2;
            for (Zombie zombie : game.getZombies()) {
                float zCentreX = zombie.getX() + zombie.getWidth() / 2;
                float zCentreY = zombie.getY() + zombie.getHeight() / 2;
                if(Math.abs(centreX- zCentreX) <= Tile.getWidth() * 2 &&
                Math.abs(centreY - zCentreY) <=  Tile.getHeight() * 2){
                    zombie.setHp(zombie.getHp() - 150);
                }
            }
            for (Plant x : game.getPlants_inField()){
                float pCentreX = x.getX() + x.getWidth() / 2;
                float pCentreY = x.getY() + x.getHeight() / 2;
                if(Math.abs(centreX - pCentreX) <= Tile.getWidth() &&
                Math.abs(centreY - pCentreY) <=   Tile.getHeight()){
                    x.setHp(x.getHp() - 80);
                }
            }

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

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getLine() {
        return line;
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

    public static float getWidth() {
        return width;
    }

    public static void setWidth(float width) {
        Sun.width = width;
    }

    public static float getHeight() {
        return height;
    }

    public static void setHeight(float height) {
        Sun.height = height;
    }

    public boolean isGroung() {
        return groung;
    }

    public void setGroung(boolean groung) {
        this.groung = groung;
    }

    public int getTileIndex() {
        return tileIndex;
    }

    public void setTileIndex(int tileIndex) {
        this.tileIndex = tileIndex;
        this.x =  tileIndex * Tile.getWidth();
    }
}

