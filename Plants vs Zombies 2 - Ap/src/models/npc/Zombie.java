package models.npc;

import models.config.ZombieConfig;
import models.config.ZombieData;
import models.npc.ability.Ability;
import models.armor.Armor;
import models.weapon.Weapon;
import controllers.observer.*;
import controllers.GameController;

import java.util.*;

public class Zombie {

    // ====== CORE ======
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
    private float eatTimer = 0;

    // ====== ABILITIES ======
    private final List<Ability> abilities = new ArrayList<>();

    // ====== EFFECTS ======
    private final List<Effect> effects = new ArrayList<>();

    // ====== EQUIPMENT ======
    private final List<Armor> armors = new ArrayList<>();
    private Weapon weapon;

    // ====== OBSERVERS ======
    private final List<BulletObserver> bulletObservers = new ArrayList<>();
    private AllStarObserver allStarObserver;
    private NewspaperObserver newspaperObserver;
    private PassThroughObserver passThroughObserver;

    // ====== CONFIG ======
    private final ZombieConfig config;
    private final ZombieData data;

    // ====== CONSTRUCTOR ======
    public Zombie(String id, ZombieConfig config) {
        this.id = id;
        this.config = config;
        this.objClass = config.getObjclass();
        this.data = config.getObjdata();

        this.hp = data.getHitpoints();
        this.maxHp = data.getHitpoints();
        this.speed = (float) data.getSpeed();
        this.damage = data.getEatDPS();
        this.eatCooldown = calculateEatCooldown();

        initObservers();
        addBulletObservers();
    }

    // ====== INIT ======
    private float calculateEatCooldown() {
        if ("imp".equals(data.getSize()) || "Imp".equals(data.getSize())) return 0.5f;
        if ("ZombieGargantuarProps".equals(objClass)) return 2.0f;
        if ("ZombieModernAllStarProps".equals(objClass)) return 1.0f;
        return data.getSpeed() > 0.25 ? 0.7f : 1.0f;
    }

    private void initObservers() {
        if ("ZombieModernAllStarProps".equals(objClass)) {
            allStarObserver = new AllStarObserver();
        }
        if ("ZombieModernNewspaperProps".equals(objClass)) {
            newspaperObserver = new NewspaperObserver();
        }
        if ("ZombieIceAgeDodoProps".equals(objClass)) {
            passThroughObserver = new PassThroughObserver();
        }
    }

    private void addBulletObservers() {
        if ("ZombieDarkJugglerProps".equals(objClass)) {
            addBulletObserver(new JugglerObserver());
        }
        if (id != null && id.toLowerCase().contains("dragon")) {
            addBulletObserver(new DragonObserver());
        }
        if (data.getZombieArmorProps() != null) {
            for (String armorRef : data.getZombieArmorProps()) {
                if (armorRef.contains("Crown") || armorRef.contains("Parasol")) {
                    addBulletObserver(new ParasolObserver());
                    break;
                }
            }
        }
    }

    // ====== UPDATE ======
    public void update(float deltaTime, GameController controller) {
        if (dead) return;

        updateEffects(deltaTime);

        if (hasEffect(EffectType.POISONED)) {
            applyPoisonDamage();
        }

        if (hasEffect(EffectType.FROZEN)) {
            return;
        }

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

        for (Ability ability : abilities) {
            ability.execute(this, deltaTime, controller);
        }

        if (weapon != null) {
            weapon.update(deltaTime, this);
        }
    }

    // ====== EFFECTS ======
    public void addEffect(Effect effect) {
        if (effect.getType() == EffectType.HYPNOTIZED) {
            removeEffect(EffectType.HYPNOTIZED);
        }
        effects.add(effect);
    }

    public void removeEffect(EffectType type) {
        effects.removeIf(e -> e.getType() == type);
    }

    public boolean hasEffect(EffectType type) {
        return effects.stream().anyMatch(e -> e.getType() == type);
    }

    private void updateEffects(float deltaTime) {
        for (Effect effect : effects) {
            effect.update(deltaTime);
        }
        effects.removeIf(Effect::isExpired);
    }

