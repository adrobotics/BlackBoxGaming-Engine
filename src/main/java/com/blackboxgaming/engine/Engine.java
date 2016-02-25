package com.blackboxgaming.engine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.blackboxgaming.engine.util.Global;
import java.util.Map;

/**
 *
 * @author Adrian
 */
public class Engine {

    public static Preferences preferences = Gdx.app.getPreferences("SuburBallUserData");
    public static Game game;
    public static Map<String, Screen> screens;
    public static final EntityManager entityManager = new EntityManager();
    public static final SystemManager systemManager = new SystemManager();
    public static final InputManager inputManager = new InputManager();
    public static final GarbageManager garbageManager = new GarbageManager();
    public static final AssetManager assetManager = new AssetManager();

    public static void update(float delta) {
        systemManager.update(delta);
        garbageManager.removeGarbage();
    }

    public static void dispose() {
        System.out.println("Disposing " + Engine.class);
        inputManager.dispose();
        systemManager.dispose();
        entityManager.dispose();
        garbageManager.dispose();
        assetManager.dispose();
        Global.dispose();
    }

}
