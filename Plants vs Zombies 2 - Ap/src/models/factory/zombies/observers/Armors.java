package models.factory.zombies.observers;

public enum Armors {

    BUCKET(1100 , true);

    Armors(int health , boolean magnetic) {
        this.health = health;
        this.magnetic = magnetic;
    }
    int health;
    boolean magnetic;
}
