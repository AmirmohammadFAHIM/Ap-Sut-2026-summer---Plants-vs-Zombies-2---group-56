package models.GameAdventure;

import models.entity.Zombie;
import models.gamePanes.Tile;
import models.gamePanes.TileType;
import models.games.BaseGame;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

public class GraveSpawner implements  ChapterSpecialEvent {


    public GraveSpawner(BaseGame game){


    }
    @Override
    public void run(BaseGame game, float delta) {
        Iterator<Zombie> iterator = game.getCurrentWave().getZombies().iterator();

        int count = 0;
        for (ArrayList< Tile> row : game.getField().getTiles()){
            for (Tile tile : row){
                if(tile.getTileType() == TileType.NECROMANCY) count += 1;
                Zombie zombie = game.getCurrentWave().getZombies().get(count);
                if(zombie != null){
                    zombie.setLine(tile.getLine());
                    zombie.setTileIndex(tile.getCol());
                    zombie.setX(tile.getX() + Tile.getWidth() / 2);
                    zombie.setY(tile.getY() + Tile.getHeight() / 2);
                }
            }
        }

        /// TODO: implement spawning random graves
        dispose(game);
    }


}
