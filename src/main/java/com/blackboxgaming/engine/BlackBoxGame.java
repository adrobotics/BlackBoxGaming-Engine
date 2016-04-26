package com.blackboxgaming.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.blackboxgaming.engine.components.*;
import com.blackboxgaming.engine.factories.*;
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
        Engine.systemManager.add(new HUDSystem());

        // world setup
        WorldUtil.addHUDItems();
        WorldUtil.addPuppet();
        Global.setCamera(-15f, 15f, 15f);
    }

    @Override
    public void render() {
        Engine.update(Global.getDeltaInSeconds());
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        Engine.dispose();
    }

}
