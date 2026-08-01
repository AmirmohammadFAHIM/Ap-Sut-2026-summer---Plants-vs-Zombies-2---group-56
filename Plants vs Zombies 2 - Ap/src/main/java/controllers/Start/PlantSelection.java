package controllers.Start;

import controllers.datacontroller.Data;
import controllers.datacontroller.SeedPackage;
import models.App;
import models.factory.PlantFactory;
import models.factory.builder.PlantBuilder;
import models.factory.builder.PlantType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class PlantSelection {
    PlantFactory factory = new PlantFactory();
    ArrayList<PlantType>  plantsToChoose = new ArrayList<>();

    public PlantSelection(ArrayList<PlantType> plantsToChoose){
        this.plantsToChoose = plantsToChoose;
    }
    public PlantSelection(){
        plantsToChoose.addAll(App.getCurrentuser().getUnlockedPlants());
    }


    PlantBuilder updates = new  PlantBuilder();

    public SeedPackage selectPlant(String plantName) {
        try {
            PlantType plantType = PlantType.valueOf(plantName);

            if(!plantsToChoose.contains(plantType)){
                return null;
            }

            System.out.println("Kobe");
            int level = App.getCurrentuser().getLevels().get(plantType);
            System.out.println("level");
            float recharge = updates.upgradedCooldown(plantType , level);
            System.out.println("recharge");
            float cost =  updates.upgradedCost(plantType , level);
            System.out.println("cost");
            return new SeedPackage(plantType ,recharge , cost );
        }catch (Exception e){
            return null;
        }

    }


    public String removePlant(HashMap<PlantType , SeedPackage> packets, PlantType toRemove) {
       try {
           packets.remove(toRemove);
           return toRemove + " has been removed";
       }catch (Exception e){
           return "Plant doesn't exist on the slots.";
       }
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
