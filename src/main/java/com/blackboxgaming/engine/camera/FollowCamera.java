package com.blackboxgaming.engine.camera;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.blackboxgaming.engine.Entity;

/**
 *
 * @author Adrian
 */
public class FollowCamera extends PerspectiveCamera {

    public static Entity target;
    float r = 0;

    public FollowCamera() {
    }

    public FollowCamera(float fieldOfViewY, float viewportWidth, float viewportHeight) {
        super(fieldOfViewY, viewportWidth, viewportHeight);
    }

    @Override
    public void update() {
//        if (target == null) {
//            super.update();
//            return;
//        }
        
        //r += 0.1f;
//        r %= 360;
//        System.out.println(r);
//        Vector3 targetPosition = ((Position) target.getComponent(Position.class)).toVector3();
//        Vector3 cameraPosition = targetPosition.add(new Vector3(-5, 5, 0));
//        //position.set(cameraPosition.x, cameraPosition.y, cameraPosition.z);
//        //lookAt(targetPosition.x, targetPosition.y, targetPosition.z);
//        rotateAround(Vector3.Zero, Vector3.Y, r);
//        
//
//        //super.up.set(0, 1, 0);
//        super.lookAt(targetPosition.toVector3());
        super.update();
    }

}
