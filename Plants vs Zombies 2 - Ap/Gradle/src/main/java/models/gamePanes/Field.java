package models.gamePanes;

import models.GameAdventure.Chapter;
import models.GameAdventure.Chapters;
import models.GameAdventure.levels.Level;
import models.entity.Moaner;
import models.entity.Plant;
import models.entity.PlantTags;
import models.games.BaseGame;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Field {
    private int width;
    private int height;
    private ArrayList<ArrayList<Tile>> tiles = new ArrayList<>(5);
    private final ArrayList<Moaner>  moaners = new ArrayList<>(5);
    private int WaveLimitColumn;
    private int waterCurrentSurface;

    public Field initField(Chapters chapter , int level){
        for (int i = 0; i < 5; i++) {
            tiles.add(new ArrayList<Tile>());
        }
            initFirstTwoColumns(chapter);
            int specialTiles = 3 * level;
            initSpecials(chapter ,  specialTiles);
           if(chapter == Chapters.BigWaveBeach) {
               initWater();
           }
            initMoaners();
            return this;
    }


    private void initFirstTwoColumns(Chapters chapter){

                for (int i = 0; i < 2 ; i++) {
                    for (int j = 0; j < 5; j++) {
                        TileType type = switch (chapter){
                            case DarkAge -> TileType.DARK_AGE_TILE;
                            case BigWaveBeach -> TileType.BEACH_TILE;
                            case FrozenCaves -> TileType.CAVE_TILE;
                            default -> TileType.EGYPTIAN_TILE;
                        };
                        this.tiles.get(j).add(new Tile(type , j , i));
                    }
                }
            }

            Random rand = new Random();
            private void initSpecials(Chapters chapter , int i){
                    if(i == 0) return;
                    int row  = rand.nextInt(5);
                    int col = rand.nextInt(9);
                    int tile = rand.nextInt(chapter.getSpecialTiles().size());
                    if(tiles.get(row).get(col) == null){
                        tiles.get(row).add(col , tiles.get(row).get(col));
                        i--;
                    }
                    initSpecials(chapter, i);

            }



            private void initWater(){
                for (int i = 8; i > 6 ; i--) {
                    for (int j = 0; j < 5; j++) {
                        tiles.get(j).get(i).setWater(true);
                    }
                }
            }

            public void updateScene(float delta , BaseGame game) {
                for (int i = 0; i < 5; i++) {
                    for (Tile tile : tiles.get(i)) {
                        if(tile.getTileType() == TileType.FROZEN){
                            for (Plant x : game.getPlants_inField()){
                                int dx = Math.abs(x.getTileIndex() - tile.getCol());
                                int dy = Math.abs(x.getLine() - tile.getLine());
                                if(dx <= 1 && dy <= 1 && x.getTags().contains(PlantTags.FIRE)){
                                    tile.setHp(tile.getHp() - delta * 60);
                                }
                            }
                        }
                    }
                }
            }


            private void initMoaners(){
                for (int i = 0; i < 5; i++) {
                    moaners.add(new Moaner(i));
                }
            }


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ArrayList<ArrayList<Tile>> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<ArrayList<Tile>> tiles) {
        this.tiles = tiles;
    }

    public ArrayList<Moaner> getMoaners() {
        return moaners;
    }

    public int getWaveLimitColumn() {
        return WaveLimitColumn;
    }

    public void setWaveLimitColumn(int waveLimitColumn) {
        WaveLimitColumn = waveLimitColumn;
    }

    public Random getRand() {
        return rand;
    }

    public void setRand(Random rand) {
        this.rand = rand;
    }

    public int getWaterCurrentSurface() {
        return waterCurrentSurface;
    }

    public void setWaterCurrentSurface(int waterCurrentSurface) {
        this.waterCurrentSurface = waterCurrentSurface;
    }

    public Tile getTileByCoordinats(int x, int y){
                return tiles.get(y).get(x);
    }
}
