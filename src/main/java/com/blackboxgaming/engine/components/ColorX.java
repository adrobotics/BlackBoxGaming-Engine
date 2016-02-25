package com.blackboxgaming.engine.components;

/**
 *
 * @author Adrian
 */
public class ColorX implements IComponent {

    public com.badlogic.gdx.graphics.Color color;

    public ColorX(com.badlogic.gdx.graphics.Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "ColorComponent{" + "color=" + color + '}';
    }

}
