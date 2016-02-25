package com.blackboxgaming.engine.components;

/**
 *
 * @author Adrian
 */
public class GenericSize implements IComponent {

    public float size;

    public GenericSize(float size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "GenericSize{" + "size=" + size + '}';
    }

}
