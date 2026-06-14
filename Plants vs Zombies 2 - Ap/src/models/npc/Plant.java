package models.npc;

import java.util.ArrayList;

public class Plant {


    private int Damage;
    private int hp;
    private int x;
    private int y;
    private int cost;
    private PlantType plantType;
    private ArrayList<PlantTags> tags;
    private boolean frozen = false;
    private boolean cat = false;
    private Graphic graphic;
    public void boost(){}

    public PlantType getPlantType() {
        return plantType;
    }

    public Graphic getGraphic() {
        return graphic;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
