package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import controllers.datacontroller.Data;
import controllers.menus.gamecontroller.PlayMenu;
import models.App;
import models.User;
import models.gameadventure.Chapters;
import models.gameadventure.levels.Level;

import pvz.libpvz.textures.ResourceIndex;
import pvz.libpvz.textures.TextureBank;
import pvz.skin.PvzSkin;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class PlayView extends View {

    // -------------------------------------------------------------------------
    // Screen
    // -------------------------------------------------------------------------

    private static final float VIRTUAL_WIDTH = 1280f;
    private static final float VIRTUAL_HEIGHT = 720f;

    private static final String ASSET_RESOLUTION = "768";

    /*
     * Order of chapters in the adventure map.
     *
     * Do NOT use Chapters.values() here because enum order in the project is:
     * DarkAge, BigWaveBeach, FrozenCaves, AncientEgypt
     *
     * while our actual game progression is:
     * AncientEgypt -> FrozenCaves -> BigWaveBeach -> DarkAge
     */
    private static final Chapters[] CHAPTER_ORDER = {
        Chapters.AncientEgypt,
        Chapters.FrozenCaves,
        Chapters.BigWaveBeach,
        Chapters.DarkAge
    };

    /*
     * Fixed slot centers.
     *
     * We intentionally show all four chapters at the same time instead of
     * pushing unused worlds outside the screen.
     */
    private static final float[] WORLD_SLOT_X = {
        165f,
        480f,
        800f,
        1115f
    };

    private final PlayMenu playMenu;

    private StageHolder stageHolder;

    private Skin skin;
    private TextureBank textureBank;
    private FileHandle pvzAssetsRoot;

    private Chapters selectedChapter;

    private Group worldGroup;
    private Table levelPanel;
    private Label selectedChapterLabel;

    private TextureRegion backgroundRegion;
    private TextureRegion lockRegion;

    private final Map<Chapters, TextureRegion> chapterRegions =
        new EnumMap<>(Chapters.class);

    private boolean disposed = false;


    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    public PlayView() {
        playMenu = new PlayMenu();

        // View already owns "menu".
        this.menu = playMenu;
    }


    // -------------------------------------------------------------------------
    // LibGDX Screen lifecycle
    // -------------------------------------------------------------------------

    @Override
    public void show() {
        disposed = false;

        skin = PvzSkin.get();

        stageHolder = new StageHolder();

        Gdx.input.setInputProcessor(stageHolder.stage);

        User user = App.getCurrentuser();

        if (user != null && user.getChapter() != null) {
            selectedChapter = user.getChapter();
        } else {
            selectedChapter = Chapters.AncientEgypt;
        }

        /*
         * Keep PlayMenu.currentChapter synchronized with the visual selection.
         * We intentionally ignore its String output.
         */
        playMenu.changeChapter(selectedChapter);

        initialisePvzAssets();

        loadMenuAssets();

        buildUI();
    }


    @Override
    public void render(float delta) {
        if (disposed || stageHolder == null) {
            return;
        }

        ScreenUtils.clear(
            0.025f,
            0.17f,
            0.20f,
            1f
        );

        /*
         * Required by libPVZ when async atlas loads exist.
         * Harmless for synchronously loaded regions too.
         */
        if (textureBank != null) {
            textureBank.update();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            goBack();
            return;
        }

        stageHolder.stage.act(delta);
        stageHolder.stage.draw();
    }


    @Override
    public void resize(int width, int height) {
        if (stageHolder != null) {
            stageHolder.viewport.update(width, height, true);
        }
    }


    @Override
    public void pause() {
    }


    @Override
    public void resume() {
    }


    @Override
    public void hide() {
        if (stageHolder != null &&
            Gdx.input.getInputProcessor() == stageHolder.stage) {

            Gdx.input.setInputProcessor(null);
        }

        /*
         * Game.setScreen() calls hide() while a click event may still be
         * travelling through Scene2D.
         *
         * Therefore dispose on the next application tick instead of destroying
         * the Stage in the middle of the ClickListener.
         */
        if (!disposed) {
            Gdx.app.postRunnable(this::dispose);
        }
    }


    @Override
    public void dispose() {
        if (disposed) {
            return;
        }

        disposed = true;

        if (stageHolder != null) {
            stageHolder.stage.dispose();
            stageHolder = null;
        }

        if (textureBank != null) {
            textureBank.dispose();
            textureBank = null;
        }

        chapterRegions.clear();

        backgroundRegion = null;
        lockRegion = null;
    }


    // -------------------------------------------------------------------------
    // UI construction
    // -------------------------------------------------------------------------

    private void buildUI() {
        stageHolder.stage.clear();

        buildBackground();
        buildTopHud();
        buildWorldArea();
        buildLevelPanel();

        refreshWorlds(false);
        refreshLevels(false);
    }


    private void buildBackground() {
        if (backgroundRegion == null) {
            return;
        }

        Image background = new Image(backgroundRegion);

        background.setScaling(Scaling.fill);
        background.setBounds(
            0f,
            0f,
            VIRTUAL_WIDTH,
            VIRTUAL_HEIGHT
        );

        background.setTouchable(Touchable.disabled);

        stageHolder.stage.addActor(background);
    }


    private void buildTopHud() {
        Table hud = new Table();

        hud.setFillParent(true);
        hud.top();
        hud.pad(14f, 18f, 0f, 18f);

        TextButton backButton =
            new TextButton("BACK", skin, "brown");

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(
                InputEvent event,
                float x,
                float y
            ) {
                goBack();
            }
        });

        Label adventureLabel =
            new Label(
                "ADVENTURE",
                skin,
                "big_outline"
            );

        User user = App.getCurrentuser();

        String coinsText =
            "COINS: " + (user == null ? 0 : user.getCoins());

        String gemsText =
            "GEMS: " + (user == null ? 0 : user.getDiamonds());

        Label coins =
            new Label(
                coinsText,
                skin,
                "medium_outline"
            );

        Label gems =
            new Label(
                gemsText,
                skin,
                "medium_outline"
            );

        hud.add(backButton)
            .width(125f)
            .height(55f)
            .left();

        hud.add().expandX();

        hud.add(adventureLabel)
            .center()
            .padRight(80f);

        hud.add().expandX();

        hud.add(coins)
            .padRight(25f);

        hud.add(gems);

        stageHolder.stage.addActor(hud);
    }


    private void buildWorldArea() {
        worldGroup = new Group();

        worldGroup.setBounds(
            0f,
            245f,
            VIRTUAL_WIDTH,
            380f
        );

        stageHolder.stage.addActor(worldGroup);
    }


    private void buildLevelPanel() {
        levelPanel = new Table();

        levelPanel.setBounds(
            145f,
            30f,
            990f,
            205f
        );

        Drawable panelBackground =
            safeDrawable(
                "image_ui_quests_panel_edge_to_edge_ten"
            );

        if (panelBackground != null) {
            levelPanel.setBackground(panelBackground);
        }

        levelPanel.pad(
            12f,
            24f,
            18f,
            24f
        );

        stageHolder.stage.addActor(levelPanel);
    }


    // -------------------------------------------------------------------------
    // Chapter selection
    // -------------------------------------------------------------------------

    private void refreshWorlds(boolean animate) {
        worldGroup.clearChildren();

        for (int i = 0; i < CHAPTER_ORDER.length; i++) {

            Chapters chapter = CHAPTER_ORDER[i];

            boolean selected =
                chapter == selectedChapter;

            Table worldCard =
                createWorldCard(
                    chapter,
                    selected
                );

            float width =
                selected ? 280f : 220f;

            float height =
                selected ? 270f : 220f;

            float centerX =
                WORLD_SLOT_X[i];

            float y =
                selected ? 40f : 64f;

            worldCard.setBounds(
                centerX - width / 2f,
                y,
                width,
                height
            );

            worldCard.setTransform(true);
            worldCard.setOrigin(Align.center);

            if (animate) {
                worldCard.getColor().a = 0f;
                worldCard.setScale(0.85f);

                worldCard.addAction(
                    Actions.parallel(
                        Actions.fadeIn(0.22f),
                        Actions.scaleTo(
                            1f,
                            1f,
                            0.25f,
                            Interpolation.swingOut
                        )
                    )
                );
            }

            worldGroup.addActor(worldCard);
        }
    }


    private Table createWorldCard(
        Chapters chapter,
        boolean selected
    ) {
        Table card = new Table();

        card.setTouchable(Touchable.enabled);

        TextureRegion region =
            chapterRegions.get(chapter);

        if (region != null) {

            Image worldImage =
                new Image(region);

            worldImage.setScaling(Scaling.fit);
            worldImage.setTouchable(Touchable.disabled);

            card.add(worldImage)
                .size(
                    selected ? 225f : 175f,
                    selected ? 185f : 145f
                )
                .padTop(4f)
                .row();

        } else {

            /*
             * Asset-safe fallback.
             *
             * This keeps the menu usable even when pvz.assets is not configured
             * or no suitable world texture is found.
             */
            TextButton fallback =
                new TextButton(
                    getChapterShortName(chapter),
                    skin,
                    selected ? "green" : "brown"
                );

            fallback.setTouchable(Touchable.disabled);

            card.add(fallback)
                .size(
                    selected ? 220f : 175f,
                    selected ? 150f : 125f
                )
                .padTop(20f)
                .row();
        }

        Label title =
            new Label(
                getChapterDisplayName(chapter),
                skin,
                selected
                    ? "big_outline"
                    : "medium_outline"
            );

        title.setAlignment(Align.center);
        title.setTouchable(Touchable.disabled);

        card.add(title)
            .center()
            .padTop(5f);

        if (selected) {
            card.row();

            Label selectedText =
                new Label(
                    "SELECTED",
                    skin,
                    "secondary"
                );

            selectedText.setTouchable(
                Touchable.disabled
            );

            card.add(selectedText)
                .padTop(2f);
        }

        card.addListener(
            new ClickListener() {
                @Override
                public void clicked(
                    InputEvent event,
                    float x,
                    float y
                ) {
                    selectChapter(chapter);
                }
            }
        );

        return card;
    }


    private void selectChapter(
        Chapters chapter
    ) {
        if (chapter == null) {
            return;
        }

        if (chapter == selectedChapter) {
            return;
        }

        selectedChapter = chapter;

        /*
         * Important:
         * PlayMenu owns the actual selected chapter for play().
         *
         * changeChapter() returns CLI text, which the graphical View simply
         * doesn't care about.
         */
        playMenu.changeChapter(chapter);

        refreshWorlds(true);
        refreshLevels(true);
    }


    // -------------------------------------------------------------------------
    // Levels
    // -------------------------------------------------------------------------

    private void refreshLevels(
        boolean animate
    ) {
        levelPanel.clearChildren();

        selectedChapterLabel =
            new Label(
                getChapterDisplayName(selectedChapter),
                skin,
                "big_outline"
            );

        selectedChapterLabel.setAlignment(
            Align.center
        );

        levelPanel.add(selectedChapterLabel)
            .colspan(4)
            .expandX()
            .center()
            .padBottom(12f)
            .row();

        ArrayList<Level> rawLevels =
            Data.getAllLevels().get(selectedChapter);

        if (rawLevels == null ||
            rawLevels.isEmpty()) {

            Label noLevels =
                new Label(
                    "NO LEVELS AVAILABLE",
                    skin,
                    "medium_outline"
                );

            levelPanel.add(noLevels)
                .colspan(4)
                .center()
                .padTop(20f);

            return;
        }

        /*
         * Make a copy because we don't want the View rearranging Data's own
         * ArrayList.
         */
        List<Level> levels =
            new ArrayList<>(rawLevels);

        levels.sort(
            Comparator.comparingInt(
                Level::getId
            )
        );

        /*
         * Project requirement: four visible stages per chapter.
         */
        int count =
            Math.min(4, levels.size());

        for (int i = 0; i < count; i++) {

            Level level =
                levels.get(i);

            Actor levelActor =
                createLevelActor(level);

            levelPanel.add(levelActor)
                .size(170f, 86f)
                .padLeft(9f)
                .padRight(9f);
        }

        /*
         * Defensive fallback if data is temporarily incomplete.
         * We still keep four visual slots.
         */
        for (int i = count; i < 4; i++) {

            TextButton unavailable =
                new TextButton(
                    "LOCK",
                    skin,
                    "brown"
                );

            unavailable.setDisabled(true);

            levelPanel.add(unavailable)
                .size(170f, 86f)
                .padLeft(9f)
                .padRight(9f);
        }

        if (animate) {

            levelPanel.getColor().a = 0f;

            levelPanel.addAction(
                Actions.fadeIn(0.22f)
            );
        }
    }


    private Actor createLevelActor(
        Level level
    ) {
        boolean unlocked =
            isLevelUnlocked(level);

        boolean current =
            isCurrentLevel(level);

        Stack stack = new Stack();

        stack.setTouchable(Touchable.enabled);
        stack.setTransform(true);
        stack.setOrigin(Align.center);

        TextButton button =
            new TextButton(
                unlocked
                    ? String.valueOf(level.getId())
                    : "",
                skin,
                unlocked
                    ? "green"
                    : "brown"
            );

        /*
         * The Stack handles clicks.
         * Button is visual only here.
         */
        button.setTouchable(
            Touchable.disabled
        );

        stack.add(button);

        if (!unlocked) {

            if (lockRegion != null) {

                Table lockHolder =
                    new Table();

                lockHolder.setTouchable(
                    Touchable.disabled
                );

                Image lock =
                    new Image(lockRegion);

                lock.setScaling(
                    Scaling.fit
                );

                lock.setTouchable(
                    Touchable.disabled
                );

                lockHolder.add(lock)
                    .size(45f, 45f);

                stack.add(lockHolder);

            } else {

                Table lockHolder =
                    new Table();

                Label lockText =
                    new Label(
                        "LOCK",
                        skin,
                        "medium_outline"
                    );

                lockText.setTouchable(
                    Touchable.disabled
                );

                lockHolder.add(lockText);

                stack.add(lockHolder);
            }

        } else if (current) {

            Table currentHolder =
                new Table();

            currentHolder.bottom();

            Label currentLabel =
                new Label(
                    "CURRENT",
                    skin,
                    "secondary"
                );

            currentLabel.setTouchable(
                Touchable.disabled
            );

            currentHolder.add(currentLabel)
                .padBottom(3f);

            currentHolder.setTouchable(
                Touchable.disabled
            );

            stack.add(currentHolder);
        }

        stack.addListener(
            new ClickListener() {
                @Override
                public void clicked(
                    InputEvent event,
                    float x,
                    float y
                ) {
                    if (!unlocked) {
                        playLockedAnimation(stack);
                        return;
                    }

                    /*
                     * PlayMenu performs:
                     *
                     * - finding the Level
                     * - unlock validation
                     * - changing the Screen to GameView
                     *
                     * String return value is intentionally ignored.
                     */
                    playMenu.play(
                        level.getId()
                    );
                }
            }
        );

        return stack;
    }


    private boolean isLevelUnlocked(
        Level level
    ) {
        User user =
            App.getCurrentuser();

        if (user == null ||
            level == null) {

            return false;
        }

        /*
         * Exactly the same progression rule currently used by PlayMenu.
         */
        return user.getLevelsPassed()
            >= level.getId() - 1;
    }


    private boolean isCurrentLevel(
        Level level
    ) {
        User user =
            App.getCurrentuser();

        if (user == null ||
            level == null) {

            return false;
        }

        return user.getChapter()
            == selectedChapter

            && user.getLevelId()
            == level.getId();
    }


    private void playLockedAnimation(
        Actor actor
    ) {
        actor.clearActions();

        actor.addAction(
            Actions.sequence(
                Actions.scaleTo(
                    0.92f,
                    0.92f,
                    0.07f
                ),

                Actions.scaleTo(
                    1.05f,
                    1.05f,
                    0.07f
                ),

                Actions.scaleTo(
                    1f,
                    1f,
                    0.08f
                )
            )
        );
    }


    // -------------------------------------------------------------------------
    // Navigation
    // -------------------------------------------------------------------------

    private void goBack() {
        /*
         * PlayMenu.exitMenu() already changes screen to HomeView.
         * Its String output belonged to the CLI version.
         */
        playMenu.exitMenu();
    }


    // -------------------------------------------------------------------------
    // PvZ assets
    // -------------------------------------------------------------------------

    private void initialisePvzAssets() {
        pvzAssetsRoot =
            findPvzAssetsRoot();

        if (pvzAssetsRoot == null) {

            Gdx.app.error(
                "PlayView",
                "PVZ asset directory was not found. " +
                    "Use -Dpvz.assets=<path-to-extracted-pvz-assets>"
            );

            return;
        }

        try {

            textureBank =
                new TextureBank(
                    ASSET_RESOLUTION,
                    pvzAssetsRoot
                );

            Gdx.app.log(
                "PlayView",
                "PVZ assets loaded from: " +
                    pvzAssetsRoot.path()
            );

        } catch (Exception e) {

            textureBank = null;

            Gdx.app.error(
                "PlayView",
                "Failed to initialise TextureBank",
                e
            );
        }
    }


    private FileHandle findPvzAssetsRoot() {

        List<FileHandle> roots =
            new ArrayList<>();

        String configured =
            System.getProperty(
                "pvz.assets"
            );

        if (configured != null &&
            !configured.isBlank()) {

            roots.add(
                new FileHandle(
                    new File(configured)
                )
            );
        }

        /*
         * Useful development fallbacks.
         */
        roots.add(
            new FileHandle(
                new File("Assets")
            )
        );

        roots.add(
            new FileHandle(
                new File("pvz-assets")
            )
        );

        roots.add(
            Gdx.files.internal(
                "pvz-assets"
            )
        );

        for (FileHandle candidate : roots) {

            FileHandle resolved =
                resolveAssetRoot(candidate);

            if (resolved != null) {
                return resolved;
            }
        }

        return null;
    }


    private FileHandle resolveAssetRoot(
        FileHandle root
    ) {
        if (root == null ||
            !root.exists()) {

            return null;
        }

        if (isPvzAssetRoot(root)) {
            return root;
        }

        /*
         * Common archive layouts.
         */
        String[] possibleChildren = {
            "Base Assets",
            "base assets",
            "BaseAssets",
            "pvz-assets",
            "assets"
        };

        for (String childName
            : possibleChildren) {

            FileHandle child =
                root.child(childName);

            if (isPvzAssetRoot(child)) {
                return child;
            }
        }

        /*
         * One-level automatic search, so the downloaded archive does not have
         * to match an exact folder name.
         */
        try {

            for (FileHandle child
                : root.list()) {

                if (child.isDirectory() &&
                    isPvzAssetRoot(child)) {

                    return child;
                }
            }

        } catch (Exception ignored) {
        }

        return null;
    }


    private boolean isPvzAssetRoot(
        FileHandle root
    ) {
        if (root == null ||
            !root.exists()) {

            return false;
        }

        boolean hasResources =
            root.child(
                "resources.json"
            ).exists()

                || root.child(
                "RESOURCES.json"
            ).exists();

        boolean hasAtlases =
            root.child(
                "atlases"
            ).exists()

                || root.child(
                "ATLASES"
            ).exists();

        return hasResources &&
            hasAtlases;
    }


    private void loadMenuAssets() {
        if (textureBank == null) {
            return;
        }

        backgroundRegion =
            findWorldMapBackground();

        lockRegion =
            findLockRegion();

        for (Chapters chapter
            : CHAPTER_ORDER) {

            TextureRegion region =
                findChapterRegion(chapter);

            if (region != null) {
                chapterRegions.put(
                    chapter,
                    region
                );
            }
        }
    }


    // -------------------------------------------------------------------------
    // Asset discovery
    // -------------------------------------------------------------------------

    private TextureRegion findChapterRegion(
        Chapters chapter
    ) {
        if (textureBank == null) {
            return null;
        }

        String[] chapterTokens =
            getChapterAssetTokens(chapter);

        ResourceIndex index =
            textureBank.resourceIndex();

        String bestId = null;
        int bestScore =
            Integer.MIN_VALUE;

        /*
         * Pass 1:
         * Require the candidate to look like a world-map asset.
         *
         * This prevents something delightful like using a frozen zombie's
         * detached head as the Frozen Caves chapter icon.
         */
        for (boolean requireWorldHint
            : new boolean[]{true, false}) {

            bestId = null;
            bestScore =
                Integer.MIN_VALUE;

            for (String id
                : index.imageIds()) {

                ResourceIndex.ImageEntry entry =
                    index.image(id);

                if (entry == null) {
                    continue;
                }

                String haystack =
                    (
                        id + " " +
                            entry.path
                    ).toUpperCase(
                        Locale.ROOT
                    );

                boolean chapterMatch =
                    false;

                int score = 0;

                for (String token
                    : chapterTokens) {

                    if (haystack.contains(token)) {

                        chapterMatch = true;

                        score +=
                            token.length() >= 8
                                ? 80
                                : 45;
                    }
                }

                if (!chapterMatch) {
                    continue;
                }

                boolean worldHint =
                    containsAny(
                        haystack,
                        "WORLD",
                        "MAP",
                        "ISLAND",
                        "CHAPTER",
                        "LEVELSELECT",
                        "LEVEL_SELECT",
                        "TIMELINE"
                    );

                if (requireWorldHint &&
                    !worldHint) {

                    continue;
                }

                if (worldHint) {
                    score += 90;
                }

                if (containsAny(
                    haystack,
                    "ICON",
                    "SELECT",
                    "WORLDKEY",
                    "WORLDMAP"
                )) {
                    score += 25;
                }

                if (containsAny(
                    haystack,
                    "ZOMBIE",
                    "PLANT",
                    "SEED",
                    "PACKET",
                    "CARD",
                    "PROJECTILE",
                    "PARTICLE"
                )) {
                    score -= 180;
                }

                if (entry.aw >= 100 &&
                    entry.ah >= 100) {

                    score += 25;
                }

                if (entry.aw >= 150 &&
                    entry.aw <= 900 &&
                    entry.ah >= 120 &&
                    entry.ah <= 900) {

                    score += 15;
                }

                float ratio =
                    entry.ah == 0
                        ? 0f
                        : (float) entry.aw /
                        entry.ah;

                if (ratio >= 0.55f &&
                    ratio <= 2.0f) {

                    score += 10;
                }

                if (score > bestScore) {
                    bestScore = score;
                    bestId = id;
                }
            }

            if (bestId != null) {
                break;
            }
        }

        if (bestId == null) {

            Gdx.app.log(
                "PlayView",
                "No world asset found for " +
                    chapter
            );

            return null;
        }

        Gdx.app.log(
            "PlayView",
            chapter +
                " world resource: " +
                bestId
        );

        return textureBank.region(
            bestId
        );
    }


    private TextureRegion findWorldMapBackground() {
        if (textureBank == null) {
            return null;
        }

        ResourceIndex index =
            textureBank.resourceIndex();

        String bestId = null;
        int bestScore =
            Integer.MIN_VALUE;

        for (String id
            : index.imageIds()) {

            ResourceIndex.ImageEntry entry =
                index.image(id);

            if (entry == null) {
                continue;
            }

            String haystack =
                (
                    id + " " +
                        entry.path
                ).toUpperCase(
                    Locale.ROOT
                );

            int score = 0;

            if (containsAny(
                haystack,
                "WORLDMAP",
                "WORLD_MAP",
                "LEVELSELECT",
                "LEVEL_SELECT"
            )) {
                score += 100;
            }

            if (containsAny(
                haystack,
                "BACKGROUND",
                "_BG",
                "BACKDROP"
            )) {
                score += 70;
            }

            if (containsAny(
                haystack,
                "MAP",
                "WORLD"
            )) {
                score += 25;
            }

            if (containsAny(
                haystack,
                "ZOMBIE",
                "PLANT",
                "CARD",
                "PACKET",
                "ICON",
                "BUTTON"
            )) {
                score -= 130;
            }

            if (entry.aw >= 600) {
                score += 35;
            }

            if (entry.ah >= 300) {
                score += 25;
            }

            float ratio =
                entry.ah == 0
                    ? 0f
                    : (float) entry.aw /
                    entry.ah;

            if (ratio >= 1.4f) {
                score += 25;
            }

            if (score > bestScore) {
                bestScore = score;
                bestId = id;
            }
        }

        /*
         * Avoid loading some random asset when no candidate looked remotely
         * like a background.
         */
        if (bestId == null ||
            bestScore < 80) {

            return null;
        }

        Gdx.app.log(
            "PlayView",
            "World map background: " +
                bestId
        );

        return textureBank.region(
            bestId
        );
    }


    private TextureRegion findLockRegion() {
        if (textureBank == null) {
            return null;
        }

        ResourceIndex index =
            textureBank.resourceIndex();

        String bestId = null;
        int bestScore =
            Integer.MIN_VALUE;

        for (String id
            : index.imageIds()) {

            ResourceIndex.ImageEntry entry =
                index.image(id);

            if (entry == null) {
                continue;
            }

            String haystack =
                (
                    id + " " +
                        entry.path
                ).toUpperCase(
                    Locale.ROOT
                );

            if (!haystack.contains("LOCK")) {
                continue;
            }

            int score = 60;

            if (haystack.contains("UNLOCK")) {
                score -= 150;
            }

            if (containsAny(
                haystack,
                "UI",
                "LEVEL",
                "WORLD",
                "MAP"
            )) {
                score += 45;
            }

            if (containsAny(
                haystack,
                "ICON",
                "PADLOCK"
            )) {
                score += 30;
            }

            if (entry.aw >= 20 &&
                entry.aw <= 250 &&
                entry.ah >= 20 &&
                entry.ah <= 250) {

                score += 25;
            }

            if (score > bestScore) {
                bestScore = score;
                bestId = id;
            }
        }

        if (bestId == null) {
            return null;
        }

        Gdx.app.log(
            "PlayView",
            "Lock resource: " +
                bestId
        );

        return textureBank.region(
            bestId
        );
    }


    private String[] getChapterAssetTokens(
        Chapters chapter
    ) {
        return switch (chapter) {

            case AncientEgypt ->
                new String[]{
                    "ANCIENTEGYPT",
                    "ANCIENT_EGYPT",
                    "ANCIENT EGYPT",
                    "EGYPT"
                };

            case FrozenCaves ->
                new String[]{
                    "FROZENCAVES",
                    "FROZEN_CAVES",
                    "FROZEN CAVES",
                    "FROZEN",
                    "ICEAGE",
                    "ICE_AGE",
                    "FROSTBITE"
                };

            case BigWaveBeach ->
                new String[]{
                    "BIGWAVEBEACH",
                    "BIG_WAVE_BEACH",
                    "BIG WAVE BEACH",
                    "BEACH",
                    "BIGWAVE"
                };

            case DarkAge ->
                new String[]{
                    "DARKAGE",
                    "DARK_AGE",
                    "DARK AGE",
                    "DARKAGES",
                    "DARK_AGES"
                };
        };
    }


    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private boolean containsAny(
        String text,
        String... tokens
    ) {
        if (text == null) {
            return false;
        }

        for (String token : tokens) {
            if (text.contains(token)) {
                return true;
            }
        }

        return false;
    }


    private Drawable safeDrawable(
        String name
    ) {
        try {
            return skin.getDrawable(name);
        } catch (Exception ignored) {
            return null;
        }
    }


    private String getChapterDisplayName(
        Chapters chapter
    ) {
        return switch (chapter) {

            case AncientEgypt ->
                "ANCIENT EGYPT";

            case FrozenCaves ->
                "FROZEN CAVES";

            case BigWaveBeach ->
                "BIG WAVE BEACH";

            case DarkAge ->
                "DARK AGES";
        };
    }


    private String getChapterShortName(
        Chapters chapter
    ) {
        return switch (chapter) {

            case AncientEgypt ->
                "EGYPT";

            case FrozenCaves ->
                "FROZEN";

            case BigWaveBeach ->
                "BEACH";

            case DarkAge ->
                "DARK";
        };
    }


    // -------------------------------------------------------------------------
    // Stage + Viewport holder
    // -------------------------------------------------------------------------

    private static final class StageHolder {

        private final Viewport viewport;
        private final com.badlogic.gdx.scenes.scene2d.Stage stage;

        private StageHolder() {

            viewport =
                new FitViewport(
                    VIRTUAL_WIDTH,
                    VIRTUAL_HEIGHT
                );

            stage =
                new com.badlogic.gdx.scenes.scene2d.Stage(
                    viewport
                );
        }
    }
}
