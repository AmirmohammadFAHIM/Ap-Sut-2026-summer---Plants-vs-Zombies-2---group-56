package models;

import models.utils.*;
import models.factory.builder.*;
import java.util.ArrayList;

public class GreenHouse {
    private static final int MAX_POTS = 20;
    private User owner;
    private ArrayList<Pot> pots;

    public GreenHouse(User owner) {
        this.owner = owner;
        this.pots = new ArrayList<>();

        for (int i = 0; i < MAX_POTS; i++) {
            int x = i % 5 + 1 ;
            int y = i / 5 + 1 ;
            pots.add(new Pot(owner, x , y));
        }
    }

    public void showAll() {
        System.out.println("welcome to your green house dear" + owner.getName());

        for (int row = 1; row <= 4; row++) {
            System.out.println("row " + row + ":");
            for (int col = 1; col <= 5; col++) {
                Pot pot = getPotByPosition(col, row);
                if (pot == null)
                    return;

                String status = "[" + col + "," + row + "] ";

                if (!pot.isUnlocked()) {
                    System.out.println("locked , pool vade");
                }
                else if (pot.getSeedling() == null) {
                    System.out.println("khalie , chizi bekar");
                }
                else{
                    int remaining = pot.getRemainingHours();
                    if (remaining <= 0) {
                        System.out.println(pot.getSeedling());
                        System.out.println("ready to collect");
                    }
                    else{
                        System.out.println(pot.getSeedling());
                        System.out.println("bayad sabr koni , be moddate :" + pot.getRemainingHours());
                    }
                }
            }
            System.out.println();
        }
        System.out.println("chenin bood golkhane at , khane at por gol");
    }

    public void growNow(int x, int y) {
        Pot pot = getPotByPosition(x, y);
        if (pot == null) {
            return;
        }
        pot.growNow();
    }

    public void plantPot(int x, int y) {
        Pot pot = getPotByPosition(x, y);
        if (pot == null) {
            return;
        }
        pot.plant();
    }

    public void collectPot(int x, int y) {
        Pot pot = getPotByPosition(x, y);
        if (pot == null) {
            return;
        }
        pot.collect();
    }

    public Pot getPotByPosition(int x, int y) {
        for (Pot pot : pots) {
            if (pot.getX() == x && pot.getY() == y) {
                return pot;
            }
        }
        System.out.println("gashtam nabood");
        return null;
    }



}