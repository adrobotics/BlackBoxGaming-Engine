package com.blackboxgaming.engine.components;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.blackboxgaming.engine.Entity;

/**
 *
 * @author Adrian
 */
public class Child implements IComponent {

    public final Entity parent;
    public final Matrix4 relativeTransformToParent;
    public boolean resetRotation;

    public Child(Entity parent, Matrix4 relativeTransformToParent) {
        this.parent = parent;
        this.relativeTransformToParent = relativeTransformToParent;
    }

    public Child(Entity parent) {
        this.parent = parent;
        this.relativeTransformToParent = new Matrix4();
    }

    public Child(Entity parent, Vector3 position) {
        this.parent = parent;
        this.relativeTransformToParent = new Matrix4().setToTranslation(position);
    }
    
    public Child(Entity parent, Vector3 position, boolean resetRotation) {
        this.resetRotation = resetRotation;
        this.parent = parent;
        this.relativeTransformToParent = new Matrix4().setToTranslation(position);
    }

}
