package com.blackboxgaming.engine.components;

import com.blackboxgaming.engine.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adrian
 */
public class Parent implements IComponent {

    public final List<Entity> children = new ArrayList();
    public boolean rootParent = false;

    public Parent(boolean rootParent) {
        this.rootParent = rootParent;
    }

    public Parent(Entity... children) {
        for (Entity child : children) {
            if (child.has(Child.class)) {
                this.children.add(child);
            }
        }
    }

    public void add(Entity child) {
        if (child.has(Child.class)) {
            if (!children.contains(child)) {
                children.add(child);
            }
        }
    }
}
