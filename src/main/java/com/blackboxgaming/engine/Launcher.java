package com.blackboxgaming.engine;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Launches demo {@link BlackBoxGame} as a {@link LwjglApplication}. This class is mentioned in build.gradle as
 * project.ext.mainClassName, thus the demo can be launched directly.
 *
 * @author adrian.popa
 * @see LwjglApplication
 */
class Launcher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "BlackBoxGaming-Engine";
        config.foregroundFPS = 60;
        config.backgroundFPS = -1;
        config.width = 1280;
//        config.width = 640;
        config.height = 800;
//        config.height = 480;
        config.vSyncEnabled = false;
        config.x = -1;
        config.y = 10;
        LwjglApplication app = new LwjglApplication(new BlackBoxGame(), config);
    }
}