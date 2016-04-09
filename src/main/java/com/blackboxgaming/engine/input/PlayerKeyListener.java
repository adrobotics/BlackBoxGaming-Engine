package com.blackboxgaming.engine.input;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

/**
 *
 * @author Adrian
 */
public class PlayerKeyListener extends InputAdapter {
    
    public static JoystickController joystickLeft = new JoystickController();
    public static JoystickController joystickRight = new JoystickController();

    // anything
    public static boolean anythingDown;
    public static boolean keyDown;
    public static boolean clickDown;
    
    // mouse
    public static boolean clickLeft;
    public static boolean clickMiddle;
    public static boolean clickRight;
    public static boolean scrollUp;
    public static boolean scrollDown;

    public static final int clickLeftButton = Buttons.LEFT;
    public static final int clickMiddleButton = Buttons.MIDDLE;
    public static final int clickRightButton = Buttons.RIGHT;
    public static final int scrollUpButton = Buttons.FORWARD;
    public static final int scrollDownButton = Buttons.BACK;

    // wasd
    public static boolean forward;
    public static boolean backward;
    public static boolean strafeLeft;
    public static boolean strafeRight;
    public static boolean rotateLeft;
    public static boolean rotateRight;

    public static final int forwardKey = Keys.W;
    public static final int backwardKey = Keys.S;
    public static final int strafeLeftKey = Keys.A;
    public static final int strafeRightKey = Keys.D;
    public static final int rotateLeftKey = Keys.Q;
    public static final int rotateRightKey = Keys.E;

    // arrows
    public static boolean up;
    public static boolean down;
    public static boolean left;
    public static boolean right;
    public static boolean rotateLeftArrow;
    public static boolean rotateRightArrow;

    public static final int upKey = Keys.UP;
    public static final int downKey = Keys.DOWN;
    public static final int leftKey = Keys.LEFT;
    public static final int rightKey = Keys.RIGHT;
    public static final int rotateLeftArrowKey = Keys.PAGE_UP;
    public static final int rotateRightArrowKey = Keys.PAGE_DOWN;
    
    // special
    public static boolean space;
    public static boolean leftShift;
    public static boolean rightShift;
    public static boolean leftAlt;
    public static boolean rightAlt;

    public static final int spaceKey = Keys.SPACE;
    public static final int leftShiftKey = Keys.SHIFT_LEFT;
    public static final int rightShiftKey = Keys.SHIFT_RIGHT;
    public static final int leftAltKey = Keys.ALT_LEFT;
    public static final int rightAltKey = Keys.ALT_RIGHT;

    @Override
    public boolean keyDown(int keycode) {
        keyChanged(keycode, true);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        keyChanged(keycode, false);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchChanged(button, true);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchChanged(button, false);
        return false;
    }
    
    private void keyChanged(int keycode, boolean value){
        anythingDown = value;
        keyDown = value;
        switch (keycode) {
            // wasd
            case forwardKey: forward = value; break;
            case backwardKey: backward = value; break;
            case strafeLeftKey: strafeLeft = value; break;
            case strafeRightKey: strafeRight = value; break;
            case rotateLeftKey: rotateLeft = value; break;
            case rotateRightKey: rotateRight = value; break;
              
            // arrows
            case upKey: up = value; break;
            case downKey: down = value; break;
            case leftKey: left = value; break;
            case rightKey: right = value; break;
            case rotateLeftArrowKey: rotateLeftArrow = value; break;
            case rotateRightArrowKey: rotateRightArrow = value; break;
                
            // special
            case spaceKey: space = value; break;
            case leftShiftKey: leftShift = value; break;
            case rightShiftKey: rightShift = value; break;
            case leftAltKey: leftAlt = value; break;
            case rightAltKey: rightAlt = value; break;
        }
    }
    
    private void touchChanged(int button, boolean value){
        anythingDown = value;
        clickDown = value;
        switch (button) {
            case clickLeftButton: clickLeft = value; break;
            case clickMiddleButton: clickMiddle = value; break;
            case clickRightButton: clickRight = value; break;
            case scrollUpButton: scrollUp = value; break;
            case scrollDownButton: scrollDown = value; break;
        }
    }

}
