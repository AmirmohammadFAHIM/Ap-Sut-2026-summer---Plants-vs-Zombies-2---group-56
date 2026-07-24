package models.games;

import models.entity.Sun;
import models.entity.Zombie;
import models.gamePanes.Wave;

import java.util.ArrayList;

public class NormalGame extends BaseGame{

    @Override
    public void initGame() {
        /// init field
        initWaves();

    }

    private void initWaves(){
        int wavesCount = 0; ///get it from the file
        int levelBaseHardness = 0;///get it from the file
        ArrayList<Zombie>  zombies = new ArrayList<>();///filtered zombies for this level
        for (int i = 0; i < wavesCount - 1; i++) {
            Wave wave = new Wave();
            wave.setCost(waves.getLast().getCost() * 1.25f);
            wave.initWave(zombies);
            waves.add(wave);
        }

        Wave finalWave = new Wave();
        finalWave.setCost(waves.getLast().getCost() * 2);
        finalWave.initWave(zombies);
    }

    public void updateSuns(float delta){
        for (Sun sun : suns){
            if(sun.getProducer() == null) sun.setRemainingTime(sun.getRemainingTime() - delta);
        }
    }
}
