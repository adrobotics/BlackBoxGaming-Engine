package com.blackboxgaming.engine.components;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

/**
 *
 * @author Adrian
 */
public class Transform implements IComponent {

    public final Matrix4 transform;
    public boolean changedDirection2D = false;

    public Transform() {
        transform = new Matrix4();
    }
    
    public Transform(Vector3 position) {
        this();
        transform.setTranslation(position);
    }
    
    public Transform(Vector3 position, Quaternion rotation) {
        transform = new Matrix4(position, rotation, new Vector3(1, 1, 1));
    }

    public Transform(float x, float y, float z) {
        this(new Vector3(x, y, z));
    }
    
    public Transform(float x, float y, float z,float yaw, float pitch, float roll) {
        this(new Vector3(x, y, z), new Quaternion().setEulerAngles(yaw, pitch, roll));
    }

    public Transform(Matrix4 transform) {
        this.transform = transform.cpy();
    }

    @Override
    public String toString() {
        return "Transform{" + "transform=" + transform + '}';
    }

}
