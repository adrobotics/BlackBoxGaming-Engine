package com.blackboxgaming.engine.systems;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Physics;
import com.blackboxgaming.engine.components.Puppet;
import com.blackboxgaming.engine.components.Transform;
import com.blackboxgaming.engine.components.Velocity;
import com.blackboxgaming.engine.input.PlayerKeyListener;
import com.blackboxgaming.engine.util.Global;

/**
 *
 * @author Adrian
 */
public class PhysicsSystem extends AbstractSystem {

    private Matrix4 transform;
    private Vector3 linear;
    private Vector3 angular;
    public static ContactListener contactListener;

    public PhysicsSystem() {
        Bullet.init();
        Global.getDynamicsWorld();
    }
    
    @Override
    public void markRequiredComponents() {
        requiredComponents.add(Physics.class);
        requiredComponents.add(Transform.class);
    }

    public int getCount() {
        return entities.size();
    }

    @Override
    public void add(Entity entity) {
        if (accept(entity) && !entities.contains(entity)) {
            Physics physics = entity.get(Physics.class);
            Matrix4 originalTransform = entity.get(Transform.class).transform;
            physics.setMotionState(originalTransform);
            physics.body.proceedToTransform(originalTransform);
            physics.body.setUserValue(entity.id);
            Global.getDynamicsWorld().addRigidBody(physics.body);
            entities.add(entity);
        }
    }

    @Override
    public void remove(Entity entity) {
        if (entities.contains(entity)) {
            btRigidBody body = entity.get(Physics.class).body;
            Global.getDynamicsWorld().removeRigidBody(body);
        }
        entities.remove(entity);
    }

    @Override
    public void update(float delta) {
        for (Entity entity : entities) {
            if (entity.has(Velocity.class)) {
                transform = entity.get(Transform.class).transform;
                linear = entity.get(Velocity.class).linear;
                angular = entity.get(Velocity.class).angular;
                btRigidBody body = entity.get(Physics.class).body;
                if (!linear.isZero() || !angular.isZero() || !PlayerKeyListener.joystickRight.joystick.isZero() || ((PlayerKeyListener.clickRight || PlayerKeyListener.clickMiddle) && entity.has(Puppet.class))) {
                    // set physics body to current object position.
                    body.proceedToTransform(transform);
                }
            }
        }

        Global.getDynamicsWorld().stepSimulation(Global.getDeltaInSeconds(), 5, 1f / 60f);
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        for (Entity entity : entities) {
            btRigidBody body = entity.get(Physics.class).body;
            Global.getDynamicsWorld().removeRigidBody(body);
        }
        entities.clear();
        if (contactListener != null) {
            contactListener.dispose();
        }
    }

}
