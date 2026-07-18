package controllers.strategy;

import models.npc.Zombie;
import controllers.GameController;
import controllers.RandomPicker;

public class KingBehavior implements BehaviorStrategy {

    private float timer = 0;
    private final float INTERVAL = 15.0f; // هر ۱۵ ثانیه یک بار
    private GameController game;
    private RandomPicker picker;

    public KingBehavior(GameController game) {
        this.game = game;
        this.picker = new RandomPicker(game);
    }

    @Override
    public void execute(Zombie zombie, float deltaTime) {
        timer += deltaTime;
        if (timer >= INTERVAL) {
            timer = 0;

            // پیدا کردن یک زامبی معمولی (بدون زره) در شعاع ۴
            Zombie target = picker.pickZombieWithoutArmor(zombie, 4.0f, 10);
            if (target != null && target != zombie) {
                // دادن کلاه‌خود و شانه‌بند به او
                game.addArmorToZombie(target, "crown", 1600);
                game.addArmorToZombie(target, "shoulder", 1600);
            }
        }
    }
}