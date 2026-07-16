package models.GameAdventure;

import models.games.BaseGame;

public interface ChapterSpecialEvent {
    public void run(BaseGame game ,  float delta);

    public void dispose(BaseGame game);
}

