package com.blackboxgaming.engine.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Disposable;

/**
 *
 * @author Adrian
 */
public class InputManager implements Disposable {

    private final InputMultiplexer multiplexer = new InputMultiplexer();

    public InputManager() {
        Gdx.input.setInputProcessor(multiplexer);
    }

    public void add(InputProcessor inputProcessor) {
        System.out.println("Adding inputProcessor: " + inputProcessor);
        multiplexer.addProcessor(inputProcessor);
    }
    
    public void remove(InputProcessor inputProcessor) {
        System.out.println("Adding inputProcessor: " + inputProcessor);
        multiplexer.removeProcessor(inputProcessor);
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        multiplexer.clear();
    }

}
