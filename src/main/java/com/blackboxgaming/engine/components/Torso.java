package com.blackboxgaming.engine.components;

import com.blackboxgaming.engine.Entity;

/**
 *
 * @author Adrian
 */
public class Torso implements IComponent {

    public final Entity torso;

    public Torso(Entity torso) {
        this.torso = torso;
    }

}
