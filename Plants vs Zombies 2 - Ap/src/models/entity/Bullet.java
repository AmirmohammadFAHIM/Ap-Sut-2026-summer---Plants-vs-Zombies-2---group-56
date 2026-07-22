package models.entity;

import models.Constants;
import models.gamePanes.Field;
import models.gamePanes.Tile;
import models.gamePanes.TileType;
import models.games.BaseGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;


public class Bullet {
    private BulletType type;
    private float velocityX;
    private float velocityY;
    private float width;
    private float height;
    private float destinationX;
    private float destinationY;
    private float damage;
    private float AoEDamage;
    private float AoE;
    private float x;
    private float y;
    private float pierce = 1;
    private boolean grounded = false;
    private float poisonDamage = Constants.poisonBaseDamage;
    private final ArrayList<BulletType> bowling = new ArrayList<>(Arrays.asList(BulletType.ONION));


    /// ------------BOOLEANS------------
    public enum Tag{MAGICAL,ICE,FIRE,POISON,HOMING,AoE}
    ArrayList<Tag> tags;
    private boolean proved = false;
    /// for homing plants_inField of course!
    private Zombie toLockIn;
    public void setTags(ArrayList<PlantTags> tags){
        if(tags.contains(PlantTags.FIRE)){
            this.tags.add(Tag.FIRE);
        }
        if(tags.contains(PlantTags.POISON)){
            this.tags.add(Tag.POISON);
        }
        if(tags.contains(PlantTags.ICE)) this.tags.add(Tag.ICE);
        if(tags.contains(PlantTags.MAGICAL)) this.tags.add(Tag.MAGICAL);
    }

    public Bullet(float x, float y , float velocityX ,  float velocityY) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public Bullet(float x, float y , float velocityX , BulletType type ,  float damage) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.type = type;
        this.damage = damage;
    }

    public Bullet(float x, float y , BulletType bulletType) {
        this.x = x;
        this.y = y;
        this.type = bulletType;
        this.velocityX = Constants.BulletVelocityX;
        damage = 20;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Bullet(){

    }

    public void run(float delta , BaseGame game){

        if(pierce <= 0) dispose(game);
        updateLocation(delta);
       if(!tags.contains(Tag.MAGICAL) && toLockIn == null) block(game.getField());
       if(bowling.contains(this.type)){
           bowling(game.getField());
       }

    }

    private void hit(BaseGame game){
        if(toLockIn != null){
            if(overlaps(toLockIn)){
                // TODO: deal damage
            }
        }
        for (Zombie x : game.getZombies()){
            if(overlaps(x)){
                // TODO: deal damagem
            }
        }
    }
    private void dispose(BaseGame game) {
        game.getBullets().remove(this);
    }

    private void block(Field field){
        for (int i = 0; i < 5; i++) {
            for (Tile tile : field.getTiles().get(i)){
                if(overlaps(tile)){
                    if(tile.getTileType() == TileType.FROZEN && this.tags.contains(Tag.FIRE)){
                        tile.setTileType(TileType.CAVE_TILE);
                        setPierce(pierce - 1);
                    }
                    else if(tile.getHp() >= 0){
                        tile.setHp(tile.getHp() - this.damage);
                        setPierce(pierce - 1);
                    }
                }
            }
        }
    }

    private void updateLocation(float delta){
        if(toLockIn != null){
            setDest();
        }
        this.x += velocityX * delta;
        this.y += velocityY * delta;
        if(!grounded){
            this.velocityY -= Constants.gravity * delta;
        }
    }
    private void setDest(){
        float dy = toLockIn.getY() - y;
        float dx = toLockIn.getX() - x;
        float d = (float) Math.sqrt(dx * dx + dy * dy);
        velocityX = Constants.HomingVelocity * (dx / d);
        velocityY = Constants.HomingVelocity * (dy / d);
    }

    private void bowling(Field field){
        if(this.y + this.height >= field.getHeight()){
            velocityY *= -1;
        }
        else if(this.y <= 0){
            velocityY *= -1;
        }
    }


    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public float getDestinationX() {
        return destinationX;
    }

    public void setDestinationX(float destinationX) {
        this.destinationX = destinationX;
    }

    public float getDestinationY() {
        return destinationY;
    }

    public void setDestinationY(float destinationY) {
        this.destinationY = destinationY;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
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

    public boolean isProved() {
        return proved;
    }

    public void setProved(boolean proved) {
        this.proved = proved;
    }

    public Zombie getToLockIn() {
        return toLockIn;
    }

    public void setToLockIn(Zombie toLockIn) {
        this.toLockIn = toLockIn;
    }

    public BulletType getType() {
        return type;
    }

    public void setType(BulletType type) {
        this.type = type;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Object o = super.clone();
        Bullet clone = new Bullet(this.x , this.y , this.type);
        clone.setVelocityX(this.velocityX);
        clone.setVelocityY(this.velocityY);
        clone.setDestinationX(this.destinationX);
        clone.setDestinationY(this.destinationY);
        clone.setDamage(this.damage);
        return clone;
    }
    public boolean overlaps(Tile tile){
        float centreX = this.x + this.width /2 ;
        float centreY = this.y + this.height /2 ;
        boolean x = centreX >= tile.getX() && centreX <= tile.getX() + Tile.getWidth();
        boolean y = centreY >= tile.getY() && centreY <= tile.getY() + Tile.getHeight();
        return x & y;
    }

    public boolean overlaps(Zombie zombie){
        float centreX = this.x + this.width /2 ;
        float centreY = this.y + this.height /2 ;
        boolean x = centreX >= zombie.getX() && centreX <= zombie.getX() + zombie.getWidth();
        boolean y = centreY >= zombie.getY() && centreY <= zombie.getY() + zombie.getHeight();
        return x & y;
    }

    public void setPierce(float pierce) {
        this.pierce = pierce;
    }
}
