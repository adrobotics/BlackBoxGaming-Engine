package com.blackboxgaming.engine.systems;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Transform;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adrian
 */
public class AbyssSystem implements ISystem, Disposable {

    private final float abyss;
    private final Vector3 position = new Vector3();

    public AbyssSystem() {
        this(-5);
    }

    public AbyssSystem(float abyss) {
        this.abyss = abyss;
    }

    @Override
    public void add(Entity entity) {
    }

    @Override
    public void remove(Entity entity) {
    }

    @Override
    public void update(float delta) {
        for (Entity entity : Engine.entityManager.getEntities()) {
            if (entity.has(Transform.class)) {
                entity.get(Transform.class).transform.getTranslation(position);
                if (position.y < abyss || Math.abs(position.x) > 50 || Math.abs(position.z) > 50) {
                    Engine.garbageManager.markForDeletion(entity);
                }
            }
        }
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
    }

}
