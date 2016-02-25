package com.blackboxgaming.engine.systems;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Transform;
import com.blackboxgaming.engine.util.Global;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adrian
 */
public class AbyssSystem implements ISystem, Disposable {

    private final List<Entity> entities = new ArrayList();
    private final float abyss = -1f;
    private final Vector3 v = new Vector3();
    private final Vector3 position = new Vector3();

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
            position.set(entity.get(Transform.class).transform.getTranslation(v));
            if (position.y < abyss || Math.abs(position.x) > Global.boxLength / 2f || Math.abs(position.z) > Global.boxWidth / 2f) {
                System.out.println(entity + " destroyed by abyss");
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
