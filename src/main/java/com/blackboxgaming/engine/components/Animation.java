package com.blackboxgaming.engine.components;

import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

/**
 *
 * @author Adrian
 */
public class Animation implements IComponent {

    public final AnimationController controller;

    public Animation(AnimationController controller) {
        this.controller = controller;
    }

    public void setAnimation(String animationId, int loopCount) {
        controller.setAnimation(animationId, loopCount);
    }

    @Override
    public String toString() {
        return "Animation{" + "controller=" + controller + '}';
    }

}
