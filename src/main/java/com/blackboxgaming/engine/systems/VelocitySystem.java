package com.blackboxgaming.engine.systems;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.components.Transform;
import com.blackboxgaming.engine.components.Velocity;
import com.blackboxgaming.engine.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adrian
 */
public class VelocitySystem implements ISystem, Disposable {

    private final List<Entity> entities = new ArrayList();
    private Matrix4 transform;
    private Vector3 velocity;
    private Vector3 angularVelocity;

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
            transform = entity.get(Transform.class).transform;
            velocity = entity.get(Velocity.class).velocity;
            angularVelocity = entity.get(Velocity.class).angularVelocity;
            if (!velocity.isZero()) {
                if (!entity.get(Velocity.class).trnFlag) {
                    transform.translate(velocity.cpy().scl(delta));
                } else {
                    transform.trn(velocity.cpy().scl(delta));
                }
            }
            if (!angularVelocity.isZero()) {
                transform.rotate(Vector3.X, angularVelocity.x * delta);
                transform.rotate(Vector3.Y, angularVelocity.y * delta);
                transform.rotate(Vector3.Z, angularVelocity.z * delta);
            }
        }
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        entities.clear();
    }

}
