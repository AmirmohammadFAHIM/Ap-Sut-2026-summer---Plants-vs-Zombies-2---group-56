package models.gameadventure;

import models.gamepanes.TileType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public enum Chapters {



    DarkAge{
        {
            specialTiles = new ArrayList<TileType>(Arrays.asList(TileType.NECROMANCY
                    , TileType.DARK_AGE_GRAVE));
        }
    },
    BigWaveBeach{
        {
            specialTiles = new ArrayList<>(Collections.singletonList(TileType.SANDY_TILE));
        }
    },
    FrozenCaves{
        {
            specialTiles = new ArrayList<>(Arrays.asList(TileType.SLIPPERY_DOWN , TileType.SLIPPERY_UP,
                    TileType.FROZEN));
        }
    },
    AncientEgypt{
        {
            specialTiles = new ArrayList<>(Arrays.asList(TileType.EGYPTIAN_GRAVE));
        }
    };

    ArrayList<TileType> specialTiles;
    Chapters(){
        specialTiles = new ArrayList<>();
    }

    public ArrayList<TileType> getSpecialTiles() {
        return specialTiles;
    }
}
