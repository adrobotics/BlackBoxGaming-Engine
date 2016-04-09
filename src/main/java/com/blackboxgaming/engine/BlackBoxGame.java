package com.blackboxgaming.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.bullet.collision.Collision;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.blackboxgaming.engine.components.*;
import com.blackboxgaming.engine.components.ai.Follow;
import com.blackboxgaming.engine.factories.CollisionShapeFactory;
import com.blackboxgaming.engine.factories.CollisionShapeFactory2D;
import com.blackboxgaming.engine.factories.ModelFactory;
import com.blackboxgaming.engine.factories.WeaponFactory;
import com.blackboxgaming.engine.global.constants.Constants;
import com.blackboxgaming.engine.input.*;
import com.blackboxgaming.engine.systems.*;
import com.blackboxgaming.engine.systems.ai.FollowSystem;
import com.blackboxgaming.engine.util.Global;
import com.blackboxgaming.engine.util.WorldSetupUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
        Engine.inputManager.add(new DebugKeyListener());
        Engine.inputManager.add(new DemoKeyListener());
        Engine.inputManager.add(new PlayerKeyListener());
//        Engine.inputManager.add(new BallFlingDetector());

//        Engine.systemManager.add(new ConwaySystem(ConwaySystem.life4555, 500));
        Engine.systemManager.add(new OrbitCameraSystem(true));
//        Engine.systemManager.add(new TheGrimReaperSystem(5));
        Engine.systemManager.add(new PuppetMoverSystem());
        Engine.systemManager.add(new TimedDeathSystem());
        Engine.systemManager.add(new VelocitySystem());
//        Engine.systemManager.add(new PhysicsSystem());
        Engine.systemManager.add(new HealthSystem());
        // render
//        Engine.systemManager.add(new AnimationSystem());
        Engine.systemManager.add(new ModelRendererSystem());
        Engine.systemManager.add(new HealthBarRendererSystem());
//        Engine.systemManager.add(new LayerRendererSystem());
//        Engine.systemManager.add(new UpdaterSystem());
        Engine.systemManager.add(new HUDSystem());

        if (Engine.systemManager.has(HUDSystem.class)) {
            Entity hudItem = new Entity();
            hudItem.add(new HUDItem("FPS", "", "", true));
            Engine.entityManager.add(hudItem);
            hudItem = new Entity();
            hudItem.add(new HUDItem("Delta", "", "ms", true));
            Engine.entityManager.add(hudItem);
            hudItem = new Entity();
            hudItem.add(new HUDItem("Entities", "", "", true));
            Engine.entityManager.add(hudItem);
            hudItem = new Entity();
            hudItem.add(new HUDItem("Conway", "", "", true));
            Engine.entityManager.add(hudItem);
        }

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
//        Global.setCamera(-5f, 5f, 0f);
        Global.setCamera(-15f, 15f, 15f);

        // x
//        Entity x = new Entity();
//        x.add(new Transform(10, 0.5f, 0));
//        x.add(new Model(ModelFactory.getCubeModel(1), Color.RED));
//        Engine.entityManager.add(x);

        // puppet
//        Engine.systemManager.add(new PhysicsSystem());
//        Engine.systemManager.add(new PhysicsSystem2D());
//        Engine.systemManager.add(new ParentChildSystem());
//        Engine.systemManager.add(new WeaponSystem());
        Entity puppet = new Entity();
        puppet.add(new Transform(0, 0.5f, 0));
        puppet.add(new Model(ModelFactory.getCubeModel(1), Color.GREEN));
        puppet.add(new OrbitCameraFocus());
        puppet.add(new Puppet());
//        puppet.add(new Physics(CollisionShapeFactory.getCubeShape(1), 25, btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK, Constants.OBJECT_FLAG, Constants.ALL_FLAG, Collision.ACTIVE_TAG));
//        puppet.add(new Physics2D(CollisionShapeFactory2D.getBoxShape(0.5f, 0.5f), BodyDef.BodyType.DynamicBody, 1, puppet.get(Transform.class).transform, false));
        puppet.add(new Velocity());
//        puppet.add(new Health(100));
        puppet.add(new Speed(10.0f, 5 * 36));
        Engine.entityManager.add(puppet);

        // follow
//        Engine.systemManager.add(new FollowSystem());
//        Entity e = new Entity();
//        e.add(new Transform(5, 0.5f, 0));
//        e.add(new Velocity(1, 0, 0));
//        e.add(new Physics(CollisionShapeFactory.getCubeShape(1), 25, btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK, Constants.OBJECT_FLAG, Constants.ALL_FLAG, Collision.ACTIVE_TAG));
//        e.add(new Physics2D(CollisionShapeFactory2D.getBoxShape(0.5f, 0.5f), BodyDef.BodyType.DynamicBody, 1, e.get(Transform.class).transform, false));
//        e.add(new Follow(puppet, 2));
//        e.add(new Model(ModelFactory.getCubeModel(1), Color.BLUE));
//        Parent parent = new Parent(true);
//        parent.add(WorldSetupUtil.createWeapon(e, WeaponFactory.WEAPON_MELEE, new Vector3(0, 0, 0.5f)));
//        e.add(parent);
//        Engine.entityManager.add(e);
//
//        Gdx.input.getInputProcessor().keyDown(Keys.F1);
        // grid
//        e = new Entity();
//        e.add(new Transform());
//        e.add(new Model(ModelFactory.getGridModel(dist * 2)));
//        Engine.entityManager.add(e);

//        float knightSize = 2;
//        Entity knight = new Entity();
//        com.badlogic.gdx.graphics.g3d.Model knightModel = Engine.assetManager.getModel("knight/repo/knight.g3db");
//        ModelInstance knightModelInstance = new ModelInstance(knightModel);
//        AnimationController controller = new AnimationController(knightModelInstance);
//        // rotate model !!!
//        knight.add(new Model(knightModelInstance, knightSize));
//        knight.add(new Animation(controller));
//        knight.add(new Transform(0, knightSize / 2f, 0, 0, 0, 0));
//        knight.add(new Puppet());
//        knight.add(new Velocity());
//        knight.add(new Speed(2.0f, 5 * 36));
//        knight.add(new OrbitCameraFocus());
        //Engine.entityManager.add(knight);
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
