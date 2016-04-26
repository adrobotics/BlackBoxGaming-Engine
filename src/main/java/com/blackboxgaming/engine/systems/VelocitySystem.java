package com.blackboxgaming.engine.systems;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.blackboxgaming.engine.components.Transform;
import com.blackboxgaming.engine.components.Velocity;
import com.blackboxgaming.engine.Entity;

/**
 * This system translates an entities position and rotation by {@link Velocity}.
 *
 * @author Adrian
 */
public class VelocitySystem extends AbstractSystem {

    private Matrix4 transform;
    private Vector3 linear;
    private Vector3 angular;

    public VelocitySystem() {
        requiredComponents.add(Velocity.class);
        requiredComponents.add(Transform.class);
    }

    @Override
    public void update(float delta) {
        for (Entity entity : entities) {
            transform = entity.get(Transform.class).transform;
            linear = entity.get(Velocity.class).linear;
            angular = entity.get(Velocity.class).angular;

            // linear motion
            if (!linear.isZero()) {
                transform.translate(linear.cpy().scl(delta));
            }

            // angular motion
            if (!angular.isZero()) {
                transform.rotate(Vector3.X, angular.x * delta);
                transform.rotate(Vector3.Y, angular.y * delta);
                transform.rotate(Vector3.Z, angular.z * delta);
            }
        }
    }
}
