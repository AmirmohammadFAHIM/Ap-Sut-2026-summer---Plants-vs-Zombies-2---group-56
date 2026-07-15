package models.entity;

public class Sun {
    private int price;
    private float remainingTime;
    private float x;
    private float y;
    public Sun(int price, float remainingTime, float x, float y) {
        this.price = price;
        this.remainingTime = remainingTime;
        this.x = x;
        this.y = y;
    }

    public void pickup(){}
    public Sun(int price, int remainingTime){}
    public void updateTime(){}
}

