package com.blackboxgaming.engine.systems;

import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Death;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adrian
 */
public class TimedDeathSystem implements ISystem, Disposable {

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
            Death death = entity.get(Death.class);
            if(death.startTime + (long)death.timeToLive <= System.currentTimeMillis()){
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
