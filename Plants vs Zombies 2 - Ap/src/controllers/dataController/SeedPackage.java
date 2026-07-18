package controllers.dataController;

import models.factory.builder.PlantType;

public class SeedPackage {
    private PlantType plant;
    private float recharge;
    private boolean available;
    public SeedPackage(PlantType plant, float recharge) {
        this.plant = plant;
        this.recharge = recharge;
        available = false;
    }

    public void update(float delta){
        if(recharge <= 0 && !available){
            available = true;
        }
        else if(recharge > 0) recharge -=  delta;
    }

    public PlantType getPlant() {
        return plant;
    }

    public void setPlant(PlantType plant) {
        this.plant = plant;
    }

    public float getRecharge() {
        return recharge;
    }

    public void setRecharge(float recharge) {
        this.recharge = recharge;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
