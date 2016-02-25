package com.blackboxgaming.engine.components.ai;

import com.blackboxgaming.engine.components.IComponent;
import com.blackboxgaming.engine.Entity;

/**
 *
 * @author Adrian
 */
public class Follow implements IComponent {

    public Entity target;
    public float distance;

    public Follow(Entity target, float distance) {
        this.target = target;
        this.distance = distance;
    }

}
