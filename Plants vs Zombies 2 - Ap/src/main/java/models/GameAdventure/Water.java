package models.GameAdventure;

import models.Constants;
import models.entity.Plant;
import models.entity.PlantTags;
import models.gamePanes.Field;
import models.games.BaseGame;

import java.util.Random;

public class Water implements  ChapterSpecialEvent
{
    public Water(BaseGame game) {

    }
    float waterSurfaceChange = 10f;
    Random rand = new Random();
    @Override
    public void run(BaseGame game, float delta) {
        if(waterSurfaceChange <= 0){
            int newSurface = rand.nextInt(8 -
                    game.getField().getWaveLimitColumn()) +
                    game.getField().getWaveLimitColumn();
            int difference = newSurface - game.getField().getWaveLimitColumn();
            game.getField().setWaterCurrentSurface(newSurface);
            fixTiles(game.getField() , difference);
            WaterEffect(game);
            waterSurfaceChange = rand.nextFloat(Constants.WaterSurfaceChangeTime);
        }else{
            waterSurfaceChange -= delta;
        }


    }

    private void fixTiles(Field field , int difference) {
        if(difference > 0){
            for (int i = field.getWaterCurrentSurface()
                 ; i < field.getWaterCurrentSurface() - difference ; i--) {
                for (int j = 0; j < 5; j++) {
                    field.getTiles().get(j).get(i).setWater(false);
                }
            }
        }
        else{
            for (int i = field.getWaterCurrentSurface()
                 ; i < field.getWaterCurrentSurface() + difference ; i++) {
                for (int j = 0; j < 5; j++) {
                    field.getTiles().get(j).get(i).setWater(true);
                }
            }
        }
    }

    private void WaterEffect(BaseGame game) {
        for (Plant x : game.getPlantsInField()){
            if(x.getTileIndex() >= game.getField().getWaterCurrentSurface()){
                if(!x.onLilyPad && !x.getTags().contains(PlantTags.WATER)){
                    x.dispose(game);
                }
            }
        }
    }


}
