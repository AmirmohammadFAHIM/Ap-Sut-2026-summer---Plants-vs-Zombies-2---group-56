package view.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import models.factory.builder.PlantType;
import pvz.libpvz.textures.ResourceIndex;
import pvz.libpvz.textures.TextureBank;
import pvz.skin.PvzSkin;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Reusable plant-card grid.
 *
 * <p>The class deliberately knows nothing about GameView, GameController or CollectionView.
 * The caller supplies an {@link Adapter}; therefore the same widget can be used for the
 * pre-game selection screen and later for the Collection screen.</p>
 */
public final class PlantTable extends Table {

    public interface Adapter {
        default boolean isSelected(PlantType type) {
            return false;
        }

        default boolean isEnabled(PlantType type) {
            return true;
        }

        default String detail(PlantType type) {
            return "";
        }

        default void clicked(PlantType type) {
        }
    }

    public static Adapter readOnly() {
        return new Adapter() {
        };
    }

    private static final float CARD_WIDTH = 124f;
    private static final float CARD_HEIGHT = 142f;
    private static final float ICON_SIZE = 76f;

    private final List<PlantType> plants = new ArrayList<>();
    private final Map<PlantType, TextureRegion> iconCache = new EnumMap<>(PlantType.class);
    private final List<PlantCard> cards = new ArrayList<>();

    private final TextureBank textureBank;
    private final Skin skin;
    private final int columns;
    private final Adapter adapter;

    public PlantTable(
        Iterable<PlantType> plants,
        TextureBank textureBank,
        int columns,
        Adapter adapter
    ) {
        this(plants, textureBank, PvzSkin.get(), columns, adapter);
    }

    public PlantTable(
        Iterable<PlantType> plants,
        TextureBank textureBank,
        Skin skin,
        int columns,
        Adapter adapter
    ) {
        if (columns <= 0) {
            throw new IllegalArgumentException("columns must be positive");
        }

        this.textureBank = textureBank;
        this.skin = skin == null ? PvzSkin.get() : skin;
        this.columns = columns;
        this.adapter = adapter == null ? readOnly() : adapter;

        if (plants != null) {
            for (PlantType type : plants) {
                if (type != null) {
                    this.plants.add(type);
                }
            }
        }

        top().left();
        rebuild();
    }

    public void rebuild() {
        clearChildren();
        cards.clear();

        int column = 0;
        for (PlantType type : plants) {
            PlantCard card = new PlantCard(type);
            cards.add(card);

            add(card)
                .size(CARD_WIDTH, CARD_HEIGHT)
                .pad(4f);

            column++;
            if (column >= columns) {
                row();
                column = 0;
            }
        }
    }

    public void refresh() {
        for (PlantCard card : cards) {
            card.refresh();
        }
    }

    private final class PlantCard extends Stack {
        private final PlantType type;
        private final TextButton background;
        private final Label detailLabel;

        private PlantCard(PlantType type) {
            this.type = type;
            setTouchable(Touchable.enabled);

            background = new TextButton("", skin, "brown");
            background.setTouchable(Touchable.disabled);
            add(background);

            Table content = new Table();
            content.setTouchable(Touchable.disabled);
            content.pad(5f);

            TextureRegion iconRegion = findPlantIconRegion(type);
            if (iconRegion != null) {
                Image icon = new Image(iconRegion);
                icon.setScaling(Scaling.fit);
                icon.setTouchable(Touchable.disabled);
                content.add(icon).size(ICON_SIZE).padTop(2f).row();
            } else {
                Label fallback = new Label(shortPlantName(type), skin, "medium_outline");
                fallback.setAlignment(Align.center);
                fallback.setTouchable(Touchable.disabled);
                content.add(fallback)
                    .width(CARD_WIDTH - 12f)
                    .height(ICON_SIZE)
                    .center()
                    .row();
            }

            Label name = new Label(shortPlantName(type), skin);
            name.setAlignment(Align.center);
            name.setTouchable(Touchable.disabled);
            content.add(name)
                .width(CARD_WIDTH - 12f)
                .center()
                .row();

            detailLabel = new Label("", skin);
            detailLabel.setAlignment(Align.center);
            detailLabel.setTouchable(Touchable.disabled);
            content.add(detailLabel)
                .width(CARD_WIDTH - 12f)
                .center();

            add(content);

            addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!adapter.isEnabled(type)) {
                        return;
                    }

                    adapter.clicked(type);
                    PlantTable.this.refresh();
                }
            });

            refresh();
        }

        private void refresh() {
            boolean selected = adapter.isSelected(type);
            boolean enabled = adapter.isEnabled(type);

            background.setColor(selected ? new Color(0.72f, 1f, 0.72f, 1f) : Color.WHITE);
            setColor(enabled || selected ? Color.WHITE : Color.GRAY);
            setTouchable(enabled ? Touchable.enabled : Touchable.disabled);

            String detail = adapter.detail(type);
            detailLabel.setText(detail == null ? "" : detail);
        }
    }

    /**
     * Uses libPVZ's ResourceIndex/TextureBank only when an extracted PvZ asset root is
     * available. If no matching seed-packet image is found, the card stays fully usable
     * with the text fallback above.
     */
    private TextureRegion findPlantIconRegion(PlantType type) {
        if (textureBank == null || type == null) {
            return null;
        }

        if (iconCache.containsKey(type)) {
            return iconCache.get(type);
        }

        ResourceIndex index = textureBank.resourceIndex();
        String plantToken = normalize(type.name());

        String bestId = null;
        int bestScore = Integer.MIN_VALUE;

        for (String id : index.imageIds()) {
            ResourceIndex.ImageEntry entry = index.image(id);
            if (entry == null) {
                continue;
            }

            String haystack = (id + " " + entry.path).toUpperCase(Locale.ROOT);
            String normalized = normalize(haystack);

            if (!normalized.contains(plantToken)) {
                continue;
            }

            int score = 100;

            if (containsAny(haystack,
                "SEEDPACKET", "SEED_PACKET", "SEEDPKT", "SEED_PKT", "PACKET")) {
                score += 140;
            }

            if (containsAny(haystack, "ICON", "PORTRAIT", "CARD")) {
                score += 45;
            }

            if (containsAny(haystack, "ZOMBIE", "PROJECTILE", "PARTICLE")) {
                score -= 250;
            }

            if (entry.aw >= 40 && entry.aw <= 400 && entry.ah >= 40 && entry.ah <= 400) {
                score += 25;
            }

            if (score > bestScore) {
                bestScore = score;
                bestId = id;
            }
        }

        TextureRegion region = bestId == null ? null : textureBank.region(bestId);
        iconCache.put(type, region);
        return region;
    }

    private static String shortPlantName(PlantType type) {
        if (type == null) {
            return "";
        }

        String[] words = type.name().toLowerCase(Locale.ROOT).split("_");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (word.isEmpty()) {
                continue;
            }

            if (!result.isEmpty()) {
                result.append(' ');
            }

            result.append(Character.toUpperCase(word.charAt(0)))
                .append(word.substring(1));
        }

        return result.toString();
    }

    private static String normalize(String value) {
        return value == null
            ? ""
            : value.toUpperCase(Locale.ROOT).replaceAll("[^A-Z0-9]", "");
    }

    private static boolean containsAny(String value, String... tokens) {
        if (value == null) {
            return false;
        }

        for (String token : tokens) {
            if (value.contains(token)) {
                return true;
            }
        }

        return false;
    }
}
