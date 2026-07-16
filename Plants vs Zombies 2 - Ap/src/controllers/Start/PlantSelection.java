package controllers.Start;

import models.entity.Plant;
import models.factory.PlantFactory;

import javax.sound.midi.MidiEvent;
import java.util.concurrent.TimeUnit;

public class PlantSelection {
    PlantFactory factory = new PlantFactory();

    public void showallPlants() {
    }

    public void showavailablePlants() {
    }

    public Plant selectPlant(String plantName) {
        return factory.CreatePlant(plantName);
        /// add an exception signature to this method so if the plant is not in it , It's a wrong decision
    }


    public void removePlant() {
    }

    public void boostPlant() {
    }
}
