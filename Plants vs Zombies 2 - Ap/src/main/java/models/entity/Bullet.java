package models.entity;

import models.Constants;
import models.gamePanes.Field;
import models.gamePanes.Tile;
import models.gamePanes.TileType;
import models.games.BaseGame;

import java.util.ArrayList;
import java.util.Arrays;


public class Bullet implements Cloneable {
    private BulletType type;
    private float velocityX;
    private float velocityY;
    private float width = 50;
    private float height = 50;
    private float destinationX;
    private float destinationY;
    private float damage;
    private float aoEDamage;
    private float x;
    private float y;
    private float pierce = 1;
    private boolean grounded = true;
    private boolean active;
    private float poisonDamage = Constants.poisonBaseDamage;
    private final ArrayList<BulletType> bowling = new ArrayList<>(Arrays.asList(BulletType.ONION_1,
            BulletType.ONION_2 , BulletType.ONION_3 , BulletType.Explosive_Onion));


    /// ------------BOOLEANS------------
    public enum Tag{MAGICAL,ICE,FIRE,POISON,HOMING,AoE}
    ArrayList<Tag> tags = new  ArrayList<>();
    private boolean proved = false;
    /// for homing plantsInField of course!
    private Zombie toLockIn;
    public void setTags(ArrayList<PlantTags> tags){
        if(tags.contains(PlantTags.Fire)){
            this.tags.add(Tag.FIRE);
        }
        if(tags.contains(PlantTags.POISON)){
            this.tags.add(Tag.POISON);
        }
        if(tags.contains(PlantTags.Ice)) this.tags.add(Tag.ICE);
        if(tags.contains(PlantTags.MAGICAL)) this.tags.add(Tag.MAGICAL);
        if(tags.contains(PlantTags.AoE)) this.tags.add(Tag.AoE);
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

        if(!grounded){
            velocityY -= Constants.gravity * delta;
        }

        if(pierce <= 0) dispose(game);
        updateLocation(delta , game);
        if(!tags.contains(Tag.MAGICAL) && toLockIn == null) block(game);
        if(bowling.contains(this.type)){
            bowling(game.getField());
        }
        checkHit(game);

    }

    private void checkHit(BaseGame game){
        if (toLockIn != null) {

            if (overlaps(toLockIn)) {
                hitZombie(toLockIn);
            }
            return;
        }

        for (Zombie z : game.getZombies()) {
            if (overlaps(z)) {
                hitZombie(z);
                if(tags.contains(Tag.AoE)){
                    damageOnArea(1 , game);
                }
                break;
            }
        }
    }

    private void hitZombie(Zombie z){
        this.pierce -= 1;
        z.notifyBulletObservers(this);
        if (this.isActive()) {
            z.takeDamage((int) this.damage);
            if (this.getTags().contains(Tag.ICE)) {
                z.addEffect(new Effect(EffectType.FROZEN, 3.0f));
            }
            if (this.getTags().contains(Tag.POISON)) {
                z.addEffect(new Effect(EffectType.POISONED, 5.0f));
            }
            if (this.getTags().contains(Tag.FIRE)) {
                z.setFrozen(false);
                z.setDynamiteFrozen(false);
            }
        }
    }


    private void damageOnArea(int radius , BaseGame  game){
        for (Zombie z : game.getZombies()) {
            float dx = Math.abs(z.getX() - this.x);
            float dy = Math.abs(z.getY() - this.y);
            if(dx <= Tile.getWidth() * radius && dy <= Tile.getHeight() * radius){
                z.setHurt(true);
                z.takeDamage((int) this.damage);
            }
        }
    }
    private void dispose(BaseGame game) {
        game.getBullets().remove(this);
    }

    private void block(BaseGame game){
        for (int i = 0; i < 5; i++) {
            for (Tile tile : game.getField().getTiles().get(i)){
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

        for (Plant p : game.getPlantsInField()){
            if(p.isFrozen()){
                if(this.tags.contains(Tag.FIRE)){
                    p.setFreezeHp(0);
                }
                else {
                    p.setFreezeHp(p.freezeHp -  this.damage);
                }
            }
        }
        Zombie z ;

    }

    public int line;
    private void updateLocation(float delta, BaseGame game){
        if(toLockIn != null){
            setDest();
        }
        this.x += velocityX * delta;
        this.y += velocityY * delta;
        if(!grounded){
            this.velocityY -= Constants.gravity * delta;
        }
        if(this.y - line * Tile.getHeight() <= 30 && velocityY < 0){
            grounded = true;
            if(tags.contains(Tag.AoE)){
                AoE(game);
            }
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

    private void AoE(BaseGame game){
        for (Zombie z : game.getZombies()) {
            float dx = Math.abs(z.getX() - this.x);
            float dy = Math.abs(z.getY() - this.y);
            if(dx <= Tile.getWidth() * 1 && dy <= Tile.getHeight() * 1){
                z.takeDamage((int) this.aoEDamage);
                if (this.getTags().contains(Tag.ICE)) {
                    z.addEffect(new Effect(EffectType.FROZEN, 3.0f));
                }
                if (this.getTags().contains(Tag.POISON)) {
                    z.addEffect(new Effect(EffectType.POISONED, 5.0f));
                }
            }
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
        Bullet clone = (Bullet) super.clone();

        if (this.tags != null) {
            clone.tags = new ArrayList<>(this.tags);
        } else {
            clone.tags = new ArrayList<>();
        }

        clone.width = this.width;
        clone.height = this.height;
        clone.aoEDamage = this.aoEDamage;
        clone.pierce = this.pierce;
        clone.grounded = this.grounded;
        clone.active = this.active;
        clone.poisonDamage = this.poisonDamage;
        clone.toLockIn = this.toLockIn;

        return clone;
    }
    public boolean overlaps(Tile tile){
        float centreX = this.x + this.width /2 ;
        float centreY = this.y + this.height /2 ;
        boolean x = centreX >= tile.getX() && centreX <= tile.getX() + Tile.getWidth();
        boolean y = centreY >= tile.getY() && centreY <= tile.getY() + Tile.getHeight();
        return x && y;
    }

    public boolean overlaps(Zombie zombie){
        if (zombie == null) {
            return false;
        }

        return (this.getX() < zombie.getX() + zombie.getWidth()) &&
                (this.getX() + width > zombie.getX()) &&
                (this.getY() < zombie.getY() + zombie.getHeight()) &&
                (this.getY() + height > zombie.getY());

    }

    public void setPierce(float pierce) {
        this.pierce = pierce;
    }

    public boolean isActive(){
        return this.active;
    }
    public void setActive(boolean active){
        this.active = active;
    }

    public float getPierce() {
        return pierce;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    public boolean isGrounded() {
        return grounded;
    }
}
