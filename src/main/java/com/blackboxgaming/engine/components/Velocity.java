package com.blackboxgaming.engine.components;

import com.badlogic.gdx.math.Vector3;

/**
 * Component that holds linear and angular velocity vectors.
 *
 * @author Adrian
 */
public class Velocity implements IComponent {

    public final Vector3 linear;
    public final Vector3 angular;

    public Velocity() {
        linear = new Vector3();
        angular = new Vector3();
    }

    public Velocity(float x, float y, float z) {
        linear = new Vector3(x, y, z);
        angular = new Vector3();
    }

    public Velocity(Vector3 copyVector) {
        linear = copyVector.cpy();
        angular = new Vector3();
    }

    @Override
    public String toString() {
        return "Velocity{" + "linear=" + linear + ", angular=" + angular + '}';
    }

}
