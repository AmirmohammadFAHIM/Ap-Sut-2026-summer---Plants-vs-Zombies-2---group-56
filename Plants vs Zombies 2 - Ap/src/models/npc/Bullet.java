package models.npc;

public class Bullet {

    private float x, y;
    private float speedX, speedY;
    private float targetX, targetY;
    private int row;

    private int damage;
    private BulletType type;
    private boolean isActive;
    private boolean isLobber;
    private boolean isFire;
    private boolean isIce;
    private boolean isCat;
    private boolean isBuff;

    private Plant targetPlant;
    private Zombie targetZombie;
    private EffectType effect;

    public Bullet(float x, float y, int row, BulletType type) {
        this.x = x;
        this.y = y;
        this.row = row;
        this.type = type;
        this.isActive = true;
        this.damage = 10;
        setPropertiesByType(type);
    }

    private void setPropertiesByType(BulletType type) {
        switch (type) {
            case NORMAL:
                break;
            case FIRE:
                isFire = true;
                damage *= 2;
                break;
            case ICE:
                isIce = true;
                break;
            case CAT:
                isCat = true;
                damage = 0;
                break;
            case BUFF:
                isBuff = true;
                damage = 0;
                break;
        }
    }

    public void update(float deltaTime) {
        if (!isActive) return;

        x += speedX * deltaTime;
        y += speedY * deltaTime;

        if (Math.abs(x - targetX) < 5 && Math.abs(y - targetY) < 5) {
            onHit();
        }

        if (x > 1200 || x < -100 || y < -100 || y > 700) {
            isActive = false;
        }
    }

    private void onHit() {
        if (!isActive) return;

        if (targetPlant != null) {
            handlePlantHit();
        } else if (targetZombie != null) {
            handleZombieHit();
        }

        isActive = false;
    }

    private void handlePlantHit() {
        if (isCat) {
            targetPlant.setCat(true);
        } else {
            // ضربه‌زننده: null است چون گیاه توسط گلوله زده شده
            targetPlant.takeDamage(damage, null);
            if (isIce) {
                targetPlant.addEffect(new Effect(EffectType.FROZEN, 3.0f));
            }
            if (effect == EffectType.POISON) {
                targetPlant.addEffect(new Effect(EffectType.POISON, 5.0f));
            }
        }
    }

    private void handleZombieHit() {
        if (isBuff) {
            // شاه: زره‌دهی به زامبی هدف
        } else {
            targetZombie.takeDamage(damage);
            if (isIce) {
                targetZombie.addEffect(new Effect(EffectType.FROZEN, 3.0f));
            }

            if (effect == EffectType.POISON) {
                targetZombie.addEffect(new Effect(EffectType.POISON, 5.0f));
            }
        }
    }

    // ====== OBSERVER ======
    public void ignore() {
        isActive = false;
    }

    public void deflectBack() {
        this.speedX = -this.speedX;
        this.speedY = -this.speedY;
    }

    // ====== GETTERS & SETTERS ======
    public float getX() { return x; }
    public float getY() { return y; }
    public int getRow() { return row; }
    public int getDamage() { return damage; }
    public BulletType getType() { return type; }
    public boolean isActive() { return isActive; }
    public boolean isLobber() { return isLobber; }
    public boolean isFire() { return isFire; }
    public boolean isIce() { return isIce; }
    public boolean isCat() { return isCat; }
    public boolean isBuff() { return isBuff; }

    public void setLobber(boolean lobber) { this.isLobber = lobber; }
    public void setEffect(EffectType effect) { this.effect = effect; }
    public void setTargetPlant(Plant plant) {
        this.targetPlant = plant;
        setTarget(plant.getX(), plant.getY());
    }
    public void setTargetZombie(Zombie zombie) {
        this.targetZombie = zombie;
        setTarget(zombie.getX(), zombie.getY());
    }

    private void setTarget(float x, float y) {
        this.targetX = x;
        this.targetY = y;
        float dx = targetX - this.x;
        float dy = targetY - this.y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        if (distance > 0) {
            this.speedX = (dx / distance) * 300;
            this.speedY = (dy / distance) * 300;
        }
    }
}