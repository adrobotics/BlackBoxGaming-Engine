package com.blackboxgaming.engine.systems;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Physics;
import com.blackboxgaming.engine.components.Puppet;
import com.blackboxgaming.engine.components.Transform;
import com.blackboxgaming.engine.components.Velocity;
import com.blackboxgaming.engine.input.PlayerKeyListener;
import com.blackboxgaming.engine.util.Global;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adrian
 */
public class PhysicsSystem implements ISystem, Disposable {

    private final List<Entity> entities = new ArrayList();
    private Matrix4 transform;
    private Vector3 velocity;
    private Vector3 angularVelocity;
    public static ContactListener contactListener;

    public PhysicsSystem() {
        Bullet.init();
        Global.getDynamicsWorld();
    }

    @Override
    public void add(Entity entity) {
        if (!entities.contains(entity)) {
            Matrix4 originalTransform = entity.get(Transform.class).transform;
            Physics physics = entity.get(Physics.class);
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
//            if (entity.has(Velocity.class) && Engine.systemManager.has(VelocitySystem.class)) {
//                Engine.systemManager.get(VelocitySystem.class).add(entity);
//            }
        }
        entities.remove(entity);
    }

    @Override
    public void update(float delta) {
        for (Entity entity : entities) {
            if (entity.has(Velocity.class)) {
                transform = entity.get(Transform.class).transform;
                velocity = entity.get(Velocity.class).velocity;
                angularVelocity = entity.get(Velocity.class).angularVelocity;
                btRigidBody body = entity.get(Physics.class).body;

//                if (!velocity.isZero()) {
//                    transform.translate(velocity.cpy().scl(delta));
//                }
//                if (!angularVelocity.isZero()) {
//                    transform.rotate(Vector3.X, angularVelocity.x * delta);
//                    transform.rotate(Vector3.Y, angularVelocity.y * delta);
//                    transform.rotate(Vector3.Z, angularVelocity.z * delta);
//                }
                if (!velocity.isZero() || !angularVelocity.isZero() || !PlayerKeyListener.joystickRight.joystick.isZero() || ((PlayerKeyListener.clickRight || PlayerKeyListener.clickMiddle) && entity.has(Puppet.class))) {
                    body.proceedToTransform(transform);
                }
            }
        }

        if (Global.performanceCounter != null) {
            Global.performanceCounter.tick();
            Global.performanceCounter.start();
        }
        Global.getDynamicsWorld().stepSimulation(Global.getDeltaInSeconds(), 5, 1f / 60f);
//        Global.getDynamicsWorld().stepSimulation(Global.getDeltaInSeconds(), 2, 1f / 40f);
//        Global.getDynamicsWorld().stepSimulation(Global.getDeltaInSeconds(), 10, 1f / 90f);
//        Global.getDynamicsWorld().stepSimulation(Global.getDeltaInSeconds(), 25, 1f / 300f);
        if (Global.performanceCounter != null) {
            Global.performanceCounter.stop();
        }
    }

    public int getCount() {
        return entities.size();
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
