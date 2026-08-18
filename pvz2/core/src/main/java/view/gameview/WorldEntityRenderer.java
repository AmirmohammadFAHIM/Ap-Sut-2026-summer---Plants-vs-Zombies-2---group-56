package view.gameview;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;

import controllers.menus.gamecontroller.GameController;
import models.entity.Sun;
import models.factory.builder.PlantType;
import pvz.libpvz.textures.TextureBank;

/**
 * Owns rendering of gameplay entities that live in world/map coordinates.
 *
 * GameView only tells this object when to render and delegates sun hit-testing
 * to it. Concrete renderers/layers stay out of GameView.
 */
public final class WorldEntityRenderer implements Disposable {

    private static final String TAG = "WorldEntityRenderer";

    private final GameController controller;
    private final Rectangle pitchBounds;
    private final Stage stage;

    private PlantRenderer plantRenderer;
    private SunRenderer sunRenderer;

    public WorldEntityRenderer(
        Viewport worldViewport,
        GameController controller,
        Rectangle pitchBounds,
        FileHandle pvzAssetsRoot,
        TextureBank sharedTextureBank,
        Drawable sunFallback
    ) {
        if (worldViewport == null) {
            throw new IllegalArgumentException("worldViewport cannot be null");
        }

        if (controller == null) {
            throw new IllegalArgumentException("controller cannot be null");
        }

        if (pitchBounds == null
            || pitchBounds.width <= 0f
            || pitchBounds.height <= 0f) {

            throw new IllegalArgumentException(
                "pitchBounds must contain a valid playable rectangle"
            );
        }

        this.controller = controller;
        this.pitchBounds = new Rectangle(pitchBounds);
        this.stage = new Stage(worldViewport);

        initialisePlants(pvzAssetsRoot);
        initialiseSuns(sharedTextureBank, sunFallback);
    }

    private void initialisePlants(FileHandle pvzAssetsRoot) {
        if (pvzAssetsRoot == null) {
            Gdx.app.error(
                TAG,
                "PVZ assets were not found; plant world rendering is disabled."
            );
            return;
        }

        try {
            plantRenderer = new PlantRenderer(pvzAssetsRoot);

            PlantLayer plantLayer = new PlantLayer(
                controller,
                plantRenderer,
                false
            );

            plantLayer.setBounds(
                pitchBounds.x,
                pitchBounds.y,
                pitchBounds.width,
                pitchBounds.height
            );

            // Plants are added before suns, therefore suns render on top.
            stage.addActor(plantLayer);

        } catch (RuntimeException e) {
            if (plantRenderer != null) {
                try {
                    plantRenderer.dispose();
                } catch (RuntimeException ignored) {
                }
                plantRenderer = null;
            }

            Gdx.app.error(
                TAG,
                "Failed to initialise plant rendering.",
                e
            );
        }
    }

    private void initialiseSuns(
        TextureBank sharedTextureBank,
        Drawable sunFallback
    ) {
        sunRenderer = new SunRenderer(
            sharedTextureBank,
            sunFallback
        );

        SunLayer sunLayer = new SunLayer(
            controller,
            sunRenderer
        );

        sunLayer.setBounds(
            pitchBounds.x,
            pitchBounds.y,
            pitchBounds.width,
            pitchBounds.height
        );

        stage.addActor(sunLayer);
    }

    /**
     * The caller must already have applied the world viewport/camera.
     */
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    /**
     * Keeps SunRenderer encapsulated while preserving GameView's existing
     * world-coordinate sun collection behaviour.
     */
    public Sun hitTestSun(float worldX, float worldY) {
        if (sunRenderer == null) {
            return null;
        }

        return sunRenderer.hitTest(
            controller.getGame().getSuns(),
            worldX,
            worldY,
            pitchBounds
        );
    }

    public void preloadPlants(Iterable<PlantType> plantTypes) {
        if (plantRenderer == null || plantTypes == null) {
            return;
        }

        for (PlantType type : plantTypes) {
            if (type != null) {
                plantRenderer.preload(type);
            }
        }
    }

    @Override
    public void dispose() {
        stage.dispose();

        if (plantRenderer != null) {
            plantRenderer.dispose();
            plantRenderer = null;
        }

        // SunRenderer does not own sharedTextureBank.
        sunRenderer = null;
    }
}
