package models.npc;

public enum SunType {
    NORMAL(25 , 8),

    BIG(50 , 8),


    PLUS(75 , 8),

    PREMIUM(100 , 8);



    int amount;
    float remainingTime;

     SunType(int amount , float remainingTime){
        this.amount = amount;
        this.remainingTime = remainingTime;
    }

    public int getAmount() {
        return amount;
    }
    public float getRemainingTime() {
         return remainingTime;
    }
}
