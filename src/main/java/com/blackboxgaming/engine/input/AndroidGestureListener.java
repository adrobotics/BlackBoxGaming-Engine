package com.blackboxgaming.engine.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Adrian
 */
public class AndroidGestureListener implements GestureDetector.GestureListener {

    public static boolean panning;
    public static boolean panningLeft;
    public static boolean panningRight;
    public static boolean panningUp;
    public static boolean panningDown;
    private static final Vector2 touchPoint = new Vector2();
    public static final Vector2 panAmount = new Vector2();

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        if (!panning && x <= Gdx.graphics.getWidth() / 2) {
            touchPoint.x = x;
            touchPoint.y = y;
            return true;
        } else {
            touchPoint.setZero();
            return false;
        }
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        if(x > Gdx.graphics.getWidth() / 2){
            return false;
        }
        if(touchPoint.isZero()){
            return false;
        }
        panning = true;
        if (x < touchPoint.x) {
            panningLeft = true;
            panningRight = false;
        } else if (x > touchPoint.x) {
            panningLeft = false;
            panningRight = true;
        }
        if (y < touchPoint.y) {
            panningUp = true;
            panningDown = false;
        } else if (y > touchPoint.y) {
            panningUp = false;
            panningDown = true;
        }
        float threshold = 150;
        panAmount.set(touchPoint.cpy().sub(x, y));
        panAmount.x = (panAmount.x > threshold ? threshold : (panAmount.x < -threshold ? -threshold : panAmount.x));
        panAmount.y = (panAmount.y > threshold ? threshold : (panAmount.y < -threshold ? -threshold : panAmount.y));
        panAmount.x = panAmount.x / threshold;
        panAmount.y = panAmount.y / threshold;
        panAmount.x = (panAmount.x > 0.5f ? panAmount.x - 0.5f : (panAmount.x < -0.5f ? -1 + 0.5f : 0));
        panAmount.y = (panAmount.y > 0.5f ? 1 : (panAmount.y < -0.5f ? -1 : 0));
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        panning = false;
        panAmount.setZero();
        touchPoint.setZero();
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

}
