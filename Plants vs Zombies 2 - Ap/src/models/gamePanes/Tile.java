package models.gamePanes;

public class Tile {
    private static float width;
    private static float height;
    private TileType tileType;

    public static float getHeight() {
        return height;
    }

    public static float getWidth() {
        return width;
    }

    public TileType getTileType() {
        return tileType;
    }
}
