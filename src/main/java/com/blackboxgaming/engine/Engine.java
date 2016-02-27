package com.blackboxgaming.engine;

import com.blackboxgaming.engine.managers.SystemManager;
import com.blackboxgaming.engine.managers.InputManager;
import com.blackboxgaming.engine.managers.GarbageManager;
import com.blackboxgaming.engine.managers.EntityManager;
import com.blackboxgaming.engine.managers.AssetManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.blackboxgaming.engine.util.Global;
import java.util.Map;

/**
 * Main engine class that contains all managers, screens and preferences 
 *
 * @author Adrian
 * @see EntityManager
 * @see SystemManager
 * @see InputManager
 * @see GarbageManager
 * @see AssetManager
 * @see Screen
 * @see Preferences
 */
public class Engine {

    public static Game game;
    public static Map<String, Screen> screens;
    public static final EntityManager entityManager = new EntityManager();
    public static final SystemManager systemManager = new SystemManager();
    public static final InputManager inputManager = new InputManager();
    public static final GarbageManager garbageManager = new GarbageManager();
    public static final AssetManager assetManager = new AssetManager();
    public static final Preferences preferences = Gdx.app.getPreferences("BlackBoxGaming-Preferences");

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
