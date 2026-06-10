package models.factory.zombies.observers;

import models.npc.Zombie;

public class Barrel implements  Observer {
    private Zombie roller;
    public Barrel(Zombie roller) {}
    @Override
    public void observe() {}
}
