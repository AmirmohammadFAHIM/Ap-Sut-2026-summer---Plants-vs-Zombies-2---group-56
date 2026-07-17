package models.entity;

public enum SunType {




    PREMIUM(100 , 8 , null),

    PLUS(75 , 8 , PREMIUM),

    BIG(50 , 8 , PLUS),


    NORMAL(25 , 8){};



    int amount;
    float remainingTime;
    SunType next;

     SunType(int amount , float remainingTime , SunType next){
        this.amount = amount;
        this.remainingTime = remainingTime;
        this.next = next;
    }

    public int getAmount() {
        return amount;
    }
    public float getRemainingTime() {
         return remainingTime;
    }
}
