package models.entity;

public class Effect {

    private final EffectType type;
    private float duration;
    private float timer;

    public Effect(EffectType type, float duration) {
        this.type = type;
        this.duration = duration;
        this.timer = duration;
    }

    public void update(float deltaTime) {
        if (duration > 0) {
            timer -= deltaTime;
        }
    }

    public boolean isExpired() {
        return duration > 0 && timer <= 0;
    }

    public boolean isPermanent() {
        return duration == 0;
    }

    // ====== GETTERS ======
    public EffectType getType() { return type; }
    public float getDuration() { return duration; }
    public float getTimer() { return timer; }
    public float getRemainingTime() { return Math.max(0, timer); }
}