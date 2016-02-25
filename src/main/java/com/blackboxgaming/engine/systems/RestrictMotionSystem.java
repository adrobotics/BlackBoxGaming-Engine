package com.blackboxgaming.engine.systems;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.components.Transform;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Physics;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adrian
 */
public class RestrictMotionSystem implements ISystem, Disposable {

    private final List<Entity> entities = new ArrayList();
    private Matrix4 transform;
    private btRigidBody body;

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
            body = entity.get(Physics.class).body;

//            Vector3 angV = body.getAngularVelocity();
//            angV.x = 0;
//            body.setAngularVelocity(angV);
//            if (!velocity.isZero()) {
//                if (!entity.get(Velocity.class).trnFlag) {
//                    transform.translate(velocity.cpy().scl(delta));
//                } else {
//                    transform.trn(velocity.cpy().scl(delta));
//                }
//            }
        }
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        entities.clear();
    }

}
