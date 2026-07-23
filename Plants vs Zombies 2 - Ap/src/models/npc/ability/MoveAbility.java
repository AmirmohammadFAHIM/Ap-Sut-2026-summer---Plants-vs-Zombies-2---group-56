package models.npc.ability;

import models.npc.*;
import models.grid.GridItem;
import models.games.BaseGame;

public class MoveAbility implements Ability {

    public enum MoveType {
        PUSH_ARCADE,
        PUSH_ICE,
        PUSH_BARREL,
        PULL_PLANT,
        THROW_OCTOPUS,
        SWAP_ZOMBIE
    }

    private final MoveType type;
    private final float cooldown;
    private float timer;
    private boolean isCarrying;

    public MoveAbility(MoveType type, float cooldown) {
        this.type = type;
        this.cooldown = cooldown;
        this.timer = 0;
        this.isCarrying = false;
    }

    @Override
    public void execute(Zombie zombie, float deltaTime, BaseGame game) {
        timer -= deltaTime;
        if (timer > 0) return;

        if (zombie.hasEffect(EffectType.HYPNOTIZED) && type == MoveType.PULL_PLANT) {
            Zombie target = game.getRandomZombieInRange(zombie, 4.0f);
            if (target != null && target != zombie) {
                game.pullZombie(zombie, target);
            }
            timer = cooldown;
            return;
        }

        switch (type) {
            case PUSH_ARCADE:
            case PUSH_ICE:
            case PUSH_BARREL:
                handlePush(zombie, game);
                break;
            case PULL_PLANT:
                handlePull(zombie, game);
                break;
            case THROW_OCTOPUS:
                handleThrowOctopus(zombie, game);
                break;
            case SWAP_ZOMBIE:
                handleSwap(zombie, game);
                break;
        }

        timer = cooldown;
    }

    private void handlePush(Zombie zombie, BaseGame game) {
        GridItem item = game.getPushableItemInFront(zombie);
        if (item != null) {
            isCarrying = true;
            game.pushItem(zombie, item);
        } else {
            isCarrying = false;
        }
    }

    private void handlePull(Zombie zombie, BaseGame game) {
        Plant target = game.findPullablePlant(zombie);
        if (target != null) {
            game.pullPlant(zombie, target);
        }
    }

    private void handleThrowOctopus(Zombie zombie, BaseGame game) {
        Plant target = game.getRandomPlantInRange(zombie, 4.0f);
        if (target != null) {
            target.disable(); // octopus effect
        }
    }

    private void handleSwap(Zombie zombie, BaseGame game) {
        Zombie target = game.getRandomZombie();
        if (target != null && target != zombie) {
            game.swapZombieToRow(target, zombie.getRow());
        }
    }

    public boolean isCarrying() { return isCarrying; }
    public MoveType getType() { return type; }
}