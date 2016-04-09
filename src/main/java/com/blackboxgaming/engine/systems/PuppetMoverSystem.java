package com.blackboxgaming.engine.systems;

import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.components.Transform;
import com.blackboxgaming.engine.components.Speed;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Animation;
import com.blackboxgaming.engine.components.Puppet;
import com.blackboxgaming.engine.components.Velocity;
import com.blackboxgaming.engine.input.AndroidGestureListener;
import com.blackboxgaming.engine.input.PlayerKeyListener;
import com.blackboxgaming.engine.util.Global;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adrian
 */
public class PuppetMoverSystem implements ISystem, Disposable {

    private final List<Entity> entities = new ArrayList();
    private Matrix4 transform;
    private Vector3 velocity;
    private Vector3 angularVelocity;
    private Speed speed;
    private Puppet puppet;
    private float linearSpeed;
    private float angularSpeed;
    private final Vector3 vAux = new Vector3();
    private final Vector3 angVAux = new Vector3();
    private final Quaternion tmp = new Quaternion();

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
            speed = entity.get(Speed.class);
            linearSpeed = speed.speed;
            angularSpeed = speed.angularSpeed;

            // is flying?
            puppet = entity.get(Puppet.class);
            if (puppet.isFlying) {
                velocity.set(vAux);
                angularVelocity.set(angVAux);
                continue;
            }

            // speed boost
            if (PlayerKeyListener.leftShift) {
                linearSpeed *= speed.linearBoost;
                angularSpeed *= speed.angularBoost;
            } else if (PlayerKeyListener.leftAlt) {
                linearSpeed /= speed.linearBoost;
                angularSpeed /= speed.linearBoost;
            }

            // rotation
            if (!PlayerKeyListener.clickRight && !PlayerKeyListener.clickMiddle) {
                if (PlayerKeyListener.rotateLeft) {
                    angVAux.y = angularSpeed;
                }
                if (PlayerKeyListener.rotateRight) {
                    angVAux.y = -angularSpeed;
                }
                // android
                if (Global.START_WITH_ANDROID_GESTURE_LISTENER && AndroidGestureListener.panning) {
                    angVAux.y = angularSpeed * AndroidGestureListener.panAmount.x;
                }
            } else {
                Quaternion cameraQuat = Global.getCamera().view.getRotation(tmp).conjugate();
                if (PlayerKeyListener.clickRight) {
                    float cameraAngle = cameraQuat.getYaw();
                    float targetAngle = transform.getRotation(tmp).conjugate().getYaw();
//                    angVAux.y = targetAngle + cameraAngle + 90;
//                    angVAux.y /= delta; // so to negate delta scaling in VelocitySystem
                    transform.rotate(Vector3.Y, targetAngle + cameraAngle + 90);
                } else if (PlayerKeyListener.clickMiddle) {
                    // directly sets transform, should find better way
                    transform.set(transform.getTranslation(Vector3.Zero), cameraQuat);
                    transform.rotate(Vector3.Y, 90);
                }
            }

            // movement
            if ((PlayerKeyListener.backward || PlayerKeyListener.forward) && (PlayerKeyListener.strafeLeft || PlayerKeyListener.strafeRight)) {
                // pressing two keys
                float angle;
                vAux.x = (PlayerKeyListener.forward ? 1f : -1f);
                angle = (PlayerKeyListener.forward ? 45f : -45f);
                vAux.rotate(Vector3.Y, (PlayerKeyListener.strafeLeft ? angle : -angle));
            } else {
                // pressing one key or no key
                vAux.x = (PlayerKeyListener.forward ? 1f : (PlayerKeyListener.backward ? -1f : vAux.x));
                vAux.z = (PlayerKeyListener.strafeRight ? 1f : (PlayerKeyListener.strafeLeft ? -1f : vAux.z));

                // halt velocity if no keys are pressed
                vAux.x = (!PlayerKeyListener.forward && !PlayerKeyListener.backward ? 0f : vAux.x);
                vAux.z = (!PlayerKeyListener.strafeRight && !PlayerKeyListener.strafeLeft ? 0f : vAux.z);
            }
            // android
            if (Global.START_WITH_ANDROID_GESTURE_LISTENER && AndroidGestureListener.panning) {
                vAux.x = AndroidGestureListener.panAmount.y;
            }

            // setting new values
            velocity.set(vAux.scl(linearSpeed));
            angularVelocity.set(angVAux);

            // animation
            if (entity.has(Animation.class)) {
                AnimationController controller = entity.get(Animation.class).controller;
                controller.allowSameAnimation = false;
                if (PlayerKeyListener.clickLeft) {
//                    controller.setAnimation("Attack", -1);
                    controller.animate("Attack", -1, null, 0.175f);
                } else {
                    if (!controller.inAction) {
                        if (PlayerKeyListener.forward || PlayerKeyListener.backward || PlayerKeyListener.strafeLeft || PlayerKeyListener.strafeRight) {
                            float animationSpeed = 1;
                            if (PlayerKeyListener.backward) {
                                animationSpeed *= -1;
                            }
                            if (PlayerKeyListener.leftShift) {
                                animationSpeed *= speed.linearBoost;
                            }
                            if (PlayerKeyListener.leftShift) {
                                controller.animate("Walk", -1, animationSpeed, null, 0.175f);
                            } else if (PlayerKeyListener.leftAlt) {
                                controller.animate("Sneak", -1, animationSpeed, null, 0.175f);
                            } else {
                                controller.animate("Walk", -1, animationSpeed, null, 0.175f);
                            }
                        } else {
                            controller.animate("Idle", -1, null, 0.175f);
                        }
                    }
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
