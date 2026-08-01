package models.entity;

import models.factory.plantSkills.Skill;
import models.games.BaseGame;

public class WrampUpPlant extends Plant{
    private int level = 1;
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
