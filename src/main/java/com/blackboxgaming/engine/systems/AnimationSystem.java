package com.blackboxgaming.engine.systems;

import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Animation;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adrian
 */
public class AnimationSystem implements ISystem, Disposable {

    private final List<Entity> entities = new ArrayList();

    @Override
    public void add(Entity entity) {
        if (!entities.contains(entity) && entity.has(Animation.class)) {
            entities.add(entity);
            entity.get(Animation.class).controller.setAnimation("Idle");
        }
    }

    @Override
    public void remove(Entity entity) {
        entities.remove(entity);
    }

    @Override
    public void update(float delta) {
        for (Entity entity : entities) {
            entity.get(Animation.class).controller.update(delta);
        }
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        entities.clear();
    }
}
