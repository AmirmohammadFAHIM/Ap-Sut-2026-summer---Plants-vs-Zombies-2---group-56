package models.gamePanes;

public class Tile {
    private static float width;
    private static float height;
    private TileType tileType;
    private boolean plantable = true;

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
}
