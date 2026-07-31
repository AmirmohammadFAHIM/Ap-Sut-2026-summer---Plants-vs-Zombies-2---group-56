package models.entity;

public enum ArmorType {
    CONE("cone", 370, false),
    BUCKET("bucket", 1100, true),
    BRICK("brick", 2200, false),
    CROWN("crown", 1600, true),
    SHOULDER("shoulder", 1600, false),
    NEWSPAPER("newspaper", 800, false);

    private final String id;
    private final int health;
    private final boolean magnetic;

    ArmorType(String id, int health, boolean magnetic) {
        this.id = id;
        this.health = health;
        this.magnetic = magnetic;
    }

    public String getId() { return id; }
    public int getHealth() { return health; }
    public boolean isMagnetic() { return magnetic; }

    public Armor create() {
        return new Armor(id, health, magnetic);
    }
}