package com.blackboxgaming.engine.systems;

import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Health;
import com.blackboxgaming.engine.components.Torso;
import com.blackboxgaming.engine.util.Global;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adrian
 */
public class HealthSystem implements ISystem, Disposable {

    private final List<Entity> entities = new ArrayList();

    @Override
    public void add(Entity entity) {
        if (!entities.contains(entity)) {
            entities.add(entity);
        }
    }

    @Override
    public void remove(Entity entity) {
        entities.remove(entity);
    }

    @Override
    public void update(float delta) {
        for (Entity entity : entities) {
            if (entity.get(Health.class).isDead()) {
                System.out.println(entity + " has died.");
                if (Global.scoreButton != null) {
                    int score = Integer.parseInt(((ImageTextButton) Global.scoreButton).getText().toString());
                    ((ImageTextButton) Global.scoreButton).setText("" + (++score));
                    Global.touchLeft = false;
                    Global.touchRight = false;
                }
                if (entity.has(Torso.class)) {
                    Engine.garbageManager.markForDeletion(entity.get(Torso.class).torso);
                }
                if(entity == Global.mainCharacter){
                    Global.gameOver = true;
                }
                Engine.garbageManager.markForDeletion(entity);
            }
        }
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        entities.clear();
    }

}
