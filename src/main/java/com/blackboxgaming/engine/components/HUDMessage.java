package com.blackboxgaming.engine.components;

/**
 * A message that should be temporarily displayed on the screen.
 *
 * @author Adrian
 */
public class HUDMessage implements IComponent{

    public final String message;

    public HUDMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "HUDMessage{" + "message=" + message + '}';
    }

}
