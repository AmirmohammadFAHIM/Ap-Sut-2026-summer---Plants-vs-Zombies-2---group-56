package view.gameview;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import controllers.menus.gamecontroller.GameController;
import models.App;

import java.util.ArrayList;

public class ToolsStack extends Table implements InputProcessor {
    public enum MouseState {PlantSelection , Shovel , Normal}
    MouseState state =  MouseState.Normal;
    private GameController controller;
    ArrayList<ImageButton> plants;
    public ToolsStack(GameController controller) {
        this.controller = controller;
        this.setFillParent(true);
        ImageButton shovel = new ImageButton(App.skin,"ingame_shovel");
        shovel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                state = MouseState.Shovel;
            }
        });
        ImageButton pause = new  ImageButton(App.skin,"ingame_pause");
        pause.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                App.getScreen().pause();
            }
        });
        ImageButton speed =  new ImageButton(App.skin,"ingame_2x");
        ImageButton settings =  new ImageButton(App.skin,"settings");
        ProgressBar wavesProgress ; // should be custom , because we need some flags on it , according to level.


    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
