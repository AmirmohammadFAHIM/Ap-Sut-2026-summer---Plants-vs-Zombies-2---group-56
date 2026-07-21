package models.factory.plantSkills;

import models.games.BaseGame;
import models.entity.Plant;
import models.entity.Zombie;

import java.util.ArrayList;

public class Melee implements Skill{
    public enum MeleeAttack{PUNCH , AoE,}
    MeleeAttack attackType;
    public int punch_numbers;
    public int edge;
    public Melee(MeleeAttack attackType){
        this.attackType = attackType;
        punch_numbers = 1;
    }
    public Melee(MeleeAttack attackType , int punch_numbers ,  int edge){
        this.attackType = attackType;
        this.punch_numbers = punch_numbers;
        this.edge = edge;
    }
    public Melee(MeleeAttack attackType , int punch_numbers){
        this.attackType = attackType;
        this.punch_numbers = punch_numbers;
    }
    @Override
    public void do_skill(Plant plant, BaseGame game) {
        switch (attackType){
            case  PUNCH -> punch(plant, game);
            case AoE -> AoE_Punch(plant , game ,edge, punch_numbers);
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
                if(x.getTileIndex() == plant.getTileIndex() + 1 ||
                x.getTileIndex() == plant.getTileIndex() - 1){
                    x.setHp(x.getHp() - plant.getDamage());
                    /// TO DO: Implement the heat effect on Icy materials of zombies or field
                }
            }
        }
    }


    private void AoE_Punch(Plant plant, BaseGame game , int edge , int punch_numbers /*for multi punches(plant food)*/) {
        ArrayList<Zombie> zombies;
        for (Zombie x : game.getZombies()) {
            if(Math.abs(x.getLine() - plant.getLine()) <= edge && Math.abs(x.getTileIndex() - plant.getTileIndex()) <= edge) {
                x.setHp(x.getHp() - plant.getDamage() * punch_numbers);
            }
        }

    }
}
