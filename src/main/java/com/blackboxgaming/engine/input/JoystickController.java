package com.blackboxgaming.engine.input;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.blackboxgaming.engine.util.Global;

/**
 *
 * @author Adrian
 */
public class JoystickController {

    private final Camera camera = Global.getCamera();
    public final Vector2 joystick = new Vector2();
    private final Vector2 cameraV2 = new Vector2();
    public float angleToWorldX;
    public float angle;
    public boolean syncRotation = true;

    public void set(float x, float y) {
        joystick.set(x, y);
        joystick.nor();
        angleToWorldX = joystick.angle();

        cameraV2.set(camera.direction.x, camera.direction.z);
        angle = cameraV2.angle() + 90;
        angle = angle >= 360 ? angle - 360 : angle;
        joystick.rotate(angle);
    }

    @Override
    public String toString() {
        return "JoystickController{" + "joystick=" + joystick + ", angle=" + angleToWorldX + '}';
    }

}
