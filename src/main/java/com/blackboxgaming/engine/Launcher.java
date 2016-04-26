package com.blackboxgaming.engine;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.lwjgl.util.Dimension;

/**
 * Launches demo {@link BlackBoxGame} as a {@link LwjglApplication}. This class
 * is mentioned in build.gradle as project.ext.mainClassName, thus the demo can
 * be launched directly.
 *
 * @author adrian.popa
 * @see LwjglApplication
 */
class Launcher {

    private static Dimension fhd = new Dimension(1920, 1080);
    private static Dimension hd = new Dimension(1366, 768);
    private static Dimension wxga = new Dimension(1280, 720);
    private static Dimension vga = new Dimension(640, 480);

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "BlackBoxGaming-Engine";
        config.foregroundFPS = 60;
        config.backgroundFPS = -1;
        setWindowSize(config, hd);
        config.vSyncEnabled = false;
//        config.x = -1;
//        config.y = 10;
        LwjglApplication app = new LwjglApplication(new BlackBoxGame(), config);
    }
    
    private static void setWindowSize(LwjglApplicationConfiguration config, Dimension dimension){
        config.width = dimension.getWidth();
        config.height = dimension.getHeight();
    }
}
