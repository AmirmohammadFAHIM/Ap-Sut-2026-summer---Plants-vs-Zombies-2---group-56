package models.npc;

public enum PlantType {

    SHOOTER{
        public void setplant(){
           String name = this.plant.getPlantType().name();

        }
    };

    private PlantType() {
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    Plant plant;




}
