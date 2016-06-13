package com.blackboxgaming.engine.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

/**
 * Represents a multiline message displayed at a certain position on screen. Use
 * \n for a new line.
 *
 * @author adrian.popa
 */
public class TextMessage implements IComponent {

    public final String text;
    public final float x, y;
    public final Color color;
    public final int alignment;

    /**
     * Used by
     * {@link com.blackboxgaming.engine.systems.render.TextRendererSystem}
     */
    public Label label;

    /**
     * Renders multiline text at the given position
     *
     * @param text use \n for new line
     * @param x
     * @param y
     * @param color
     * @param alignment use {@link Align}
     */
    public TextMessage(String text, float x, float y, Color color, int alignment) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
        this.alignment = alignment;
    }

    /**
     * Renders multiline text at the given position
     *
     * @param text use \n for new line
     * @param x
     * @param y
     */
    public TextMessage(String text, float x, float y) {
        this(text, x, y, Color.GREEN, Align.center);
    }

}
