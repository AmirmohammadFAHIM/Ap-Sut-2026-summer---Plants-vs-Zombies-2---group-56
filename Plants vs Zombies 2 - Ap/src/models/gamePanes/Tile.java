package models.gamePanes;

public class Tile {
    private static float width;
    private static float height;
    float x , y;
    private TileType tileType;
    private boolean plantable = true;
    private boolean zombieSpawner = false;
    private boolean block =  false;
    private float hp;
    private int line;
    private int col;
    public Tile(TileType tileType , int line , int col) {
        this.tileType = tileType;
        this.plantable = tileType.isPlantable();
        this.zombieSpawner = tileType.isZombieSpawner();
        this.block = tileType.block;
        this.hp = tileType.hp;
        this.line = line;
        this.col = col;

    }

    public static float getHeight() {
        return height;
    }

    public static float getWidth() {
        return width;
    }

    public TileType getTileType() {
        return tileType;
    }

    public static void setWidth(float width) {
        Tile.width = width;
    }

    public static void setHeight(float height) {
        Tile.height = height;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
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

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
