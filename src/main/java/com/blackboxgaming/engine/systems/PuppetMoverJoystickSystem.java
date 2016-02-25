package com.blackboxgaming.engine.systems;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.components.Transform;
import com.blackboxgaming.engine.components.Speed;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Puppet;
import com.blackboxgaming.engine.components.Torso;
import com.blackboxgaming.engine.components.Velocity;
import com.blackboxgaming.engine.input.PlayerKeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adrian
 */
public class PuppetMoverJoystickSystem implements ISystem, Disposable {

    private final List<Entity> entities = new ArrayList();
    private Matrix4 transform;
    private Vector3 velocity;
    private Vector3 angularVelocity;
    private Speed speedComponent;
    private Puppet puppet;
    private float linearSpeed;
    private float angularSpeed;
    private final Vector3 vAux = new Vector3();
    private final Vector3 angVAux = new Vector3();
    private Quaternion tmp = new Quaternion();
    private Vector3 tmpv = new Vector3();

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
            // clearing old data
            vAux.setZero();
            angVAux.setZero();

            transform = entity.get(Transform.class).transform;
            velocity = entity.get(Velocity.class).velocity;
            angularVelocity = entity.get(Velocity.class).angularVelocity;
            speedComponent = entity.get(Speed.class);
            linearSpeed = speedComponent.speed;
            angularSpeed = speedComponent.angularSpeed;

            // is flying?
            puppet = entity.get(Puppet.class);
            if (puppet.isFlying) {
                velocity.set(vAux);
                angularVelocity.set(angVAux);
                continue;
            }

            // speed boost
            if (PlayerKeyListener.leftShift) {
                linearSpeed *= speedComponent.linearBoost;
                angularSpeed *= speedComponent.angularBoost;
            }

            if (!entity.has(Torso.class)) {
                // for single bodied object
                if (!PlayerKeyListener.joystickLeft.joystick.isZero()) {
                    vAux.set(PlayerKeyListener.joystickLeft.joystick.x, 0, PlayerKeyListener.joystickLeft.joystick.y);
                    velocity.set(vAux.scl(linearSpeed));
                    transform.trn(velocity.cpy().scl(delta));
                    transform.translate(velocity.cpy().scl(delta).scl(-1));
                    if (PlayerKeyListener.joystickLeft.syncRotation && PlayerKeyListener.joystickRight.joystick.isZero()) {
                        transform.getTranslation(tmpv);
                        transform.setFromEulerAngles(-PlayerKeyListener.joystickLeft.angleToWorldX - PlayerKeyListener.joystickLeft.angle, 0, 0).trn(tmpv);
                    }
                }
                if (!PlayerKeyListener.joystickRight.joystick.isZero()) {
                    transform.getTranslation(tmpv);
                    transform.setFromEulerAngles(-PlayerKeyListener.joystickRight.angleToWorldX - PlayerKeyListener.joystickRight.angle, 0, 0).trn(tmpv);
                }
            } else {
                // for double bodied object
                if (!PlayerKeyListener.joystickLeft.joystick.isZero()) {
                    vAux.set(PlayerKeyListener.joystickLeft.joystick.x, 0, PlayerKeyListener.joystickLeft.joystick.y);
                    velocity.set(vAux.scl(linearSpeed));
                    transform.trn(velocity.cpy().scl(delta));
                    transform.translate(velocity.cpy().scl(delta).scl(-1));
//                    if (PlayerKeyListener.joystickLeft.syncRotation && PlayerKeyListener.joystickRight.joystick.isZero()) {
                    if (PlayerKeyListener.joystickLeft.syncRotation) {
                        transform.getTranslation(tmpv);
                        transform.setFromEulerAngles(-PlayerKeyListener.joystickLeft.angleToWorldX - PlayerKeyListener.joystickLeft.angle, 0, 0).trn(tmpv);
                    }

                    if (PlayerKeyListener.joystickRight.joystick.isZero()) {
                        
                    }
                }
                if (!PlayerKeyListener.joystickRight.joystick.isZero()) {
                    Matrix4 torsoTransform = entity.get(Torso.class).torso.get(Transform.class).transform;
//                    Matrix4 torsoTransform = entity.get(Torso.class).torso.get(Child.class).relativeTransformToParent;
//                    torsoTransform.setFromEulerAngles(0, 0, 0);
                    torsoTransform.getTranslation(tmpv);
                    torsoTransform.setFromEulerAngles(-PlayerKeyListener.joystickRight.angleToWorldX - PlayerKeyListener.joystickRight.angle, 0, 0).trn(tmpv);
                }
            }
        }
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        entities.clear();
    }

}
