package models.npc;

import models.config.ZombieConfig;
import models.config.ZombieData;
import models.armor.Armor;
import models.weapon.Weapon;
import models.behavior.BehaviorStrategy;
import models.behavior.BehaviorFactory;
import controllers.observer.BulletObserver;
import controllers.observer.ParasolObserver;
import controllers.observer.JugglerObserver;
import controllers.observer.DragonObserver;

import java.util.*;

public class Zombie {

    // ====== FIELDS ======
    private final String id;
    private final String objClass;
    private int hp;
    private final int maxHp;
    private int damage;
    private float speed;
    private float x, y;
    private int row;
    private boolean dead;
    private boolean frozen;
    private boolean hypnotized;
    private float eatCooldown;

    private final List<Armor> armors = new ArrayList<>();
    private Weapon weapon;
    private final List<BulletObserver> observers = new ArrayList<>();
    private BehaviorStrategy behavior;

    private final ZombieConfig config;
    private final ZombieData data;
    private float eatTimer = 0;
    private boolean isSlowed = false;
    private boolean isEnraged = false;

    // ====== CONSTRUCTOR ======
    public Zombie(String id, ZombieConfig config) {
        this.id = id;
        this.config = config;
        this.objClass = config.getObjclass();
        this.data = config.getObjdata();

        this.hp = data.getHitpoints();
        this.maxHp = data.getHitpoints();
        this.speed = (float) data.getSpeed();
        this.eatCooldown = calculateEatCooldown();
        this.damage = calculateDamage();

        this.behavior = BehaviorFactory.create(this);
        addObservers();
    }

    // ====== INITIALIZATION ======
    private float calculateEatCooldown() {
        if ("imp".equals(data.getSize()) || "Imp".equals(data.getSize())) {
            return 0.5f;
        }
        if ("ZombieGargantuarProps".equals(objClass)) {
            return 2.0f;
        }
        if ("ZombieModernAllStarProps".equals(objClass)) {
            return 1.0f;
        }
        return data.getSpeed() > 0.25 ? 0.7f : 1.0f;
    }

    private int calculateDamage() {
        if ("ZombieGargantuarProps".equals(objClass)) return 1500;
        if ("ZombieModernAllStarProps".equals(objClass)) return 1500;
        if ("ZombieExplorerProps".equals(objClass)) return 1500;
        if ("ZombieIceAgeTroglobiteProps".equals(objClass)) return 1500;
        if ("ZombieArcadeProps".equals(objClass)) return 2500;
        if ("ZombieCamelDefault".equals(objClass) || "ZombieTurquoiseProps".equals(objClass)) return 1500;
        if (id != null && id.toLowerCase().contains("surfer")) return 1500;
        return data.getEatDPS();
    }

    private void addObservers() {
        if ("ZombieDarkJugglerProps".equals(objClass)) {
            addObserver(new JugglerObserver());
        }
        if (id != null && id.toLowerCase().contains("dragon")) {
            addObserver(new DragonObserver());
        }
        if (data.getZombieArmorProps() != null) {
            for (String armorRef : data.getZombieArmorProps()) {
                if (armorRef.contains("Crown") || armorRef.contains("Parasol")) {
                    addObserver(new ParasolObserver());
                    break;
                }
            }
        }
    }

    // ====== CORE METHODS ======
    public void update(float deltaTime) {
        if (dead || frozen) return;

        move();

        if (reachedPlant()) {
            eatTimer += deltaTime;
            if (eatTimer >= eatCooldown) {
                eatTimer = 0;
                Plant plant = findNextPlant();
                if (plant != null) {
                    attack(plant);
                }
            }
        }

        if (behavior != null) {
            behavior.execute(this, deltaTime);
        }

        if (weapon != null) {
            weapon.update(deltaTime, this);
        }
    }

    public void move() {
        if (dead || frozen) return;
        x += speed * movingDirection();
    }

    public void attack(Plant plant) {
        if (plant == null) return;

        // Wizard: transform to sheep
        if ("ZombieDarkWizardProps".equals(objClass)) {
            plant.setCat(true);
            return;
        }

        // Turquoise: laser handled in behavior, does not eat
        if ("ZombieCamelDefault".equals(objClass) || "ZombieTurquoiseProps".equals(objClass)) {
            return;
        }

        // Default damage
        plant.takeDamage(damage);

        // AllStar: slow down after first kill
        if ("ZombieModernAllStarProps".equals(objClass) && plant.isDead() && !isSlowed) {
            isSlowed = true;
            speed *= 0.5f;
        }
    }

    public void takeDamage(int damage) {
        if (dead) return;

        // Armor absorbs damage first (Decorator pattern)
        for (Armor armor : armors) {
            if (armor.isActive()) {
                armor.takeDamage(damage);
                if (armor.isBroken() && "newspaper".equals(armor.getType()) && !isEnraged) {
                    isEnraged = true;
                    speed *= 4.0f;
                    eatCooldown /= 4.0f;
                }
                return;
            }
        }

        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            die();
        }
    }

    public void die() {
        dead = true;
        if (weapon != null) {
            weapon.onOwnerDeath();
        }
    }

    // ====== OBSERVERS ======
    public void addObserver(BulletObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(BulletObserver observer) {
        observers.remove(observer);
    }

    public List<BulletObserver> getObservers() {
        return Collections.unmodifiableList(observers);
    }

    public void notifyObservers(Bullet bullet) {
        for (BulletObserver observer : observers) {
            observer.onBulletHit(this, bullet);
        }
    }

    // ====== ARMOR ======
    public void addArmor(Armor armor) {
        armors.add(armor);
    }

    public List<Armor> getArmors() {
        return Collections.unmodifiableList(armors);
    }

    public boolean hasArmor() {
        return !armors.isEmpty();
    }

    // ====== WEAPON ======
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public boolean hasWeapon() {
        return weapon != null;
    }

    // ====== BEHAVIOR ======
    public void setBehavior(BehaviorStrategy strategy) {
        this.behavior = strategy;
    }

    public BehaviorStrategy getBehavior() {
        return behavior;
    }

    // ====== PLANT INTERACTION (placeholder - will be implemented by controller) ======
    public boolean reachedPlant() {
        return false;
    }

    public Plant findNextPlant() {
        return null;
    }

    public boolean isNearPlant() {
        return false;
    }

    public Plant getTargetPlant() {
        return null;
    }

    public boolean isNearHouse() {
        return x < 50;
    }

    public int movingDirection() {
        return hypnotized ? -1 : 1;
    }

    // ====== GETTERS ======
    public String getId() {
        return id;
    }

    public String getObjClass() {
        return objClass;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getDamage() {
        return damage;
    }

    public float getSpeed() {
        return speed;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getRow() {
        return row;
    }

    public float getEatCooldown() {
        return eatCooldown;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public boolean isHypnotized() {
        return hypnotized;
    }

    public ZombieConfig getConfig() {
        return config;
    }

    public ZombieData getData() {
        return data;
    }

    // ====== SETTERS ======
    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public void setHypnotized(boolean hypnotized) {
        this.hypnotized = hypnotized;
    }
}