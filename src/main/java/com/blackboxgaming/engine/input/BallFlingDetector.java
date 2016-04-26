package com.blackboxgaming.engine.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureAdapter;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Physics;
import com.blackboxgaming.engine.components.Transform;
import com.blackboxgaming.engine.systems.PhysicsSystem;
import com.blackboxgaming.engine.util.Global;
import com.blackboxgaming.engine.util.OldButNotThatOldWorldSetup;

/**
 *
 * @author Adrian
 */
@Deprecated
public class BallFlingDetector extends GestureDetector {

    public BallFlingDetector() {
        super(new BallFlingAdaptor());
        setTapSquareSize(1);
    }

}

class BallFlingAdaptor extends GestureAdapter {

    private Entity ball;
    public static double lastFlingVelocity;
    private final Vector3 intersection = new Vector3();
    private final Vector3 bbCenter = new Vector3(-Global.boxLength / 4f, 0, 0);
    private final Vector3 bbDim = new Vector3(Global.boxLength / 2f, 0.5f, Global.boxWidth);
    private final float yLevel = 0 + 0.5f;
    private Plane x0zPlane = new Plane(Vector3.Y, 0);

    private boolean isInsideCastArea(float x, float y) {
        if (Intersector.intersectRayBoundsFast(Global.getCamera().getPickRay(x, y), bbCenter, bbDim)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        if (Intersector.intersectRayPlane(Global.getCamera().getPickRay(x, y), x0zPlane, intersection)) {
            if (isInsideCastArea(x, y)) {
                ball = OldButNotThatOldWorldSetup.createBall(intersection.add(0, yLevel, 0));
            } else {
                ball = null;
            }
        }
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        if (ball != null && Engine.systemManager.has(PhysicsSystem.class)) {
            ball.add(OldButNotThatOldWorldSetup.createBallPhysics());
            Engine.systemManager.get(PhysicsSystem.class).add(ball);
        }
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        if (ball != null && Intersector.intersectRayPlane(Global.getCamera().getPickRay(x, y), x0zPlane, intersection)) {
            if (intersection.x < -Global.boxLength / 2f + 0.25f) {
                intersection.x = -Global.boxLength / 2f + 0.25f;
            }
            if (intersection.x > Global.boxLength / 2f - 0.25f) {
                intersection.x = Global.boxLength / 2f - 0.25f;
            }
            if (intersection.z < -Global.boxWidth / 2f + 0.25f) {
                intersection.z = -Global.boxWidth / 2f + 0.25f;
            }
            if (intersection.z > Global.boxWidth / 2f - 0.25f) {
                intersection.z = Global.boxWidth / 2f - 0.25f;
            }
            ball.get(Transform.class).transform.setToTranslation(intersection.add(0, yLevel, 0));
        }
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        if (ball != null && Engine.systemManager.has(PhysicsSystem.class)) {
            ball.add(OldButNotThatOldWorldSetup.createBallPhysics());
            Engine.systemManager.get(PhysicsSystem.class).add(ball);
        }
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
//        lastFlingVelocity = FastMath.hypot(velocityX, velocityY);
        float vx = -(velocityY * Global.boxLength / Gdx.graphics.getHeight());
        float vz = velocityX * Global.boxWidth / Gdx.graphics.getWidth();
        if (ball != null && Engine.systemManager.has(PhysicsSystem.class)) {
            ball.get(Physics.class).body.applyCentralImpulse(new Vector3(vx, 0, vz).scl(1.0f));
            System.out.println(vx + ", " +vz);
        }
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        if (ball != null && Engine.systemManager.has(PhysicsSystem.class)) {
            ball.add(OldButNotThatOldWorldSetup.createBallPhysics());
            Engine.systemManager.get(PhysicsSystem.class).add(ball);
        }
        return false;
    }

}
