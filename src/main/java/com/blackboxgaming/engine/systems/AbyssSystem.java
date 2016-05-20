package com.blackboxgaming.engine.systems;

import com.badlogic.gdx.math.Vector3;
import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Transform;

/**
 * Marks entities for deletion that exceed the world bounds.
 *
 * @author Adrian
 */
public class AbyssSystem extends AbstractSystem {

    private final float depth;
    private final float distance;
    private final Vector3 position = new Vector3();

    public AbyssSystem() {
        this(10, 50);
    }

    /**
     * Marks entities for deletion that exceed the world bounds.
     *
     * @param depth distance beneath X0Z plane
     * @param distance from center of the world
     */
    public AbyssSystem(float depth, float distance) {
        this.depth = depth;
        this.distance = distance;
        requiredComponents.add(Transform.class);
    }

    @Override
    public void update(float delta) {
        for (Entity entity : entities) {
            entity.get(Transform.class).transform.getTranslation(position);
            if (position.y < -depth || Math.abs(position.x) > distance || Math.abs(position.z) > distance) {
                Engine.garbageManager.markForDeletion(entity);
            }
        }
    }

}
