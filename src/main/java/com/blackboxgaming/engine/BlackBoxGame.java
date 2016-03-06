package com.blackboxgaming.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.PerformanceCounter;
import com.blackboxgaming.engine.collision.contactlistener.BallContactListener;
import com.blackboxgaming.engine.components.*;
import com.blackboxgaming.engine.factories.ModelFactory;
import com.blackboxgaming.engine.input.*;
import com.blackboxgaming.engine.systems.*;
import com.blackboxgaming.engine.util.Global;
import com.blackboxgaming.engine.util.WorldSetupUtil;

/**
 * Demo/prototyping game class. Do not use it like this in your final product. For demo, prototyping and fast plug and
 * play only
 *
 * @author adrian.popa
 */
public class BlackBoxGame extends ApplicationAdapter {

    @Override
    public void create() {
        // assets
//        Engine.assetManager.init();

        // input
//        Engine.inputManager.add(new DebugKeyListener());
        Engine.inputManager.add(new PlayerKeyListener());
//        Engine.inputManager.add(new BallFlingDetector());
        Engine.systemManager.add(new OrbitCameraSystem(true));
//        Engine.systemManager.add(new TheGrimReaperSystem(5));
        Engine.systemManager.add(new PuppetMoverSystem());
        Engine.systemManager.add(new VelocitySystem());
//        Engine.systemManager.add(new PhysicsSystem());
//        Engine.systemManager.add(new HealthSystem());
        // render
        Engine.systemManager.add(new ModelRendererSystem());
//        Engine.systemManager.add(new HealthBarRendererSystem());
//        Engine.systemManager.add(new LayerRendererSystem());
//        Engine.systemManager.add(new HUDSystem());

        // world setup
//        WorldSetup.setCamera(-0.1f, 18.5f, 0f);
//        WorldSetupUtil.addSkyBox();
//        WorldSetup.createXZPlane(0);
//        WorldSetup.createBox(Global.boxWidth, Global.boxLength, Global.boxDepth);
//        WorldSetup.createRamp();
        // wall center point
//        Entity wallCenter = new Entity();
//        wallCenter.add(new Transform(Global.boxLength / 4f, 0, 0));
//        wallCenter.add(new Model(ModelFactory.getSphereModel(0.5f)));
//        Engine.entityManager.add(wallCenter);
//        Entity focus = new Entity();
//        focus.add(new Transform(0, 0, 0));
//        focus.add(new Velocity());
////        focus.add(new Model(ModelFactory.getSphereModel(0.1f)));
//        focus.add(new Speed(10));
//        focus.add(new Puppet());
//        focus.add(new OrbitCameraFocus());
//        Engine.entityManager.add(focus);
//
//        WorldSetupUtil.simualteKeyPressForDebug();
//        Global.performanceCounter = new PerformanceCounter("PerformanceCounter");
//        PhysicsSystem.contactListener = new BallContactListener();
        Global.setCamera(-0.1f, 15f, 0f);

        // center
        int dist = 5;
        Entity e = new Entity();
        e.add(new Transform());
        e.add(new Model(ModelFactory.getCubeModel(1), Color.GOLD));
        Engine.entityManager.add(e);
        // x+
        e = new Entity();
        e.add(new Transform(dist, 0, 0));
        e.add(new Model(ModelFactory.getCubeModel(1), Color.RED));
        Engine.entityManager.add(e);
        // y+
        e = new Entity();
        e.add(new Transform(0, dist, 0));
        e.add(new Model(ModelFactory.getCubeModel(1), Color.GREEN));
        Engine.entityManager.add(e);
        // z+
        e = new Entity();
        e.add(new Transform(0, 0, dist));
        e.add(new Model(ModelFactory.getCubeModel(1), Color.BLUE));
        Engine.entityManager.add(e);
        // xyz
        e = new Entity();
        e.add(new Transform());
        e.add(new Model(ModelFactory.getXYZCoordinates(dist)));
        Engine.entityManager.add(e);
        e = new Entity();
        e.add(new Transform());
        e.add(new Model(ModelFactory.getGridModel(dist * 2)));
        Engine.entityManager.add(e);
        
        // 
        e = new Entity();
        e.add(new Transform());
        e.add(new Velocity());
        e.add(new Puppet());
        e.add(new Speed(8, Global.angularSpeed, 2));
        e.add(new Model(ModelFactory.getSphereModel(1)));
        e.add(new OrbitCameraFocus());
        Engine.entityManager.add(e);
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
