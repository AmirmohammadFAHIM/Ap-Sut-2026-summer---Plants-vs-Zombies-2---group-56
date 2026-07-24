package models.factory.builder;

import models.entity.Sun;
import models.games.BaseGame;
import models.utils.Result;

import java.util.Random;

public class SunBuilder {
    private float timePassed = 0f;
    private float cooldownTillNextSun;

    public Result SunLight(float delta , BaseGame game) {
        this.timePassed += delta;
        if(cooldownTillNextSun <= 0){
            cooldownTillNextSun = Math.max(6 + 0.05f * timePassed , 12);
            Sun product = drop();
            game.getSuns().add(product);
            String type = product.getPrice() == 100 ? "Radio Active" : product.getPrice() == 50 ?
                    "Special" : "Normal";
            return new Result(true , "Sun " + type + " is dropping at ("
                    + product.getX() + " , " + product.getY() + ")" , null);
        }else{
            cooldownTillNextSun -= delta;
        }
        return null;
    }

    private Sun drop() {
        Random  rand = new Random();
        Sun sun = new Sun();
        int i = rand.nextInt(20) + 1;
        int line = rand.nextInt(5);
        int tileIndex = rand.nextInt(9);
        sun.setLine(line);
        sun.setTileIndex(tileIndex);
        if(i == 9){
            sun.setRadioActive(true);
            sun.setPrice(25);
        }
        else if(i >= 10 && i <= 12) {
            sun.setPrice(100);
        }
        else{
            sun.setPrice(25);
        }
        return sun;
    }
}
