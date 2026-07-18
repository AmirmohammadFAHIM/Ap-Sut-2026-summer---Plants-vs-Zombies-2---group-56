package controllers.strategy;

import models.npc.Zombie;

public class TurquoiseBehavior implements BehaviorStrategy {

    private static final float STEAL_DURATION = 5.0f;
    private static final float LASER_COOLDOWN = 8.0f;

    private enum State { IDLE, STEALING, LASER }
    private State state = State.IDLE;
    private float stateTimer = 0;
    private int stolenSun = 0;
    private float laserCooldown = 0;

    @Override
    public void execute(Zombie zombie, float deltaTime) {
        if (zombie.isDead()) {
            releaseStolenSun(zombie);
            return;
        }

        switch (state) {
            case IDLE:
                laserCooldown -= deltaTime;
                if (laserCooldown <= 0) {
                    state = State.STEALING;
                    stateTimer = 0;
                    stolenSun = 0;
                }
                break;
            case STEALING:
                stateTimer += deltaTime;
                if (stateTimer % 1.0f < deltaTime) {
                    stolenSun += 25;
                }
                if (stateTimer >= STEAL_DURATION) {
                    state = State.LASER;
                    stateTimer = 0;
                }
                break;
            case LASER:
                stateTimer += deltaTime;
                if (stateTimer >= 0.5f) {
                    state = State.IDLE;
                    laserCooldown = LASER_COOLDOWN;
                }
                break;
        }
    }

    private void releaseStolenSun(Zombie zombie) {
        if (stolenSun > 0) {
            int released = stolenSun / 2;
            // add sun back to player - delegated to controller
            stolenSun = 0;
        }
    }
}