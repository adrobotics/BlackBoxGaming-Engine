package com.blackboxgaming.engine.input;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.blackboxgaming.engine.util.Global;

/**
 *
 * @author Adrian
 */
public class OrbitCameraListener extends CameraInputController {

    public OrbitCameraListener(Camera camera) {
        super(camera);
    }

    @Override
    protected boolean process(float deltaX, float deltaY, int button) {
        Vector3 tmp = new Vector3();
        if ((Global.leftMouseCameraMove && button == PlayerKeyListener.clickLeftButton) || button == PlayerKeyListener.clickRightButton || button == PlayerKeyListener.clickMiddleButton) {
            tmp.set(camera.direction).crs(camera.up).y = 0f;
            camera.rotateAround(target, tmp.nor(), deltaY * rotateAngle);
            camera.rotateAround(target, Vector3.Y, deltaX * -rotateAngle);
        }
        if (autoUpdate) {
            camera.update();
        }

        return true;
    }

}
