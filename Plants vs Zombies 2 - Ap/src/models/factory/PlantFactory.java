package models.factory;

import models.factory.builder.PlantBuilder;
import models.entity.Plant;
import models.factory.builder.PlantType;

public class PlantFactory {
    private PlantBuilder plantBuilder;
    public Plant CreatePlant(String input) {
        return null;
    }

    PlantBuilder builder = new PlantBuilder();
    public  Plant CreatePlant(PlantType type) {
        return builder.build(type);
    }


}
