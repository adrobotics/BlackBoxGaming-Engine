package com.blackboxgaming.engine.systems;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.components.Transform;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Child;
import com.blackboxgaming.engine.components.Parent;
import com.blackboxgaming.engine.input.PlayerKeyListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Adrian
 */
public class ParentChildSystem implements ISystem, Disposable {

    private final List<Entity> entities = new LinkedList();

    @Override
    public void add(Entity entity) {
        if (!entities.contains(entity)) {
            entities.add(entity);
        }
    }

    @Override
    public void remove(Entity entity) {
        entities.remove(entity);
    }

    @Override
    public void update(float delta) {
//        for (Entity parent : entities) {
//            for (Entity child : parent.get(Parent.class).children) {
//                Matrix4 parentTransform = parent.get(Transform.class).transform;
//                Matrix4 childTransform = child.get(Transform.class).transform;
//                Matrix4 childRelativeTransformToParent = child.get(Child.class).relativeTransformToParent;
//                childTransform.set(parentTransform.cpy().mul(childRelativeTransformToParent));
//            }
//        }

        for (Entity parent : entities) {
            if (parent.get(Parent.class).rootParent) {
                traverseChildren(parent);
            }
        }
    }

    private void traverseChildren(Entity parent) {
        for (Entity child : parent.get(Parent.class).children) {
            Matrix4 parentTransform = parent.get(Transform.class).transform;
            Matrix4 childTransform = child.get(Transform.class).transform;
            Matrix4 childRelativeTransformToParent = child.get(Child.class).relativeTransformToParent;
//            childTransform.set(parentTransform.cpy().mul(childRelativeTransformToParent));

            if (!child.get(Child.class).resetRotation) {
                childTransform.set(parentTransform.cpy().mul(childRelativeTransformToParent));
            } else {
                if (!PlayerKeyListener.joystickRight.joystick.isZero()) {
                    childTransform.setTranslation((parentTransform.cpy().mul(childRelativeTransformToParent).getTranslation(Vector3.Zero)));
                } else {
                    childTransform.set(parentTransform.cpy().mul(childRelativeTransformToParent));
                }
            }

            if (child.has(Parent.class)) {
                traverseChildren(child);
            }
        }
    }

    public void reverse() {
        Collections.reverse(entities);
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        entities.clear();
    }

}
