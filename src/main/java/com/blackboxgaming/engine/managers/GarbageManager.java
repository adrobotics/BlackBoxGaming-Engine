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
                for (Entity child : entity.get(Parent.class).children) {
                    Engine.systemManager.remove(child);
                }
            }
            Engine.systemManager.remove(entity);
            Engine.entityManager.remove(entity);
        }
        entities.clear();
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        entities.clear();
    }

}
