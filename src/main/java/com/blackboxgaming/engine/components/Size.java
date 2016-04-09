package com.blackboxgaming.engine.components;

/**
 *
 * @author Adrian
 */
@Deprecated
public class Size implements IComponent{

    public float x = 0, y = 0, z = 0;

    public Size(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

}
