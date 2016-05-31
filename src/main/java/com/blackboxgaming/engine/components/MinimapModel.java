package com.blackboxgaming.engine.components;

import com.badlogic.gdx.graphics.Color;

/**
 * Describes an entities minimap icon.
 *
 * @author adrian.popa
 */
public class MinimapModel implements IComponent {

    /**
     * Whether to draw on the minimap.
     */
    public boolean ignore;
    public Color color;

    public MinimapModel(boolean ignore) {
        this.ignore = ignore;
    }

    public MinimapModel(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "MinimapModel{" + "ignore=" + ignore + ", color=" + color + '}';
    }

}
