package models.gamePanes;

import models.entity.*;
public class GridItem {

    private String type;      // "arcade", "ice", "barrel", "grave"
    private int row;
    private int col;
    private int hp;
    private final int maxHp;
    private boolean isPushable;
    private boolean isDestructible;
    private Zombie owner;

    public GridItem(String type, int row, int col, int hp, boolean isPushable, boolean isDestructible) {
        this.type = type;
        this.row = row;
        this.col = col;
        this.hp = hp;
        this.maxHp = hp;
        this.isPushable = isPushable;
        this.isDestructible = isDestructible;
        this.owner = null;
    }

    public void takeDamage(int damage) {
        if (!isDestructible) return;
        hp -= damage;
        if (hp < 0) hp = 0;
    }

    public boolean isDestroyed() {
        return isDestructible && hp <= 0;
    }

    // ====== GETTERS & SETTERS ======
    public String getType() { return type; }
    public int getRow() { return row; }
    public int getCol() { return col; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public boolean isPushable() { return isPushable; }
    public boolean isDestructible() { return isDestructible; }
    public Zombie getOwner() { return owner; }
    public void setOwner(Zombie owner) { this.owner = owner; }

    public void setRow(int row) { this.row = row; }
    public void setCol(int col) { this.col = col; }
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }
}