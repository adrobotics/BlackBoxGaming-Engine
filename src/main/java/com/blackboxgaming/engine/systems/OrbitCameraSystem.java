package com.blackboxgaming.engine.systems;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.components.Transform;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.input.OrbitCameraListener;
import com.blackboxgaming.engine.components.OrbitCameraFocus;
import com.blackboxgaming.engine.input.AndroidGestureListener;
import com.blackboxgaming.engine.input.PlayerKeyListener;
import com.blackboxgaming.engine.util.Global;

/**
 *
 * @author Adrian
 */
public class OrbitCameraSystem implements ISystem, Disposable {

    private Entity target;
    private Matrix4 targetTransform;
    private final Vector3 position = new Vector3();
    private final Vector3 oldPosition = new Vector3();
    private final Quaternion tmp = new Quaternion();
    private final Camera camera = Global.getCamera();
    public final OrbitCameraListener orbitCameraController;
    private float oldRotationValue = 0;
    public boolean mouseListener;

    public OrbitCameraSystem() {
        this(true);
    }

    public OrbitCameraSystem(boolean mouseListener) {
        this.mouseListener = mouseListener;
        if (!Global.START_WITH_ANDROID_GESTURE_LISTENER) {
            this.orbitCameraController = new OrbitCameraListener(Global.getCamera());
            if (mouseListener) {
                Engine.inputManager.add(orbitCameraController);
            }
        } else {
            orbitCameraController = null;
        }
    }

    @Override
    public void add(Entity entity) {
        if (target == null) {
            target = entity;
        } else {
            target.remove(OrbitCameraFocus.class);
            target = entity;
        }
        targetTransform = target.get(Transform.class).transform;
        position.set(targetTransform.getTranslation(Vector3.Zero));
        if (!Global.START_WITH_ANDROID_GESTURE_LISTENER) {
            orbitCameraController.target = position;
            orbitCameraController.autoUpdate = false;
        }
    }

    @Override
    public void remove(Entity entity) {
        if (target == entity) {
            target = null;
        }
    }

    @Override
    public void update(float delta) {
        if (target == null) {
            return;
        }

        position.set(targetTransform.getTranslation(Vector3.Zero));
        camera.position.add(position.cpy().sub(oldPosition));
        oldPosition.set(position.cpy());

        if ((PlayerKeyListener.rotateLeft || PlayerKeyListener.rotateRight) && Global.SYNC_KEYBOARD_CAM_ROTATION) {
            float newRotation = targetTransform.getRotation(tmp).getYaw();
            camera.rotateAround(targetTransform.getTranslation(Vector3.Zero), Vector3.Y, -(oldRotationValue - newRotation));
            oldRotationValue = newRotation;
        }
        if ((AndroidGestureListener.panningLeft || AndroidGestureListener.panningRight) && Global.SYNC_KEYBOARD_CAM_ROTATION && Global.START_WITH_ANDROID_GESTURE_LISTENER) {
            float newRotation = targetTransform.getRotation(tmp).getYaw();
            camera.rotateAround(targetTransform.getTranslation(Vector3.Zero), Vector3.Y, -(oldRotationValue - newRotation));
            oldRotationValue = newRotation;
        }

        Global.getCamera().update();
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        target = null;
    }

}
