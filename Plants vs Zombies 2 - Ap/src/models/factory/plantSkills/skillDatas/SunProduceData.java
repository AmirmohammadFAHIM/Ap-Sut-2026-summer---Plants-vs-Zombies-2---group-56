package models.factory.plantSkills.skillDatas;

import models.entity.SunType;

public class SunProduceData {
    private SunType sunType;
    private boolean imadiate;

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
