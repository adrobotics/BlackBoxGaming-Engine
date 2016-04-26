package com.blackboxgaming.engine.systems;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.components.Transform;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.input.OrbitCameraListener;
import com.blackboxgaming.engine.components.OrbitCameraFocus;
import com.blackboxgaming.engine.input.PlayerKeyListener;
import com.blackboxgaming.engine.util.Global;

/**
 * System that sets the camera to follow and orbit around the last entity that
 * was added to it.
 *
 * @author Adrian
 */
public class OrbitCameraSystem extends AbstractSystem {

    private Entity target;
    private Matrix4 targetTransform;
    private final Vector3 position = new Vector3();
    private final Vector3 oldPosition = new Vector3();
    private final Quaternion tmp = new Quaternion();
    private final Camera camera = Global.getCamera();
    private final OrbitCameraListener orbitCameraController;
    private float oldRotationValue = 0;

    public OrbitCameraSystem() {
        requiredComponents.add(OrbitCameraFocus.class);
        orbitCameraController = new OrbitCameraListener(camera);
        Engine.inputManager.add(orbitCameraController);
    }

    @Override
    public void add(Entity entity) {
        if (!accept(entity)) {
            return;
        }
        if (target != null) {
            target.remove(OrbitCameraFocus.class);
        }
        target = entity;
        targetTransform = target.get(Transform.class).transform;
        targetTransform.getTranslation(position);
        orbitCameraController.target = position;
        orbitCameraController.autoUpdate = false;
    }

    @Override
    public void remove(Entity entity) {
        if (entity.equals(target)) {
            System.err.println("Should give this system something else to look at!");
        }
    }

    @Override
    public void update(float delta) {
        targetTransform.getTranslation(position);
        camera.position.add(position.cpy().sub(oldPosition));
        oldPosition.set(position.cpy());

        if ((PlayerKeyListener.rotateLeft || PlayerKeyListener.rotateRight) && Global.SYNC_KEYBOARD_CAM_ROTATION) {
            float newRotation = targetTransform.getRotation(tmp).getYaw();
            camera.rotateAround(targetTransform.getTranslation(Vector3.Zero), Vector3.Y, -(oldRotationValue - newRotation));
            oldRotationValue = newRotation;
        }

        camera.update();
    }

    @Override
    public void dispose() {
        super.dispose();
        Engine.inputManager.remove(orbitCameraController);
    }

}
