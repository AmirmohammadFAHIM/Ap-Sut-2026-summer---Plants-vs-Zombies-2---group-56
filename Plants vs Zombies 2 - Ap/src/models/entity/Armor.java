package models.entity;

public class Armor {

    private final String type;
    private int health;
    private final int maxHealth;
    private final boolean magnetic;
    private boolean broken;

    public Armor(String type, int health) {
        this(type, health, false);
    }

    public Armor(String type, int health, boolean magnetic) {
        this.type = type;
        this.health = health;
        this.maxHealth = health;
        this.magnetic = magnetic;
        this.broken = false;
    }

    public void takeDamage(int damage) {
        if (broken) return;
        health -= damage;
        if (health <= 0) {
            health = 0;
            broken = true;
        }
    }

    public boolean isActive() {
        return !broken && health > 0;
    }

    // ====== GETTERS ======
    public String getType() { return type; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public boolean isMagnetic() { return magnetic; }
    public boolean isBroken() { return broken; }
}