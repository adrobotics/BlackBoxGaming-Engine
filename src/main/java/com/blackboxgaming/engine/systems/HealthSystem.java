package com.blackboxgaming.engine.systems;

import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Health;

/**
 * Marks dead entities for deletion.
 *
 * @author Adrian
 * @see Health
 * @see GarbageManager
 */
public class HealthSystem extends AbstractSystem {

    public HealthSystem() {
        requiredComponents.add(Health.class);
    }

    @Override
    public void update(float delta) {
        for (Entity entity : entities) {
            if (entity.get(Health.class).isDead()) {
                System.out.println(entity.toShortString() + " has died.");
                Engine.garbageManager.markForDeletion(entity);
            }
        }
    }
}
