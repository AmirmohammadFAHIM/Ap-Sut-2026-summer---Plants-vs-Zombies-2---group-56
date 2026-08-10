package models.entity;

import models.factory.plantSkills.Skill;
import models.games.BaseGame;

public class WrampUpPlant extends Plant{
    private int level = 1;
    public WrampUpPlant(Plant plant){
        this.x = plant.getX();
        this.y = plant.getY();
        this.width = plant.getWidth();
        this.height = plant.getHeight();
        this.tags = plant.getTags();
        this.type =  plant.getType();




    }
    private float updateStageTimer = 24f;
    private void grow(float delta){
        if(level >= 3) return;
        if(updateStageTimer <= 0){
            level += 1;
            updateStageTimer = 24 * level;
            for (Skill skill : getBaseSkill()) skill.update();
        }
        else updateStageTimer -= delta;
    }

    @Override
    public void update(float delta, BaseGame game) {
        grow(delta);
        super.update(delta, game);
    }
}
