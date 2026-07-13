package models.gamePanes;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Field {
    private int width;
    private int height;
    private ArrayList<ArrayList<Tile>> tiles;

    /*public void initField(){

    };*/

    public void updateField(){


    };

    public void Locactions(){

    };

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ArrayList<ArrayList<Tile>> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<ArrayList<Tile>> tiles) {
        this.tiles = tiles;
    }
}
