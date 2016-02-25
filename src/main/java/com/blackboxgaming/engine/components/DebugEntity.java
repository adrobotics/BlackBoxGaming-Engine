package com.blackboxgaming.engine.components;

import com.blackboxgaming.engine.Entity;

/**
 *
 * @author Adrian
 */
public class DebugEntity implements IComponent {

    public Entity entity;

    public DebugEntity(Entity entity) {
        this.entity = entity;
    }

}
