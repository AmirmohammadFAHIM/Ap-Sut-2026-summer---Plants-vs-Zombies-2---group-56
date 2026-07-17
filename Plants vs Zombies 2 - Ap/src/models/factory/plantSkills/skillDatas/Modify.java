package models.factory.plantSkills.skillDatas;

import models.entity.Bullet;
import models.entity.Plant;
import models.factory.plantSkills.Skill;
import models.gamePanes.Tile;
import models.gamePanes.TileType;
import models.games.BaseGame;

public class Modify implements Skill {
    public enum Type {GRAVE_EATER}
    Type type;
    public Modify() {
    }
    public Modify(Type type) {
        this.type = type;
    }
    @Override
    public void do_skill(Plant plant, BaseGame game) {

    }

    @Override
    public void all(Plant plant, BaseGame game) {

    }

    @Override
    public void setRandom(boolean random) {

    }

    @Override
    public void setAll(boolean all) {

    }

    private void graveEater(Plant eater , BaseGame game) {
        Tile tile = game.getField().getTiles().get(eater.getLine()).get(eater.getTileIndex());
        TileType newType = switch (tile.getTileType()){
            case EGYPTIAN_GRAVE -> TileType.EGYPTIAN_TILE;
            case DARK_AGE_GRAVE -> TileType.DARK_AGE_TILE;
            case NECROMANCY -> TileType.DARK_AGE_TILE;
            default -> null;
        };
        tile.setTileType(newType);
        tile.setEmpty(true);
    }

    public boolean pf;
    private void fire(Plant plant, BaseGame game) {
        for (Bullet x : game.getBullets()) {
            if(x.getX() >= plant.getX() && x.getY() - plant.getY() <= 20){
                x.setDamage(x.getDamage() * (pf ? 3 : 2));
                x.getTags().add(Bullet.Tag.FIRE);
            }
        }
    }

    private void lilyPad(Plant plant, BaseGame game) {
        if(plant.getArmor() == null){
            for (Plant x : game.getPlants_inField()){
                if(x.getLine() == plant.getLine() && x.getTileIndex() == plant.getTileIndex()){
                    plant.getArmor().add(new PlantArmor(x.getHp()));
                    break;
                }
            }
        }
    }
}
