package models.npc;

public class Bullet {
    private float velocityX;
    private float velocityY;
    private float destinationX;
    private float destinationY;
    private int damage;
    private float x;
    private float y;
    private boolean proved = false;

    public Bullet(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void dealDamage() {}

    public void run(){}

}
