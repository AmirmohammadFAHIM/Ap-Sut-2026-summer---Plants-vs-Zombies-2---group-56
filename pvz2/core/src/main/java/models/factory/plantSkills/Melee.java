package models.factory.plantSkills;

import models.games.BaseGame;
import models.entity.Plant;
import models.entity.Zombie;

import java.util.ArrayList;

public class Melee implements Skill{
    public enum MeleeAttack{PUNCH , AoE,}
    MeleeAttack attackType;
    public int punchNumbers;
    public int edge;
    public int range = 1;
    public Skill setRange(int range) {
        this.range = range;
        return this;
    }
    public Melee(MeleeAttack attackType){
        this.attackType = attackType;
        punchNumbers = 1;
    }
    public Melee(MeleeAttack attackType , int punchNumbers, int edge){
        this.attackType = attackType;
        this.punchNumbers = punchNumbers;
        this.edge = edge;
    }
    public Melee(MeleeAttack attackType , int punchNumbers){
        this.attackType = attackType;
        this.punchNumbers = punchNumbers;
    }
    @Override
    public void do_skill(Plant plant, BaseGame game) {
        System.out.println(plant.getType() + " is fighting ...");
        switch (attackType){
            case  PUNCH -> punch(plant, game);
            case AoE -> aoEPunch(plant , game ,edge, punchNumbers);
        }
    }

    @Override
    public void all(Plant plant, BaseGame game) {

    }

    @Override
    public void setRandom(boolean random) {

    }

    @Override
    public void setAll(boolean all) {

    }

    private void punch(Plant plant, BaseGame game) {
        ArrayList<Zombie> zombies;
        for (Zombie x : game.getZombies()) {
            if(x.getLine() == plant.getLine()) {
                if(x.getTileIndex() == plant.getTileIndex() +  range||
                x.getTileIndex() == plant.getTileIndex() - range){
                    x.setHp(x.getHp() - plant.getDamage());
                    /// TO DO: Implement the heat effect on Icy materials of zombies or field
                }
            }
        }
    }


    private void aoEPunch(Plant plant, BaseGame game , int edge , int punch_numbers) {
        ArrayList<Zombie> zombies;
        for (Zombie x : game.getZombies()) {
            if(Math.abs(x.getLine() - plant.getLine()) <= edge
                    && Math.abs(x.getTileIndex() - plant.getTileIndex()) <= edge) {
                x.setHp(x.getHp() - plant.getDamage() * punch_numbers);
            }
        }

    }
}
