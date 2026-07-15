package models.entity;

public class Seed {

    private Plant plant;
    private float growthRate;


    public Seed(Plant plant, boolean isRandom) {
        if(isRandom) {
            RandomSeed();
        }
        else{
            this.plant = plant;
        }
    }

    public void RandomSeed(){
    }

    public void makeSeed(Plant plant){
    }

    public void grow(){

    }
}
