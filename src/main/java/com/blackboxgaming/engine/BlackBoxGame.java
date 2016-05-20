package com.blackboxgaming.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.blackboxgaming.engine.components.*;
import com.blackboxgaming.engine.input.*;
import com.blackboxgaming.engine.systems.*;
import com.blackboxgaming.engine.systems.render.*;
import com.blackboxgaming.engine.util.*;

/**
 * For demo, prototyping and fast plug and play only
 *
 * @author adrian.popa
 */
public class BlackBoxGame extends ApplicationAdapter {

    @Override
    public void create() {
        // assets
//        Engine.assetManager.init();

        // inputs
        Engine.inputManager.add(new DebugKeyListener());
        Engine.inputManager.add(new DemoKeyListener());
        Engine.inputManager.add(new PlayerKeyListener());

        // systems
        Engine.systemManager.add(new PuppetMoverSystem());
        Engine.systemManager.add(new VelocitySystem());
        Engine.systemManager.add(new OrbitCameraSystem());

        // render
        Engine.systemManager.add(new ModelRendererSystem());
        Engine.systemManager.addAfter(new MinimapRendererSystem(), ModelRendererSystem.class);

        // world setup
        WorldUtil.addHUDItems();
        boolean demo = false;
        if (demo) {
            WorldUtil.addCameraFocus();
        } else {
            WorldUtil.addGrid();
            WorldUtil.addPlayer();
            WorldUtil.addObstacle(new Vector3(5, 0.5f, 0), Color.BLUE);
            WorldUtil.addObstacle(new Vector3(0, 0.5f, 5), Color.RED);
            WorldUtil.addWall(new Vector3(10, 0.5f, 5), 5, 5);
        }
        Global.setCamera(-15f, 15f, 15f);
        Global.loaded = true;

        System.out.println("Created " + this.getClass());
    }

    @Override
    public void render() {
        Engine.update(Global.getDeltaInSeconds());
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        Engine.dispose();
        System.out.println("Done");
    }

}
