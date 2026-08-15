package view.gameview;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import models.entity.Plant;
import pvz.libpvz.pam.PamPlayer;

import java.io.File;

public class PlantRenderer {
    static String path = "768/FULL/PLANT/";
    public void render(Plant plant , float stateTime, PamPlayer player, SpriteBatch batch){
        String path = PlantRenderer.path + plant.getType() + "/" +
             plant.getType() + ".PAM";
        player.draw(batch , "idle",path,plant.getStateTime() , plant.getX() , plant.getY(),false);

    }
}
