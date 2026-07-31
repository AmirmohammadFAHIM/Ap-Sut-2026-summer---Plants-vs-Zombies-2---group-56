package models.factory;

import models.entity.PlantCategory;
import models.entity.PlantTags;
import models.factory.builder.PlantBuilder;
import models.entity.Plant;
import models.factory.builder.PlantType;
import models.factory.plantSkills.skillobserver.AoEObserver;
import models.factory.plantSkills.skillobserver.ShootingObserver;

public class PlantFactory {
    private PlantBuilder plantBuilder;
    public Plant CreatePlant(String input) {
        return null;
    }

    PlantBuilder builder = new PlantBuilder();
    public  Plant CreatePlant(PlantType type) {
        Plant plant = builder.build(type);
        if(plant.getCategory() == PlantCategory.SHOOTER) plant.setSkillObserver(new ShootingObserver());
        else if(plant.getCategory() == PlantCategory.Explosive
        && plant.getTags().contains(PlantTags.AoE)){
            plant.setSkillObserver(new AoEObserver());
        }
        return plant;
    }


}
