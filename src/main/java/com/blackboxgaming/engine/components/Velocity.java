package com.blackboxgaming.engine.components;

import com.badlogic.gdx.math.Vector3;

/**
 *
 * @author Adrian
 */
public class Velocity implements IComponent {

    public final Vector3 velocity;
    public final Vector3 angularVelocity;
    public boolean trnFlag = false;

    public Velocity() {
        velocity = new Vector3();
        angularVelocity = new Vector3();
    }

    public Velocity(float x, float y, float z) {
        velocity = new Vector3(x, y, z);
        angularVelocity = new Vector3();
    }

    public Velocity(Vector3 copyVector) {
        velocity = copyVector.cpy();
        angularVelocity = new Vector3();
    }

    @Override
    public String toString() {
        return "Velocity{" + "velocity=" + velocity + ", angularVelocirt=" + angularVelocity + '}';
    }
    
}
