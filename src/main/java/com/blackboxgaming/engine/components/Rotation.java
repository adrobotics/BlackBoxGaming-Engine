package com.blackboxgaming.engine.components;

import com.badlogic.gdx.math.Quaternion;

/**
 *
 * @author Adrian
 */
@Deprecated
public class Rotation implements IComponent {

    public Quaternion rotation;

    public Rotation(Quaternion rotation) {
        this.rotation = rotation;
    }

    public Rotation(float yaw, float pitch, float roll) {
        this(new Quaternion());
        rotation.setEulerAngles(yaw, pitch, roll);
    }

    @Override
    public String toString() {
        return "Rotation{" + "rotation=" + rotation + "}"
                + "\nEulerAngles{" + "yaw=" + rotation.getYaw() + ", pitch=" + rotation.getPitch() + ", roll=" + rotation.getRoll() + '}';
    }

}
