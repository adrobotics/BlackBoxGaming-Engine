package com.blackboxgaming.engine.util;

import com.badlogic.gdx.Gdx;
import com.blackboxgaming.engine.systems.render.HUDRendererSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.bullet.collision.Collision;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.utils.Align;
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
import com.blackboxgaming.engine.systems.render.MinimapRendererSystem;
import com.blackboxgaming.engine.systems.render.ModelRendererSystem;
import com.blackboxgaming.engine.systems.render.TextRendererSystem;
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
        MinimapRendererSystem.focusOn(player);
    }

    public static void addCameraFocus() {
        Entity puppet = new Entity();
        puppet.add(new Transform(0, 0.5f, 0));
        puppet.add(new OrbitCameraFocus());
        puppet.add(new Puppet());
        puppet.add(new Name("Focus"));
        puppet.add(new Velocity());
        puppet.add(new Speed(10.0f, 5 * 36));
        Engine.entityManager.add(puppet);
        MinimapRendererSystem.focusOn(puppet);
    }

    public static void addGrid() {
        if (!Engine.systemManager.has(PhysicsSystem.class)) {
            Engine.systemManager.addAfter(new PhysicsSystem(), VelocitySystem.class);
        }
        Engine.systemManager.addAfter(new AbyssSystem(), PhysicsSystem.class);
        Entity grid = new Entity();
        grid.add(new Transform(0, 0, 0));
        grid.add(new Model(ModelFactory.getGridModel((int) 50)));
        grid.add(new Physics(CollisionShapeFactory.getBoxShape(50, 1, 50, new Vector3(0, -0.5f, 0)), 0, btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT, Constants.GROUND_FLAG, (short) 0, Collision.DISABLE_DEACTIVATION));
        grid.add(new MinimapModel(Color.LIGHT_GRAY));
        Engine.entityManager.add(grid);
    }

    public static void addObstacle(Vector3 position, Color color) {
        if (!Engine.systemManager.has(PhysicsSystem.class)) {
            Engine.systemManager.addAfter(new PhysicsSystem(), VelocitySystem.class);
        }
        if (!Engine.systemManager.has(PhysicsSystem2D.class)) {
            Engine.systemManager.addAfter(new PhysicsSystem2D(), PhysicsSystem.class);
        }
        Entity obstacle = new Entity();
        obstacle.add(new Transform(position));
        obstacle.add(new Model(ModelFactory.getCubeModel(1), color));
        obstacle.add(new Name("Obstacle"));
        obstacle.add(new Health(100));
        obstacle.add(new Physics(CollisionShapeFactory.getCubeShape(1), 25, btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK, Constants.OBJECT_FLAG, Constants.ALL_FLAG, Collision.ACTIVE_TAG));
        obstacle.add(new Physics2D(CollisionShapeFactory2D.getBoxShape(0.5f, 0.5f), BodyDef.BodyType.DynamicBody, 1, obstacle.get(Transform.class).transform, false));
        Engine.entityManager.add(obstacle);
    }

    public static void addWall(Vector3 vector3, int width, int height) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                addObstacle(vector3.cpy().add(0, i, -width + j), Color.BROWN);
            }
        }
    }

    public static void addKeyText() {
        if (!Engine.systemManager.has(TextRendererSystem.class)) {
            Engine.systemManager.addAfter(new TextRendererSystem(), ModelRendererSystem.class);
        }
        Entity text = new Entity();
        text.add(new TextMessage("Keys\n"
                + "[W, A, S, D] Move\n"
                + "[Q, E] Rotate\n"
                + "[Space] Shoot\n"
                + "[Shift] Sprint\n"
                + "[Mouse 2, 3] Rotate view\n"
                + "[Scroll] Zoom\n"
                + "[Esc] Exit",
                10, 180, Color.GREEN, Align.left));
        Engine.entityManager.add(text);
    }

    public static void addDemoText() {
        if (!Engine.systemManager.has(TextRendererSystem.class)) {
            Engine.systemManager.addAfter(new TextRendererSystem(), ModelRendererSystem.class);
        }
        Entity text = new Entity();
        text.add(new Name("Demo help message"));
        text.add(new TextMessage("Demo keys - use in this order\n"
                + "Ex: 1 2 1 1 3 4 5 6 7 8\n"
                + "[Mouse 2] Rotate view\n"
                + "\n"
                + "Conway's Game of Life\n"
                + "[1] Create/Remove cells\n"
                + "[2] Start/Stop game of life\n"
                + "[G] Add glider (remove other cells first)\n"
                + "       also don't forget to start with [2]\n"
                + "[1], [2], [G] can be pressed several times\n"
                + "\n"
                + "\n"
                + "To continue make sure that there are cells on the screen\n"
                + "If not, press [1] once or twice\n"
                + "[3] Add physics to world\n"
                + "[4] Add physics to cells\n"
                + "[5] Make cells follow each other\n"
                + "[6] Make cells fight each other\n"
                + "\n"
                + "[7] Create player\n"
                + "[8] Add enemies\n"
                + "      can be pressed several times\n"
                + "Enemies shoot whenever you shoot\n"
                + "Enemies rotate when hit, they can hit each other aswell\n"
                + "\n"
                + "\n"
                + "Press [H] to show/hide this message",
                10, Gdx.graphics.getHeight() - 440, Color.WHITE, Align.left));
        Engine.entityManager.add(text);
        Global.demoHelpMessage = true;
    }

}
