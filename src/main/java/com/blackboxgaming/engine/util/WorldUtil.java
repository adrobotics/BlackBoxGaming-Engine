package com.blackboxgaming.engine.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.bullet.collision.Collision;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.*;
import com.blackboxgaming.engine.factories.CollisionShapeFactory;
import com.blackboxgaming.engine.factories.CollisionShapeFactory2D;
import com.blackboxgaming.engine.factories.ModelFactory;
import com.blackboxgaming.engine.global.constants.Constants;
import com.blackboxgaming.engine.systems.HUDSystem;

/**
 *
 * @author Adrian
 */
public class WorldUtil {

    public static void addHUDItems() {
        if (Engine.systemManager.has(HUDSystem.class)) {
            Entity hudItem;
            hudItem = new Entity();
            hudItem.add(new HUDItem("Fps"));
            Engine.entityManager.add(hudItem);
            hudItem = new Entity();
            hudItem.add(new HUDItem("Delta", "ms"));
            Engine.entityManager.add(hudItem);
            hudItem = new Entity();
            hudItem.add(new HUDItem("Entities"));
            Engine.entityManager.add(hudItem);
            hudItem = new Entity();
            hudItem.add(new HUDItem("Visible"));
            Engine.entityManager.add(hudItem);
            hudItem = new Entity();
            hudItem.add(new HUDItem("Physics"));
            Engine.entityManager.add(hudItem);
            hudItem = new Entity();
            hudItem.add(new HUDItem("Camera"));
            Engine.entityManager.add(hudItem);
        }
    }

    public static void addPlayer() {
        Entity player = new Entity();
        player.add(new Transform(0, 0.5f, 0));
        player.add(new Model(ModelFactory.getCubeModel(1), Color.GREEN));
        player.add(new OrbitCameraFocus());
        player.add(new Puppet());
        player.add(new Name("Player"));
        player.add(new Physics(CollisionShapeFactory.getCubeShape(1), 25, btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK, Constants.OBJECT_FLAG, Constants.ALL_FLAG, Collision.ACTIVE_TAG));
        player.add(new Physics2D(CollisionShapeFactory2D.getBoxShape(0.5f, 0.5f), BodyDef.BodyType.DynamicBody, 1, player.get(Transform.class).transform, false));
        player.add(new Velocity());
        player.add(new Health(100));
        player.add(new Speed(10.0f, 5 * 36));
        Engine.entityManager.add(player);
    }

    public static void addPuppet() {
        Entity player = new Entity();
        player.add(new Transform(0, 0.5f, 0));
        player.add(new Model(ModelFactory.getCubeModel(1), Color.GREEN));
        player.add(new OrbitCameraFocus());
        player.add(new Puppet());
        player.add(new Name("Puppet"));
        player.add(new Velocity());
        player.add(new Speed(10.0f, 5 * 36));
        Engine.entityManager.add(player);
    }

}
