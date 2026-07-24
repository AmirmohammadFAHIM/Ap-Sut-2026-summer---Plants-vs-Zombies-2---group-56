package models.gamePanes;

public enum TileType {

    /// DARK_AGE
    DARK_AGE_TILE{
        @Override
        public boolean isDefault() {
            return true;
        }
    },
    NECROMANCY(1 , false , true , false ),
    DARK_AGE_GRAVE(600 , false , true , true ),

    /// EGYPTIAN TILES
    EGYPTIAN_GRAVE(600 , false , true , true),
    EGYPTIAN_TILE{
        @Override
        public boolean isDefault() {
            return true;
        }
    },

    /// FROZEN CAVES
    CAVE_TILE{
        @Override
        public boolean isDefault() {
            return true;
        }
    },
    SLIPPERY_UP(0 , false , false , false ),
    SLIPPERY_DOWN(0 , false , false , false ),
    FROZEN(600 , false , false , true), /// HAS AN UPDATE FUNCTION

    /// BIG WAVES BEACH
    BEACH_TILE{
        @Override
        public boolean isDefault() {
            return true;
        }
    },
    WATER,
    SANDY_TILE(0 , false , true , false ),;



    float hp = 0;
    boolean plantable =  true;
    boolean zombieSpawner = false;
    boolean block = false;


    TileType(float hp, boolean plantable, boolean zombieSpawner ,  boolean block) {
        this.hp = hp;
        this.plantable = plantable;
        this.zombieSpawner = zombieSpawner;
        this.block = block;
    }
    TileType() {}

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public boolean isPlantable() {
        return plantable;
    }

    public void setPlantable(boolean plantable) {
        this.plantable = plantable;
    }

    public boolean isZombieSpawner() {
        return zombieSpawner;
    }

    public void setZombieSpawner(boolean zombieSpawner) {
        this.zombieSpawner = zombieSpawner;
    }

    public boolean isDefault(){
        return false;
    }
}
