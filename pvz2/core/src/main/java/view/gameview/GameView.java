package view.gameview;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import controllers.menus.gamecontroller.GameController;
import models.gameadventure.Chapters;
import models.gameadventure.levels.Level;
import models.factory.builder.PlantType;
import models.games.BaseGame;
import models.games.specialgames.PlantWhatYouGet;
import models.utils.RegexHelper;
import view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameView extends View {
    private final GameController controller;
    private Stage stage;
    private Image background;
    private FitViewport  viewport;

    public GameView(Chapters chapter , Level level) {
        this.controller = new GameController( chapter, level);
        this.menu =  this.controller;


    }




    @Override
    public void show() {
        stage = new Stage();
        ToolsStack toolsStack = new ToolsStack(controller);
        toolsStack.setFillParent(true);
        stage.addActor(toolsStack);
        viewport = new FitViewport(1028,960);
    }

    @Override
    public void render(float delta) {
        controller.playGame(delta);

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
