package models.GameAdventure;

import models.games.BaseGame;

public interface ChapterSpecialEvent {
    public void run(BaseGame game ,  float delta);

    public default void dispose(BaseGame game){
        game.setEvent(null);
    };
}

