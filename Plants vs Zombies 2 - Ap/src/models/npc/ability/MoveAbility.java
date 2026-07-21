package models.npc.ability;

import models.npc.*;
import models.grid.GridItem;
import controllers.GameController;

public class MoveAbility implements Ability {

    private final MoveType type;
    private final float cooldown;
    private float timer;
    private boolean isCarrying;

    public enum MoveType {
        PUSH_ARCADE,
        PUSH_ICE,
        PULL_PLANT,
        SWAP_ZOMBIE
    }

    public MoveAbility(MoveType type, float cooldown) {
        this.type = type;
        this.cooldown = cooldown;
        this.timer = 0;
        this.isCarrying = false;
    }

    @Override
    public void execute(Zombie zombie, float deltaTime, GameController controller) {
        timer -= deltaTime;
        if (timer > 0) return;

        // هیپنوتیزم: فقط ماهیگیر تغییر می‌کند
        if (zombie.hasEffect(EffectType.HYPNOTIZED)) {
            if (type == MoveType.PULL_PLANT) {
                handleHypnotizedPull(zombie, controller);
            }
            timer = cooldown;
            return;
        }

        switch (type) {
            case PUSH_ARCADE:
            case PUSH_ICE:
                handlePush(zombie, controller);
                break;
            case PULL_PLANT:
                handlePull(zombie, controller);
                break;
            case SWAP_ZOMBIE:
                handleSwap(zombie, controller);
                break;
        }

        timer = cooldown;
    }

    // ====== PUSH (ARCADE / TROGLOBITE) ======
    private void handlePush(Zombie zombie, GameController controller) {
        GridItem item = controller.getPushableItemInFront(zombie);

        if (item != null) {
            isCarrying = true;
            controller.pushItem(zombie, item);
        } else {
            isCarrying = false;
        }
    }

    // ====== PULL (FISHERMAN) ======
    private void handlePull(Zombie zombie, GameController controller) {
        Plant target = controller.findPullablePlant(zombie);
        if (target != null) {
            controller.pullPlant(zombie, target);
        }
    }

    // ====== SWAP (PIANO) ======
    private void handleSwap(Zombie zombie, GameController controller) {
        Zombie target = controller.getRandomZombie();
        if (target != null && target != zombie) {
            controller.swapZombieToRow(target, zombie.getRow());
        }
    }

    // ====== HYPNOTIZED FISHERMAN ======
    private void handleHypnotizedPull(Zombie zombie, GameController controller) {
        // به جای گیاه، یک زامبی را به سمت خود می‌کشد
        Zombie target = controller.getRandomZombieInRange(zombie, 4.0f);
        if (target != null && target != zombie) {
            controller.pullZombie(zombie, target);
        }
    }

    public boolean isCarrying() {
        return isCarrying;
    }

    public MoveType getType() {
        return type;
    }
}