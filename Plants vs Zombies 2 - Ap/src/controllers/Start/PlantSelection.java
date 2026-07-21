package controllers.Start;

import controllers.datacontroller.Data;
import controllers.datacontroller.SeedPackage;
import models.App;
import models.factory.PlantFactory;
import models.factory.builder.PlantType;

import java.util.ArrayList;

public class PlantSelection {
    PlantFactory factory = new PlantFactory();
    ArrayList<PlantType>  plantsToChoose = new ArrayList<>();

    public PlantSelection(ArrayList<PlantType> plantsToChoose){
        this.plantsToChoose = plantsToChoose;
    }
    public PlantSelection(){
        plantsToChoose.addAll(App.getCurrentuser().getUnlockedPlants());
    }

    public void showallPlants() {
    }

    public void showavailablePlants() {
    }

    public SeedPackage selectPlant(String plantName) {
        try {
            PlantType plantType = PlantType.valueOf(plantName);
            if(!plantsToChoose.contains(plantType)){
                return null;
            }
            return new SeedPackage(plantType , Data.getPlants().get(plantType).getRecharge());
        }catch (Exception e){
            return null;
        }

        /// add an exception signature to this method so if the plant is not in it , It's a wrong decision
    }


    public void removePlant() {
    }

    public void boostPlant() {
    }

    public PlantFactory getFactory() {
        return factory;
    }

    public void setFactory(PlantFactory factory) {
        this.factory = factory;
    }

    public ArrayList<PlantType> getPlantsToChoose() {
        return plantsToChoose;
    }

    public void setPlantsToChoose(ArrayList<PlantType> plantsToChoose) {
        this.plantsToChoose = plantsToChoose;
    }
}
