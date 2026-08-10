package models.gameadventure;

import models.entity.Zombie;
import models.gamepanes.Tile;
import models.gamepanes.TileType;
import models.games.BaseGame;

import java.util.ArrayList;
import java.util.Iterator;

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
