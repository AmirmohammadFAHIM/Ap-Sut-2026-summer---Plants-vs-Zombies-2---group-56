package models.entity;

public enum PlantCategory {

    SunProducer,

    SHOOTER{
        public void setplant(){
           String name = this.plant.getCategory().name();

        }
    };

    private PlantCategory() {
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    Plant plant;




}
