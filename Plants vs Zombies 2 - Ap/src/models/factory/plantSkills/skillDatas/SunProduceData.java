package models.factory.plantSkills.skillDatas;

import models.entity.Sun;
import models.entity.SunType;

public class SunProduceData {
    private SunType sunType;
    private boolean imadiate;
    public int amount;
    public SunProduceData(int amount){
        this.amount = amount;
        imadiate = false;
    }
    public  SunProduceData(int amount , boolean imadiate){
        this.amount = amount;
        this.imadiate = imadiate;
    }
    public SunType getSunType() {
        return sunType;
    }

    public void setSunType(SunType sunType) {
        this.sunType = sunType;
    }

    public boolean isImadiate() {
        return imadiate;
    }

    public void setImadiate(boolean imadiate) {
        this.imadiate = imadiate;
    }
}
