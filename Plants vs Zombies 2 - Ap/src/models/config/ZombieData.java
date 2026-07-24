package models.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ZombieData {

    private int Hitpoints;
    private double Speed;
    private int EatDPS;
    private int Cost;
    private int WavePointCost;
    private int Weight;
    private List<String> ZombieArmorProps;
    private String Size;
    private String ImpType;
    private double HealthThresholdThrowImp;
    private int SmashDamage;
    private double ImpFlightTime;
    private int ImpTargetColumn;
    private int MaxClaimedSunCurrency;
    private int MaxTorchReach;
    private Map<String, Object> PlantsToEat;
    private int Ammo;
    private int NumberOfTombsToSpawn;
    private double TimeBetweenRaisings;
    private int FarAttackRange;
    private int NearAttackRange;
    private int SnowballsPerBarrage;
    private int NumberOfIceblocksToSpawnWith;
    private int MaximumGridSquaresToFlyOver;
    private Map<String, Object> PlantsToFlyOver;
    private Map<String, Object> GridItemsToFlyOver;
    private int CastingAreaMinRange;
    private int CastingAreaMaxRange;
    private double DelayBetweenCasting;
    private Map<String, Object> DamageWhileSubmerged;
    private Map<String, Object> TargetByIncludelist;
    private int CatchArcDegrees;
    private int MaxProjectilesToJuggle;
    private List<String> JuggleableProjectiles;
    private double DelayBetweenKnightings;
    private int KnightingAreaX;
    private int KnightingAreaY;
    private List<String> ValidKnightTargets;
    private double RunningSpeedScale;
    private double EnragedSpeedScale;
    private int EnragedDamageScale;
    private double FastMoveSpeed;
    private double LaunchCountdown;
    private double StunTime;
    private List<Map<String, Object>> Stages;
    private List<Map<String, String>> ZombieStats;
    private int FireDamageMultiplier;
    private Map<String, Object> ScaledProps;

    // ====== NEW: Abilities ======
    private List<AbilityConfig> Abilities;

    // ====== GETTERS & SETTERS ======
    public int getHitpoints() { return Hitpoints; }
    public void setHitpoints(int hitpoints) { Hitpoints = hitpoints; }

    public double getSpeed() { return Speed; }
    public void setSpeed(double speed) { Speed = speed; }

    public int getEatDPS() { return EatDPS; }
    public void setEatDPS(int eatDPS) { EatDPS = eatDPS; }

    public int getCost() { return Cost; }
    public void setCost(int cost) { Cost = cost; }

    public int getWavePointCost() { return WavePointCost; }
    public void setWavePointCost(int wavePointCost) { WavePointCost = wavePointCost; }

    public int getWeight() { return Weight; }
    public void setWeight(int weight) { Weight = weight; }

    public List<String> getZombieArmorProps() { return ZombieArmorProps; }
    public void setZombieArmorProps(List<String> zombieArmorProps) { ZombieArmorProps = zombieArmorProps; }

    public String getSize() { return Size; }
    public void setSize(String size) { Size = size; }

    public String getImpType() { return ImpType; }
    public void setImpType(String impType) { ImpType = impType; }

    public double getHealthThresholdThrowImp() { return HealthThresholdThrowImp; }
    public void setHealthThresholdThrowImp(double healthThresholdThrowImp) { HealthThresholdThrowImp = healthThresholdThrowImp; }

    public int getSmashDamage() { return SmashDamage; }
    public void setSmashDamage(int smashDamage) { SmashDamage = smashDamage; }

    public double getImpFlightTime() { return ImpFlightTime; }
    public void setImpFlightTime(double impFlightTime) { ImpFlightTime = impFlightTime; }

    public int getImpTargetColumn() { return ImpTargetColumn; }
    public void setImpTargetColumn(int impTargetColumn) { ImpTargetColumn = impTargetColumn; }

    public int getMaxClaimedSunCurrency() { return MaxClaimedSunCurrency; }
    public void setMaxClaimedSunCurrency(int maxClaimedSunCurrency) { MaxClaimedSunCurrency = maxClaimedSunCurrency; }

    public int getMaxTorchReach() { return MaxTorchReach; }
    public void setMaxTorchReach(int maxTorchReach) { MaxTorchReach = maxTorchReach; }

    public Map<String, Object> getPlantsToEat() { return PlantsToEat; }
    public void setPlantsToEat(Map<String, Object> plantsToEat) { PlantsToEat = plantsToEat; }

    public int getAmmo() { return Ammo; }
    public void setAmmo(int ammo) { Ammo = ammo; }

    public int getNumberOfTombsToSpawn() { return NumberOfTombsToSpawn; }
    public void setNumberOfTombsToSpawn(int numberOfTombsToSpawn) { NumberOfTombsToSpawn = numberOfTombsToSpawn; }

    public double getTimeBetweenRaisings() { return TimeBetweenRaisings; }
    public void setTimeBetweenRaisings(double timeBetweenRaisings) { TimeBetweenRaisings = timeBetweenRaisings; }

    public int getFarAttackRange() { return FarAttackRange; }
    public void setFarAttackRange(int farAttackRange) { FarAttackRange = farAttackRange; }

    public int getNearAttackRange() { return NearAttackRange; }
    public void setNearAttackRange(int nearAttackRange) { NearAttackRange = nearAttackRange; }

    public int getSnowballsPerBarrage() { return SnowballsPerBarrage; }
    public void setSnowballsPerBarrage(int snowballsPerBarrage) { SnowballsPerBarrage = snowballsPerBarrage; }

    public int getNumberOfIceblocksToSpawnWith() { return NumberOfIceblocksToSpawnWith; }
    public void setNumberOfIceblocksToSpawnWith(int numberOfIceblocksToSpawnWith) { NumberOfIceblocksToSpawnWith = numberOfIceblocksToSpawnWith; }

    public int getMaximumGridSquaresToFlyOver() { return MaximumGridSquaresToFlyOver; }
    public void setMaximumGridSquaresToFlyOver(int maximumGridSquaresToFlyOver) { MaximumGridSquaresToFlyOver = maximumGridSquaresToFlyOver; }

    public Map<String, Object> getPlantsToFlyOver() { return PlantsToFlyOver; }
    public void setPlantsToFlyOver(Map<String, Object> plantsToFlyOver) { PlantsToFlyOver = plantsToFlyOver; }

    public Map<String, Object> getGridItemsToFlyOver() { return GridItemsToFlyOver; }
    public void setGridItemsToFlyOver(Map<String, Object> gridItemsToFlyOver) { GridItemsToFlyOver = gridItemsToFlyOver; }

    public int getCastingAreaMinRange() { return CastingAreaMinRange; }
    public void setCastingAreaMinRange(int castingAreaMinRange) { CastingAreaMinRange = castingAreaMinRange; }

    public int getCastingAreaMaxRange() { return CastingAreaMaxRange; }
    public void setCastingAreaMaxRange(int castingAreaMaxRange) { CastingAreaMaxRange = castingAreaMaxRange; }

    public double getDelayBetweenCasting() { return DelayBetweenCasting; }
    public void setDelayBetweenCasting(double delayBetweenCasting) { DelayBetweenCasting = delayBetweenCasting; }

    public Map<String, Object> getDamageWhileSubmerged() { return DamageWhileSubmerged; }
    public void setDamageWhileSubmerged(Map<String, Object> damageWhileSubmerged) { DamageWhileSubmerged = damageWhileSubmerged; }

    public Map<String, Object> getTargetByIncludelist() { return TargetByIncludelist; }
    public void setTargetByIncludelist(Map<String, Object> targetByIncludelist) { TargetByIncludelist = targetByIncludelist; }

    public int getCatchArcDegrees() { return CatchArcDegrees; }
    public void setCatchArcDegrees(int catchArcDegrees) { CatchArcDegrees = catchArcDegrees; }

    public int getMaxProjectilesToJuggle() { return MaxProjectilesToJuggle; }
    public void setMaxProjectilesToJuggle(int maxProjectilesToJuggle) { MaxProjectilesToJuggle = maxProjectilesToJuggle; }

    public List<String> getJuggleableProjectiles() { return JuggleableProjectiles; }
    public void setJuggleableProjectiles(List<String> juggleableProjectiles) { JuggleableProjectiles = juggleableProjectiles; }

    public double getDelayBetweenKnightings() { return DelayBetweenKnightings; }
    public void setDelayBetweenKnightings(double delayBetweenKnightings) { DelayBetweenKnightings = delayBetweenKnightings; }

    public int getKnightingAreaX() { return KnightingAreaX; }
    public void setKnightingAreaX(int knightingAreaX) { KnightingAreaX = knightingAreaX; }

    public int getKnightingAreaY() { return KnightingAreaY; }
    public void setKnightingAreaY(int knightingAreaY) { KnightingAreaY = knightingAreaY; }

    public List<String> getValidKnightTargets() { return ValidKnightTargets; }
    public void setValidKnightTargets(List<String> validKnightTargets) { ValidKnightTargets = validKnightTargets; }

    public double getRunningSpeedScale() { return RunningSpeedScale; }
    public void setRunningSpeedScale(double runningSpeedScale) { RunningSpeedScale = runningSpeedScale; }

    public double getEnragedSpeedScale() { return EnragedSpeedScale; }
    public void setEnragedSpeedScale(double enragedSpeedScale) { EnragedSpeedScale = enragedSpeedScale; }

    public int getEnragedDamageScale() { return EnragedDamageScale; }
    public void setEnragedDamageScale(int enragedDamageScale) { EnragedDamageScale = enragedDamageScale; }

    public double getFastMoveSpeed() { return FastMoveSpeed; }
    public void setFastMoveSpeed(double fastMoveSpeed) { FastMoveSpeed = fastMoveSpeed; }

    public double getLaunchCountdown() { return LaunchCountdown; }
    public void setLaunchCountdown(double launchCountdown) { LaunchCountdown = launchCountdown; }

    public double getStunTime() { return StunTime; }
    public void setStunTime(double stunTime) { StunTime = stunTime; }

    public List<Map<String, Object>> getStages() { return Stages; }
    public void setStages(List<Map<String, Object>> stages) { Stages = stages; }

    public List<Map<String, String>> getZombieStats() { return ZombieStats; }
    public void setZombieStats(List<Map<String, String>> zombieStats) { ZombieStats = zombieStats; }

    public int getFireDamageMultiplier() { return FireDamageMultiplier; }
    public void setFireDamageMultiplier(int fireDamageMultiplier) { FireDamageMultiplier = fireDamageMultiplier; }

    public Map<String, Object> getScaledProps() { return ScaledProps; }
    public void setScaledProps(Map<String, Object> scaledProps) { ScaledProps = scaledProps; }

    public List<AbilityConfig> getAbilities() { return Abilities; }
    public void setAbilities(List<AbilityConfig> abilities) { Abilities = abilities; }
}