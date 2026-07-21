package controllers.datacontroller;

import java.io.Serializable;

public class Upgrade implements Serializable {
    private String effect;
    private boolean specialFlag;

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public boolean isSpecialFlag() {
        return specialFlag;
    }

    public void setSpecialFlag(boolean specialFlag) {
        this.specialFlag = specialFlag;
    }
}
