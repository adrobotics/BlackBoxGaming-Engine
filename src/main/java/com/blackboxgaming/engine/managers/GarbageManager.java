package com.blackboxgaming.engine.managers;

import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Parent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adrian
 */
public class GarbageManager implements Disposable {

    private final List<Entity> entities = new ArrayList();

    public void markForDeletion(Entity entity) {
        if (!entities.contains(entity)) {
            entities.add(entity);
        }
    }

    public void removeGarbage() {
        for (Entity entity : entities) {
            if (entity.has(Parent.class)) {
                // doesn't handle multi level children
                for (Entity child : entity.get(Parent.class).children) {
                    remove(child);
                }
            }
            remove(entity);
        }
        entities.clear();
    }

    private void remove(Entity entity) {
        Engine.systemManager.remove(entity);
        Engine.entityManager.remove(entity);
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        entities.clear();
    }

}
