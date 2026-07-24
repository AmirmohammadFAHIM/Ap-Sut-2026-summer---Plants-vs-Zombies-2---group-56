package models.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AbilityConfig {
    private String type;
    private String bulletType;
    private float cooldown;
    private float range;
    private int damage;
    private String moveType;
    private String spawnType;
    private int count;
    private float healthThreshold;
    private int maxStolenSun;
    private float stealRate;
    private float multiplier;
    private String triggerType;

    // ====== GETTERS & SETTERS ======
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getBulletType() { return bulletType; }
    public void setBulletType(String bulletType) { this.bulletType = bulletType; }

    public float getCooldown() { return cooldown; }
    public void setCooldown(float cooldown) { this.cooldown = cooldown; }

    public float getRange() { return range; }
    public void setRange(float range) { this.range = range; }

    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }

    public String getMoveType() { return moveType; }
    public void setMoveType(String moveType) { this.moveType = moveType; }

    public String getSpawnType() { return spawnType; }
    public void setSpawnType(String spawnType) { this.spawnType = spawnType; }

    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }

    public float getHealthThreshold() { return healthThreshold; }
    public void setHealthThreshold(float healthThreshold) { this.healthThreshold = healthThreshold; }

    public int getMaxStolenSun() { return maxStolenSun; }
    public void setMaxStolenSun(int maxStolenSun) { this.maxStolenSun = maxStolenSun; }

    public float getStealRate() { return stealRate; }
    public void setStealRate(float stealRate) { this.stealRate = stealRate; }

    public float getMultiplier() { return multiplier; }
    public void setMultiplier(float multiplier) { this.multiplier = multiplier; }

    public String getTriggerType() { return triggerType; }
    public void setTriggerType(String triggerType) { this.triggerType = triggerType; }
}