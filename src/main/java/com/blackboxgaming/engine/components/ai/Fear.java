package com.blackboxgaming.engine.components.ai;

import com.blackboxgaming.engine.components.IComponent;
import com.blackboxgaming.engine.Entity;

/**
 *
 * @author Adrian
 */
public class Fear implements IComponent {

    public Entity target;
    public float distance;

    public Fear(Entity target, float distance) {
        this.target = target;
        this.distance = distance;
    }

}
