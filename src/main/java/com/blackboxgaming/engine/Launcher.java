package com.blackboxgaming.engine;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Launches demo {@link BlackBoxGame} as a {@link LwjglApplication}. This class
 * is mentioned in build.gradle as project.ext.mainClassName, thus the demo can
 * be launched directly.
 *
 * @author adrian.popa
 * @see LwjglApplication
 */
public class Launcher {

    public static void main(String[] arg) {
        new LwjglApplication(new BlackBoxGame(), new LwjglApplicationConfiguration());
    }
}

/**
 * Demo/prototyping game class. Do not use it like this in your final product.
 * For demo, prototyping and fast plug and play only
 *
 * @author adrian.popa
 */
class BlackBoxGame extends ApplicationAdapter {

    SpriteBatch batch;
    Texture img;

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("BlackBoxGaming.png");
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();
    }

}