    private void applyPoisonDamage() {
        this.hp -= 5;
        if (this.hp <= 0) {
            this.hp = 0;
            die();
        }
    }

    public void meltFrozen() {
        removeEffect(EffectType.FROZEN);
    }

    public float getActualSpeed() {
        if (hasEffect(EffectType.FROZEN)) {
            return speed * 0.3f;
        }
        return speed;
    }

    // ====== CORE METHODS ======
    public void move() {
        if (dead) return;

        // PassThroughObserver: Dodo Rider ignores obstacles
        if (passThroughObserver != null && passThroughObserver.canPassThrough(this, null)) {
            x += getActualSpeed() * movingDirection();
            return;
        }

        x += getActualSpeed() * movingDirection();
    }

    public void attack(Plant plant) {
        if (plant == null) return;

        if (hasEffect(EffectType.HYPNOTIZED)) {
            return;
        }

        if ("ZombieDarkWizardProps".equals(objClass)) {
            plant.setCat(true);
            return;
        }

        if ("ZombieCamelDefault".equals(objClass) || "ZombieTurquoiseProps".equals(objClass)) {
            return;
        }

        plant.takeDamage(damage, this);

        if (plant.isDead() && allStarObserver != null) {
            allStarObserver.onPlantKilled(this);
        }
    }

    public void takeDamage(int damage) {
        if (dead) return;

        for (Armor armor : armors) {
            if (armor.isActive()) {
                armor.takeDamage(damage);
                if (armor.isBroken() && "newspaper".equals(armor.getType())) {
                    if (newspaperObserver != null) {
                        newspaperObserver.onArmorBroken(this);
                    }
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

        // Notify all abilities about death
        for (Ability ability : abilities) {
            ability.onDeath(this, controller);
        }

        if (weapon != null) {
            weapon.onOwnerDeath();
        }
    }

    // ====== BULLET OBSERVERS ======
    public void addBulletObserver(BulletObserver observer) {
        bulletObservers.add(observer);
    }

    public void removeBulletObserver(BulletObserver observer) {
        bulletObservers.remove(observer);
    }

    public List<BulletObserver> getBulletObservers() {
        return Collections.unmodifiableList(bulletObservers);
    }

    public void notifyBulletObservers(Bullet bullet) {
        for (BulletObserver observer : bulletObservers) {
            observer.onBulletHit(this, bullet);
            if (!bullet.isActive()) {
                break;
            }
        }
    }

    // ====== ABILITIES ======
    public void addAbility(Ability ability) {
        abilities.add(ability);
    }

    @SuppressWarnings("unchecked")
    public <T extends Ability> T getAbility(Class<T> type) {
        for (Ability ability : abilities) {
            if (type.isInstance(ability)) {
                return (T) ability;
            }
        }
        return null;
    }

    public List<Ability> getAbilities() {
        return Collections.unmodifiableList(abilities);
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

    // ====== PLANT INTERACTION (placeholder) ======
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

    // ====== GETTERS & SETTERS ======
    public String getId() { return id; }
    public String getObjClass() { return objClass; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getDamage() { return damage; }
    public float getSpeed() { return speed; }
    public float getX() { return x; }
    public float getY() { return y; }
    public int getRow() { return row; }
    public float getEatCooldown() { return eatCooldown; }
    public boolean isDead() { return dead; }
    public boolean isFrozen() { return frozen; }
    public boolean isHypnotized() { return hypnotized; }

    public void setHp(int hp) { this.hp = hp; }
    public void setSpeed(float speed) { this.speed = speed; }
    public void setPosition(float x, float y) { this.x = x; this.y = y; }
    public void setRow(int row) { this.row = row; }
    public void setFrozen(boolean frozen) { this.frozen = frozen; }
    public void setHypnotized(boolean hypnotized) { this.hypnotized = hypnotized; }

    public AllStarObserver getAllStarObserver() { return allStarObserver; }
    public NewspaperObserver getNewspaperObserver() { return newspaperObserver; }
    public PassThroughObserver getPassThroughObserver() { return passThroughObserver; }
}