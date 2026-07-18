package models.gamePanes;

import models.GameAdventure.Chapter;
import models.GameAdventure.Chapters;
import models.GameAdventure.levels.Level;
import models.entity.Moaner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Field {
    private int width;
    private int height;
    private ArrayList<ArrayList<Tile>> tiles;
    private final ArrayList<Moaner>  moaners = new ArrayList<>(5);
    private int WaveLimitColumn;
    private int waterCurrentSurface;

    public void initField(Chapters chapter , int level){
            initFirstTwoColumns(chapter);
            int specialTiles = 3 * level;
            initSpecials(chapter ,  specialTiles);
           if(chapter == Chapters.BigWaveBeach) initWater();
        for (int i = 0; i < 5; i++) {
            Moaner moaner = new Moaner(i);
            moaners.add(moaner);
        }
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
}
