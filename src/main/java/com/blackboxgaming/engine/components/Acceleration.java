package com.blackboxgaming.engine.components;

import com.badlogic.gdx.math.Vector3;

/**
 *
 * @author Adrian
 */
public class Acceleration implements IComponent {
    
    public float x;
    public float y;
    public float z;

    public Acceleration(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 toVector3(){
        Vector3 vector = new Vector3();
        vector.x = this.x;
        vector.y = this.y;
        vector.z = this.z;
        return vector;
    }

    @Override
    public String toString() {
        return "Acceleration{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
    }

}
