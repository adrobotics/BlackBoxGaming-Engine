package com.blackboxgaming.engine.util;

import com.blackboxgaming.engine.systems.render.HUDRendererSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.bullet.collision.Collision;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.*;
import com.blackboxgaming.engine.factories.CollisionShapeFactory;
import com.blackboxgaming.engine.factories.CollisionShapeFactory2D;
import com.blackboxgaming.engine.factories.ModelFactory;
import com.blackboxgaming.engine.factories.WeaponFactory;
import com.blackboxgaming.engine.global.constants.Constants;
import com.blackboxgaming.engine.systems.*;
import com.blackboxgaming.engine.systems.render.HealthBarRendererSystem;
import com.blackboxgaming.engine.systems.render.ModelRendererSystem;
import static com.blackboxgaming.engine.util.OldButNotThatOldWorldSetup.createWeapon;

/**
 *
 * @author Adrian
 */
public class WorldUtil {

    public static void addHUDItems() {
        if (!Engine.systemManager.has(HUDRendererSystem.class)) {
            Engine.systemManager.addAfter(new HUDRendererSystem(), ModelRendererSystem.class);
        }
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
        hudItem.add(new HUDItem("Systems"));
        Engine.entityManager.add(hudItem);
        hudItem = new Entity();
        hudItem.add(new HUDItem("Camera"));
        Engine.entityManager.add(hudItem);
    }

    public static void addPlayer() {
        if (!Engine.systemManager.has(PhysicsSystem.class)) {
            Engine.systemManager.addAfter(new PhysicsSystem(), VelocitySystem.class);
        }
        if (!Engine.systemManager.has(PhysicsSystem2D.class)) {
            Engine.systemManager.addAfter(new PhysicsSystem2D(), PhysicsSystem.class);
        }
        Engine.systemManager.addAfter(new ParentChildSystem(), VelocitySystem.class);
        Engine.systemManager.add(new WeaponSystem());
        Engine.systemManager.add(new TimedDeathSystem());
        Engine.systemManager.add(new HealthSystem());
        Engine.systemManager.addAfter(new HealthBarRendererSystem(), ModelRendererSystem.class);

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
        Parent parent = new Parent(true);
        parent.add(createWeapon(player, WeaponFactory.WEAPON_PLASMA, new Vector3(0, 0, 0.5f)));
        parent.add(createWeapon(player, WeaponFactory.WEAPON_PLASMA, new Vector3(0, 0, -0.5f)));
        player.add(parent);
        Engine.entityManager.add(player);
    }

    public static void addPuppet() {
        Entity puppet = new Entity();
        puppet.add(new Transform(0, 0.5f, 0));
        puppet.add(new Model(ModelFactory.getCubeModel(1), Color.GREEN));
        puppet.add(new OrbitCameraFocus());
        puppet.add(new Puppet());
        puppet.add(new Name("Puppet"));
        puppet.add(new Velocity());
        puppet.add(new Speed(10.0f, 5 * 36));
        Engine.entityManager.add(puppet);
    }

    public static void addPhysics() {
        if (!Engine.systemManager.has(PhysicsSystem.class)) {
            Engine.systemManager.addAfter(new PhysicsSystem(), VelocitySystem.class);
        }
        Engine.systemManager.addAfter(new AbyssSystem(), PhysicsSystem.class);
        Entity grid = new Entity();
        grid.add(new Transform(0, 0, 0));
        grid.add(new Model(ModelFactory.getGridModel((int) 50)));
        grid.add(new Physics(CollisionShapeFactory.getBoxShape(50, 1, 50, new Vector3(0, -0.5f, 0)), 0, btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT, Constants.GROUND_FLAG, (short) 0, Collision.DISABLE_DEACTIVATION));
        Engine.entityManager.add(grid);
    }

    public static void addObstacle(Vector3 position) {
        if (!Engine.systemManager.has(PhysicsSystem.class)) {
            Engine.systemManager.addAfter(new PhysicsSystem(), VelocitySystem.class);
        }
        if (!Engine.systemManager.has(PhysicsSystem2D.class)) {
            Engine.systemManager.addAfter(new PhysicsSystem2D(), PhysicsSystem.class);
        }
        Entity obstacle = new Entity();
        obstacle.add(new Transform(position));
        obstacle.add(new Model(ModelFactory.getCubeModel(1), Color.RED));
        obstacle.add(new Name("2D Obstacle"));
        obstacle.add(new Physics(CollisionShapeFactory.getCubeShape(1), 25, btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK, Constants.OBJECT_FLAG, Constants.ALL_FLAG, Collision.ACTIVE_TAG));
        obstacle.add(new Physics2D(CollisionShapeFactory2D.getBoxShape(0.5f, 0.5f), BodyDef.BodyType.DynamicBody, 1, obstacle.get(Transform.class).transform, false));
        Engine.entityManager.add(obstacle);
    }

}
