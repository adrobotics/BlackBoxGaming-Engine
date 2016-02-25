package com.blackboxgaming.engine.systems;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.collision.contactlistener.ContactListener2D;
import com.blackboxgaming.engine.components.Enemy;
import com.blackboxgaming.engine.components.Physics2D;
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
public class PhysicsSystem2D implements ISystem, Disposable {

    private final List<Entity> entities = new ArrayList();
    private Matrix4 transform;
    private final Vector2 position2D = new Vector2();
    private final Vector3 position3D = new Vector3();
    private Vector3 velocity;
    private Vector3 angularVelocity;
    private final Vector3 tmp = new Vector3();
    private Quaternion quat = new Quaternion();

    public PhysicsSystem2D() {
        Box2D.init();
        Global.getDynamicsWorld2D();
        Global.getDynamicsWorld2D().setContactListener(new ContactListener2D());
    }

    @Override
    public void add(Entity entity) {
        if (!entities.contains(entity)) {
            Body body = entity.get(Physics2D.class).body;
            body.setUserData(entity.id);
            entities.add(entity);
        }
    }

    @Override
    public void remove(Entity entity) {
        if (entities.contains(entity)) {
            Physics2D physics2D = entity.get(Physics2D.class);
            Global.getDynamicsWorld2D().destroyBody(physics2D.body);
        }
        entities.remove(entity);
    }

    @Override
    public void update(float delta) {
        for (Entity entity : entities) {
            if (entity.has(Velocity.class)) {
                position3D.set(entity.get(Transform.class).transform.getTranslation(tmp));
                position2D.set(position3D.x, position3D.z);
                velocity = entity.get(Velocity.class).velocity;
                angularVelocity = entity.get(Velocity.class).angularVelocity;
                Body body = entity.get(Physics2D.class).body;
                float angle = -entity.get(Transform.class).transform.getRotation(quat).getYawRad();

                if (!velocity.isZero() || !angularVelocity.isZero() || !PlayerKeyListener.joystickRight.joystick.isZero() || ((PlayerKeyListener.clickRight || PlayerKeyListener.clickMiddle) && entity.has(Puppet.class))) {
                    body.setTransform(position2D, angle);
                }
            }
        }

        Global.getDynamicsWorld2D().step(Global.getDeltaInSeconds(), 6, 2);

        for (Entity entity : entities) {
            if (entity.has(Velocity.class)) {
                if (!velocity.isZero() || !angularVelocity.isZero() || !PlayerKeyListener.joystickRight.joystick.isZero() || ((PlayerKeyListener.clickRight || PlayerKeyListener.clickMiddle) && entity.has(Puppet.class))) {
                    if(entity.has(Enemy.class) && entity.get(Transform.class).changedDirection2D){
                        entity.get(Transform.class).changedDirection2D = false;
                        continue;
                    }
                    Body body = entity.get(Physics2D.class).body;
                    Vector2 phPos = body.getPosition();
                    float angle = body.getAngle();
                    transform = entity.get(Transform.class).transform;
                    float y = transform.getTranslation(Vector3.Zero).y;
                    quat.set(transform.getRotation(quat));
                    quat.setEulerAnglesRad(-angle, quat.getPitchRad(), quat.getRollRad());
                    transform.set(quat);
                    transform.trn(new Vector3(phPos.x, y, phPos.y));
                }
            }
        }
    }

    public int getCount() {
        return entities.size();
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        for (Entity entity : entities) {
            Physics2D physics2D = entity.get(Physics2D.class);
            Global.getDynamicsWorld2D().destroyBody(physics2D.body);
        }
        entities.clear();
    }

}
