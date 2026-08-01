package models.entity;

public enum PlantCategory {



    Mint,

    Explosive,

    StrikeThrough,

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
