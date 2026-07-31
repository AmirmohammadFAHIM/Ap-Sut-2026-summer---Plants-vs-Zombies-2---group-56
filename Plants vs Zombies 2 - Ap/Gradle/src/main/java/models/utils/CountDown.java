package models.utils;

import java.time.*;

public class CountDown {
    private LocalDateTime targetTime;
    private int countingHours;


    public CountDown(int hours) {
        this.countingHours = hours;
        this.targetTime = LocalDateTime.now().plusHours(hours);
    }

    public int getRemainingHours() {
        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(targetTime) || now.equals(targetTime)) {
            return 0;
        }

        return (int)Duration.between(now, targetTime).toHours();
    }

    public void setCountingHours(int time){
        this.countingHours = time;
    }
}