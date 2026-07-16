package models.entity;

public class Moaner {
    private int line;
    private static float width;
    private static float height;
    private float x = - width ;
    private float y;
    private static float V = 150f;
    private boolean on = false;
    public void run(){}
    public Moaner(int line){
        this.line = line;
        this.y = line * height;
    }

}
