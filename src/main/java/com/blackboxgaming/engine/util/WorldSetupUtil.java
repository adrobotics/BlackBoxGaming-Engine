package com.blackboxgaming.engine.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.bullet.collision.Collision;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject.CollisionFlags;
import com.blackboxgaming.engine.components.Model;
import com.blackboxgaming.engine.components.Transform;
import com.blackboxgaming.engine.components.Puppet;
import com.blackboxgaming.engine.components.Speed;
import com.blackboxgaming.engine.components.HUDItem;
import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Child;
import com.blackboxgaming.engine.components.Damage;
import com.blackboxgaming.engine.components.Enemy;
import com.blackboxgaming.engine.components.Health;
import com.blackboxgaming.engine.factories.ModelFactory;
import com.blackboxgaming.engine.global.constants.Constants;
import com.blackboxgaming.engine.components.OrbitCameraFocus;
import com.blackboxgaming.engine.components.Parent;
import com.blackboxgaming.engine.components.Physics;
import com.blackboxgaming.engine.components.Physics2D;
import com.blackboxgaming.engine.components.Shadow;
import com.blackboxgaming.engine.components.Velocity;
import com.blackboxgaming.engine.components.ai.Follow;
import com.blackboxgaming.engine.factories.CollisionShapeFactory;
import com.blackboxgaming.engine.factories.CollisionShapeFactory2D;
import com.blackboxgaming.engine.factories.LayerFactory;
import com.blackboxgaming.engine.factories.WeaponFactory;
import com.blackboxgaming.engine.systems.LayerRendererSystem;
import com.blackboxgaming.engine.systems.PhysicsSystem;
import com.blackboxgaming.engine.systems.TheGrimReaperSystem;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adrian
 */
public class WorldSetupUtil {

    private static Entity player;

    public static Entity addGrid(float size) {
        Entity grid = new Entity();
        grid.add(new Transform(0, 0, 0));
        grid.add(new Model(ModelFactory.getGridModel((int) size)));
        Engine.entityManager.add(grid);
        return grid;
    }

    public static void addGround(float size) {
        Entity grid = new Entity();
        grid.add(new Transform(0, -0.5f, 0));
        grid.add(new Model(Engine.assetManager.getModel("ground/ground"), true));
        grid.add(new Physics(CollisionShapeFactory.getBoxShape(size, 1, size), 0, btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT, Constants.GROUND_FLAG, (short) 0, Collision.DISABLE_DEACTIVATION));
        Engine.entityManager.add(grid);
    }

    public static void addArena(float width, float height) {
        Entity grid = new Entity();
        grid.add(new Transform(0, -0.5f, 0));
        grid.add(new Model(Engine.assetManager.getModel("ground/ground"), true));
        grid.add(new Physics(CollisionShapeFactory.getBoxShape(width, 1, width), 0, btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT, Constants.GROUND_FLAG, (short) 0, Collision.DISABLE_DEACTIVATION));
        Engine.entityManager.add(grid);

        Entity wall;
        // x
        wall = new Entity();
        wall.add(new Transform(width / 2f + height / 2f, height / 2f, 0));
        wall.add(new Model(ModelFactory.getBoxModel(height, height, width), Color.BLUE));
        wall.add(new Physics(CollisionShapeFactory.getBoxShape(height, height, width), 0, btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT, Constants.GROUND_FLAG, (short) 0, Collision.DISABLE_DEACTIVATION));
        Engine.entityManager.add(wall);
        Global.PHYSICS_CONTACT_GROUP_WALL.add(wall.id);
        // -x
        wall = new Entity();
        wall.add(new Transform(-width / 2f - height / 2f, height / 2f, 0));
        wall.add(new Model(ModelFactory.getBoxModel(height, height, width), Color.BLUE));
        wall.add(new Physics(CollisionShapeFactory.getBoxShape(height, height, width), 0, btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT, Constants.GROUND_FLAG, (short) 0, Collision.DISABLE_DEACTIVATION));
        Engine.entityManager.add(wall);
        Global.PHYSICS_CONTACT_GROUP_WALL.add(wall.id);

        // z
        wall = new Entity();
        wall.add(new Transform(0, height / 2f, width / 2f + height / 2f));
        wall.add(new Model(ModelFactory.getBoxModel(width, height, height), Color.BLUE));
        wall.add(new Physics(CollisionShapeFactory.getBoxShape(width, height, height), 0, btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT, Constants.GROUND_FLAG, (short) 0, Collision.DISABLE_DEACTIVATION));
        Engine.entityManager.add(wall);
        Global.PHYSICS_CONTACT_GROUP_WALL.add(wall.id);
        // -z
        wall = new Entity();
        wall.add(new Transform(0, height / 2f, -width / 2f - height / 2f));
        wall.add(new Model(ModelFactory.getBoxModel(width, height, height), Color.BLUE));
        wall.add(new Physics(CollisionShapeFactory.getBoxShape(width, height, height), 0, btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT, Constants.GROUND_FLAG, (short) 0, Collision.DISABLE_DEACTIVATION));
        Engine.entityManager.add(wall);
        Global.PHYSICS_CONTACT_GROUP_WALL.add(wall.id);

    }

    public static void addSpecificObject() {
        Entity entity = new Entity();
        entity.add(new Transform(0, 0.5f, 0));
        entity.add(new Velocity(0f, 0f, 0f));
        entity.add(new Speed(10, 360, 2));
        entity.add(new Model(ModelFactory.getCubeModel(1f), Color.ORANGE));
        entity.add(new Puppet());
        entity.add(new OrbitCameraFocus());
        entity.add(new HUDItem("isFlying", "", "", true));
        entity.add(new Shadow());
//        entity.add(new Physics(CollisionShapeFactory.getCubeShape(1), 25, btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK, Constants.OBJECT_FLAG, Constants.ALL_FLAG, Collision.ACTIVE_TAG));
        Engine.entityManager.add(entity);
        Global.PHYSICS_CONTACT_GROUP_PUPPET.add(entity.id);
    }

    public static void addPuppedMatrix(int n, int spread) {
        float height = 0.5f;
        Entity puppet;

        for (int i = -n; i <= n; i += spread) {
            for (int j = -n; j <= n; j += spread) {
                if (!(i == 0 && j == 0)) {
                    puppet = new Entity();
                    puppet.add(new Transform(i, height, j));
                    puppet.add(new Velocity(0, 0, 0));
//                    puppet.get(Velocity.class).trnFlag = true;
                    puppet.add(new Speed(10, 360));
//                    puppet.add(new Health(100));
                    puppet.add(new Model(ModelFactory.getCubeModel(1f)));
                    puppet.add(new Puppet());
                    puppet.add(new Shadow());
                    puppet.add(new Physics(CollisionShapeFactory.getCubeShape(1), 25, btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK, Constants.OBJECT_FLAG, Constants.ALL_FLAG, Collision.ACTIVE_TAG));
                    Engine.entityManager.add(puppet);
                    Global.PHYSICS_CONTACT_GROUP_PUPPET.add(puppet.id);
                }
            }
        }
    }

    public static void addRandomObstacles(int n, int spread, float minSize, float maxSize, float health) {
        Entity obstacle;
        float size;

        for (int i = 0; i < n; i++) {
            size = MathUtils.random(minSize, maxSize);
            obstacle = new Entity();
            obstacle.add(new Transform(MathUtils.random(-spread, spread), size / 2f, MathUtils.random(-spread, spread)));
            obstacle.add(new Model(Randomizer.getRandomModel(size), Randomizer.getRandomReddishColor()));
            obstacle.add(new Shadow());
            obstacle.add(new Health(health));
            obstacle.add(new Physics(CollisionShapeFactory.getCollisionShape(Randomizer.getMatchingCollisionShapeName(), size), 0, btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT, Constants.GROUND_FLAG, (short) 0, Collision.DISABLE_DEACTIVATION));
            Engine.entityManager.add(obstacle);
            Global.PHYSICS_CONTACT_GROUP_OBSTACLE.add(obstacle.id);
        }
    }

    public static void addRandomPhysicsObject(int n, float spread) {
        if (Engine.systemManager.has(PhysicsSystem.class)) {
            for (int i = 0; i < n; i++) {
                float randomSize = MathUtils.random(0.5f, 2);
                Entity entity = new Entity();
                entity.add(new Transform(MathUtils.random(-spread, spread), 30, MathUtils.random(-spread, spread), MathUtils.random(360), MathUtils.random(360), MathUtils.random(360)));
                entity.add(new Model(Randomizer.getRandomModel(randomSize), Randomizer.getRandomColor()));
                entity.add(new Shadow());
                entity.add(new Health(20));
                entity.add(new Physics(CollisionShapeFactory.getCollisionShape(Randomizer.getMatchingCollisionShapeName(), randomSize), MathUtils.random(1f, 250), btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK, Constants.OBJECT_FLAG, Constants.GROUND_FLAG, Collision.ACTIVE_TAG));
                Engine.entityManager.add(entity);
            }
        }
    }

    public static void addRandomTree() {
        if (Engine.systemManager.has(PhysicsSystem.class)) {
            Entity modelEntity;
            String[] trees = {"bigtooth_aspen", "black_spruce", "river_burch"};
            int randTreeIndex = MathUtils.random(2);
            modelEntity = new Entity();
            modelEntity.add(new Transform(MathUtils.random(-50, 50), 0, MathUtils.random(-50, 50), MathUtils.random(0, 360), 0, 0));
            modelEntity.add(new Model(Engine.assetManager.getTreeModel(trees[randTreeIndex]), true));
            modelEntity.add(new Shadow());
            modelEntity.add(new Physics(CollisionShapeFactory.getTreeCollisionShape(trees[randTreeIndex]), 0, btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT, Constants.GROUND_FLAG, (short) 0, Collision.DISABLE_DEACTIVATION));
            Engine.entityManager.add(modelEntity);
        }
    }

    public static void addCatAndMouse(int cats) {
//        Entity mouse = new Entity();
//        mouse.addComponent(new Position(new Vector3()));
//        mouse.addComponent(new ColorComponent(Color.RED));
//        mouse.addComponent(new MyModel(ModelFactory.getSphereModel(1)));
//        EntityManager.addEntity(mouse);
//        Engine.systemManager.systemList.add(new MousePointerSystem(mouse));
//        
//
//        int dist = 10;
//        for (int i = 0; i < cats; i++) {
//            Entity cat = new Entity();
//            cat.add(new Transform(new Vector3(MathUtils.random(-dist, dist), 3, MathUtils.random(-dist, dist))));
//            cat.add(new Follow(player, 5));
//            cat.add(new Fear(player, 5));
//            cat.add(new GenericSize(0.25f));
//            cat.add(new Speed(0.15f, 0.5f));
//            cat.add(new ColorX(com.badlogic.gdx.graphics.Color.ORANGE));
//            cat.add(new Mass(1));
//            cat.add(new CollisionComponent("box", btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK, Constants.OBJECT_FLAG, Constants.GROUND_FLAG, Collision.ACTIVE_TAG));
//            cat.add(new Model(ModelFactory.getCubeModel(1)));
//            Engine.entityManager.add(cat);
//
//        }
    }

    public static void addObjModel(String modelName) {
        Engine.assetManager.addModel(modelName + ".g3db");
        com.badlogic.gdx.graphics.g3d.Model model = Engine.assetManager.getModel(modelName);
        Entity modelEntity = new Entity();
        modelEntity.add(new Transform());
        modelEntity.add(new Model(model));
        modelEntity.add(new Shadow());
        modelEntity.add(new Physics(CollisionShapeFactory.getTreeCollisionShape("river_burch"), 0, btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT, Constants.GROUND_FLAG, (short) 0, Collision.DISABLE_DEACTIVATION));
        Engine.entityManager.add(modelEntity);
    }

    public static void addRandomTrees(int n) {
        Entity modelEntity;
        String[] trees = {"bigtooth_aspen", "black_spruce", "river_burch"};
        for (int i = 0; i < n; i++) {
            int randTreeIndex = MathUtils.random(2);
            modelEntity = new Entity();
            modelEntity.add(new Transform(MathUtils.random(-50, 50), 0, MathUtils.random(-50, 50), MathUtils.random(0, 360), 0, 0));
            modelEntity.add(new Model(Engine.assetManager.getTreeModel(trees[randTreeIndex]), true));
            modelEntity.add(new Shadow());
            modelEntity.add(new Physics(CollisionShapeFactory.getTreeCollisionShape(trees[randTreeIndex]), 0, btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT, Constants.GROUND_FLAG, (short) 0, Collision.DISABLE_DEACTIVATION));
            Engine.entityManager.add(modelEntity);
        }
    }

    public static void addHUDItems() {
        Entity hudItem;
        hudItem = new Entity();
        hudItem.add(new HUDItem("Camera", "", "", true));
        Engine.entityManager.add(hudItem);
        hudItem = new Entity();
        hudItem.add(new HUDItem("Bullet val", "", "%", true));
        Engine.entityManager.add(hudItem);
        hudItem = new Entity();
        hudItem.add(new HUDItem("Bullet avg", "", "%", true));
        Engine.entityManager.add(hudItem);
        hudItem = new Entity();
        hudItem.add(new HUDItem("Rotation", "", "degrees", true));
        Engine.entityManager.add(hudItem);
        hudItem = new Entity();
        hudItem.add(new HUDItem("Fps", "", "", true));
        Engine.entityManager.add(hudItem);
        hudItem = new Entity();
        hudItem.add(new HUDItem("Delta", "", "ms", true));
        Engine.entityManager.add(hudItem);
        hudItem = new Entity();
        hudItem.add(new HUDItem("Entities", "", "", true));
        Engine.entityManager.add(hudItem);
        hudItem = new Entity();
        hudItem.add(new HUDItem("Frustrum", "", "ojects", true));
        Engine.entityManager.add(hudItem);
        hudItem = new Entity();
        hudItem.add(new HUDItem("Physics", "", "", true));
        Engine.entityManager.add(hudItem);
    }

    public static void addHUDItemsForProfiling() {
        Entity hudItem;
        hudItem = new Entity();
        hudItem.add(new HUDItem("GL-calls", "", "", true));
        Engine.entityManager.add(hudItem);
        hudItem = new Entity();
        hudItem.add(new HUDItem("Draw-calls", "", "", true));
        Engine.entityManager.add(hudItem);
        hudItem = new Entity();
        hudItem.add(new HUDItem("Shader-switches", "", "", true));
        Engine.entityManager.add(hudItem);
        hudItem = new Entity();
        hudItem.add(new HUDItem("Texture-bindings", "", "", true));
        Engine.entityManager.add(hudItem);
        hudItem = new Entity();
        hudItem.add(new HUDItem("Vertices", "", "", true));
        Engine.entityManager.add(hudItem);
    }

    public static void simualteKeyPressForDebug() {
        InputProcessor input = Gdx.input.getInputProcessor();
//        input.keyDown(Keys.F1);
//        input.keyDown(Keys.F4); // rotation arrow
//        input.keyDown(Keys.F5); // shadow
//        input.keyDown(Keys.F6); // physics
//        input.keyDown(Keys.F8);
//        input.keyDown(Keys.C);
        input.keyDown(Keys.V);
        input.keyDown(Keys.V);
    }

    public static void addLayers() {
        Entity scoreEntity = new Entity();
        scoreEntity.add(LayerFactory.createScoreLayer());
        Engine.systemManager.get(LayerRendererSystem.class).add(scoreEntity);

//        Entity fireEntity = new Entity();
//        fireEntity.add(LayerFactory.createFireLayer());
//        Engine.systemManager.get(LayerRendererSystem.class).add(fireEntity);
//        Entity joystickEntity = new Entity();
//        joystickEntity.add(LayerFactory.createJoystickLayer());
//        Engine.systemManager.get(LayerRendererSystem.class).add(joystickEntity);
        Entity controlEntity = new Entity();
        controlEntity.add(LayerFactory.createJoystickControlLayer());
        Engine.systemManager.get(LayerRendererSystem.class).add(controlEntity);
    }
    
    public static void addTopLayer() {
        Entity top = new Entity();
        top.add(LayerFactory.createLevelAndHealthLayer());
        Engine.systemManager.get(LayerRendererSystem.class).add(top);
    }

    public static void addBossEnemies(float area, int n) {
        Entity boss;
        for (int i = 0; i < n; i++) {
            boss = new Entity();
            boss.add(new Transform(MathUtils.random(-area, area), 1.75f, MathUtils.random(-area, area), MathUtils.random(360), 0, 0));
            boss.add(new Model(ModelFactory.getCubeModel(3), Randomizer.getRandomColor()));
            boss.add(new Health(100));
            boss.add(new Enemy());
            boss.add(new Shadow());
            boss.add(new Velocity(2.5f, 0, 0));
//        enemy.add(new Physics(CollisionShapeFactory.getCubeShape(3), 5, btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK, Constants.OBJECT_FLAG, Constants.ALL_FLAG, Collision.ACTIVE_TAG));
            boss.add(new Physics2D(CollisionShapeFactory2D.getBoxShape(1.5f, 1.5f), BodyType.DynamicBody, 5, boss.get(Transform.class).transform, false));

            Parent parent = new Parent(true);
            parent.add(createWeapon(boss, WeaponFactory.WEAPON_PLASMA, new Vector3(1, -1, 1.5f)));
            parent.add(createWeapon(boss, WeaponFactory.WEAPON_PLASMA, new Vector3(1, -1, -1.5f)));

            boss.add(parent);

            Engine.entityManager.add(boss);
        }
    }

    public static void addNeutralEnemies(float area, int n) {
        Entity enemy;
        for (int i = 0; i < n; i++) {
            enemy = new Entity();
            enemy.add(new Transform(MathUtils.random(-area, area), 0.75f, MathUtils.random(-area, area), MathUtils.random(360), 0, 0));
            enemy.add(new Model(ModelFactory.getCubeModel(1), Randomizer.getRandomColor()));
            enemy.add(new Health(10));
            enemy.add(new Enemy());
            enemy.add(new Shadow());
            enemy.add(new Velocity(5, 0, 0));
//            enemy.add(new Physics(CollisionShapeFactory.getCubeShape(1), 1, btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK, Constants.OBJECT_FLAG, Constants.ALL_FLAG, Collision.ACTIVE_TAG));
            enemy.add(new Physics2D(CollisionShapeFactory2D.getBoxShape(0.5f, 0.5f), BodyType.DynamicBody, 1, enemy.get(Transform.class).transform, false));

            Engine.entityManager.add(enemy);
        }
    }

    public static void addFollowingEnemies(float area, int n) {
        Entity enemy;
        for (int i = 0; i < n; i++) {
            enemy = new Entity();
            enemy.add(new Transform(MathUtils.random(-area, area), 0.75f, MathUtils.random(-area, area), MathUtils.random(360), 0, 0));
            enemy.add(new Model(ModelFactory.getCubeModel(1), Color.BLACK));
            enemy.add(new Health(10));
            enemy.add(new Enemy());
            enemy.add(new Shadow());
            enemy.add(new Follow(Global.mainCharacter, 2));
            enemy.add(new Velocity(4f, 0, 0));
//            enemy.add(new Physics(CollisionShapeFactory.getCubeShape(1), 1, btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK, Constants.OBJECT_FLAG, Constants.ALL_FLAG, Collision.ACTIVE_TAG));
            enemy.add(new Physics2D(CollisionShapeFactory2D.getBoxShape(0.5f, 0.5f), BodyType.DynamicBody, 1, enemy.get(Transform.class).transform, false));

            Parent parent = new Parent(true);
            parent.add(createWeapon(enemy, WeaponFactory.WEAPON_MELEE, new Vector3(0, 0, 0.5f)));
            enemy.add(parent);

            Engine.entityManager.add(enemy);
        }
    }

    private static Entity createWeapon(Entity parent, int weaponType, Vector3 relativePositionToParent) {
        Entity entity = new Entity();
        entity.add(new Transform());
        entity.add(new Model(ModelFactory.getBoxModel(1.5f, 0.125f, 0.125f)));
        entity.add(WeaponFactory.getWeapon(weaponType, new Matrix4().setToTranslation(new Vector3(0.75f, 0, 0))));
        entity.add(new Child(parent, relativePositionToParent));
        Engine.entityManager.add(entity);
        return entity;
    }

    public static void addSkyBox() {
        Entity skyBox;
        skyBox = new Entity();
        skyBox.add(new Transform(0, 10, 0));
        skyBox.add(new Model(Engine.assetManager.getModel("sky_box1"), true));
        Engine.entityManager.add(skyBox);
    }

    public static void addRandomTrees(int n, float spread, int size) {
        for (int i = 0; i < n; i++) {
            Entity tree = getTree(MathUtils.random(1, 3), new Vector3(MathUtils.random(-spread, spread), 0, MathUtils.random(-spread, spread)));
            Engine.entityManager.add(tree);
            Global.PHYSICS_CONTACT_GROUP_OBSTACLE.add(tree.id);
        }
    }

    public static void addRandomRocks(int n, float spread, int size) {
        for (int i = 0; i < n; i++) {
            Entity tree = getRock(MathUtils.random(1, 3), new Vector3(MathUtils.random(-spread, spread), 0, MathUtils.random(-spread, spread)));
            Engine.entityManager.add(tree);
            Global.PHYSICS_CONTACT_GROUP_OBSTACLE.add(tree.id);
        }
    }

    public static Entity getTree(int treeNumber, Vector3 position) {
        float[] treeHeight = {1.5f, 2.5f, 3.5f};
        Entity tree;
        tree = new Entity();
        tree.add(new Transform(position));
        tree.add(new Model(Engine.assetManager.getModel("trees/tree" + treeNumber), true));
        tree.add(new Physics2D(CollisionShapeFactory2D.getBoxShape(0.5f, 0.5f), BodyType.StaticBody, 0, tree.get(Transform.class).transform, true));
//        tree.add(new Physics(CollisionShapeFactory.getBoxShape(1, treeHeight[treeNumber - 1], 1, new Vector3(0, treeHeight[treeNumber - 1] / 2f, 0)), 0, btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT, Constants.GROUND_FLAG, (short) 0, Collision.DISABLE_DEACTIVATION));
        return tree;
    }

    public static Entity getRock(int rockNumber, Vector3 position) {
        float[] rockWidth = {1f, 2f, 4f};
        Entity rock;
        rock = new Entity();
        rock.add(new Transform(position));
        rock.add(new Model(Engine.assetManager.getModel("trees/rock" + rockNumber), true));
        if (rockNumber == 1) {
            rock.add(new Physics2D(CollisionShapeFactory2D.getCircleShape(rockWidth[rockNumber - 1] / 2f), BodyType.StaticBody, 0, rock.get(Transform.class).transform, true));
//            rock.add(new Physics(CollisionShapeFactory.getSphereShape(rockWidth[rockNumber - 1], new Vector3(0, 0.25f, 0)), 0, btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT, Constants.GROUND_FLAG, (short) 0, Collision.DISABLE_DEACTIVATION));
        } else if (rockNumber == 2) {
            rock.add(new Physics2D(CollisionShapeFactory2D.getCircleShape(rockWidth[rockNumber - 1] / 2f), BodyType.StaticBody, 0, rock.get(Transform.class).transform, true));
//            rock.add(new Physics(CollisionShapeFactory.getSphereShape(rockWidth[rockNumber - 1]), 0, btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT, Constants.GROUND_FLAG, (short) 0, Collision.DISABLE_DEACTIVATION));
        } else if (rockNumber == 3) {
            rock.add(new Physics2D(CollisionShapeFactory2D.getBoxShape(rockWidth[rockNumber - 1] / 2f, rockWidth[rockNumber - 1] / 4f), BodyType.StaticBody, 0, rock.get(Transform.class).transform, true));
//            rock.add(new Physics(CollisionShapeFactory.getBoxShape(rockWidth[rockNumber - 1], rockWidth[rockNumber - 1] / 2f, rockWidth[rockNumber - 1] / 2f, new Vector3(0, rockWidth[rockNumber - 1] / 4f, 0)), 0, btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT, Constants.GROUND_FLAG, (short) 0, Collision.DISABLE_DEACTIVATION));
        }
        return rock;
    }

    public static void createBall(float x, float y, float z) {
        createBall(new Vector3(x, y, z));
    }

    private static com.badlogic.gdx.graphics.g3d.Model ballModel;

    public static Entity createBall(Vector3 position) {
        if (ballModel == null) {
            ballModel = ModelFactory.getSphereModel(Global.tennisBallDiameter, 10);
        }
        Entity ball = new Entity();
        ball.add(new Transform(position.x, position.y, position.z));
        ball.add(new Model(ballModel, Randomizer.getRandomColor(), true));
        ball.add(new Damage(1));
        ball.add(new Shadow());
//        ball.add(createBallPhysics());

        Engine.entityManager.add(ball);
        if (Engine.systemManager.has(TheGrimReaperSystem.class)) {
            Engine.systemManager.get(TheGrimReaperSystem.class).add(ball);
        }
        return ball;
    }

    public static Physics createBallPhysics() {
        Physics ballPhysics = new Physics(CollisionShapeFactory.getSphereShape(Global.tennisBallDiameter), 0.594f, CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK, Constants.OBJECT_FLAG, Constants.ALL_FLAG, Collision.DISABLE_DEACTIVATION);
        ballPhysics.body.setRestitution(0.712f);
        ballPhysics.body.setFriction(0.8f);
        ballPhysics.body.setRollingFriction(0.25f);
        ballPhysics.body.setCcdMotionThreshold(0.25f);
        ballPhysics.body.setCcdSweptSphereRadius(0.05f);
        return ballPhysics;
    }

    public static Entity createObstacle() {
        Entity obstacle = new Entity();
        obstacle.add(new Transform(MathUtils.random(-Global.boxLength / 2f, Global.boxLength / 2f), Global.boxDepth / 2f, MathUtils.random(-Global.boxWidth / 2f, Global.boxWidth / 2f)));
        obstacle.add(new Model(ModelFactory.getBoxModel(1f, Global.boxDepth, 1f)));
        obstacle.add(new Health(10));
        obstacle.add(new Shadow());
        obstacle.add(new Physics(CollisionShapeFactory.getBoxShape(1f, Global.boxDepth, 1f), 0, CollisionFlags.CF_STATIC_OBJECT, Constants.OBSTACLE_FLAG, (short) 0, Collision.DISABLE_DEACTIVATION));

        Engine.entityManager.add(obstacle);
        return obstacle;
    }

    public static List<Entity> createWall(float width, float x, float levels, int klevels) {
        List<Entity> bricks = new ArrayList();
        float brickWidth = 1.0f;
        float brickHeight = 0.5f;
        float brickDepth = 0.5f;
        for (int k = 0; k < klevels; k++) {
            for (int i = 0; i < levels; i++) {
                for (int j = 0; j < width / brickWidth; j++) {
                    if (i % 2 == 0) {
                        bricks.add(createBrick(brickWidth, brickHeight, brickDepth, x + brickDepth * k, i * brickHeight + brickHeight / 2f, brickWidth / 4f + -width / 2f + j * brickWidth));
                    } else {
                        if (j == 0) {
                            continue;
                        }
                        bricks.add(createBrick(brickWidth, brickHeight, brickDepth, x + brickDepth * k, i * brickHeight + brickHeight / 2f, -brickWidth / 4f + -width / 2f + j * brickWidth));
                    }
                }
            }
        }

        return bricks;
    }

    public static Entity createBrick(com.badlogic.gdx.graphics.g3d.Model model, float width, float height, float depth, float x, float y, float z) {
//        float brickWeight = 18.14f; // real-life
        float brickWeight = 5;
        int active = Collision.ACTIVE_TAG;
//        int active = Collision.DISABLE_DEACTIVATION;
        Entity brick = new Entity();
        brick.add(new Transform(x, y, z));
        brick.add(new Model(model, Randomizer.getRandomColor(), true));
        brick.add(new Health(2));
//        brick.add(new Shadow());
        brick.add(new Physics(CollisionShapeFactory.getBoxShape(depth, height, width), brickWeight, CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK, Constants.OBSTACLE_FLAG, (short) 0, active));
        brick.get(Physics.class).body.setRestitution(0.55f);
//        brick.get(Physics.class).body.setRollingFriction(0.8f);
//        brick.get(Physics.class).body.setFriction(0.8f);
        Engine.entityManager.add(brick);

        return brick;
    }

    private static com.badlogic.gdx.graphics.g3d.Model brickModel;

    public static Entity createBrick(float width, float height, float depth, float x, float y, float z) {
        if (brickModel == null) {
            brickModel = ModelFactory.getBoxModel(depth, height, width);
        }
//        float brickWeight = 18.14f; // real-life
        float brickWeight = 5;
        int active = Collision.ACTIVE_TAG;
//        int active = Collision.DISABLE_DEACTIVATION;
        Entity brick = new Entity();
        brick.add(new Transform(x, y, z));
        brick.add(new Model(brickModel, Randomizer.getRandomColor(), true));
        brick.add(new Health(3));
//        brick.add(new Shadow());
        brick.add(new Physics(CollisionShapeFactory.getBoxShape(depth, height, width), brickWeight, CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK, Constants.OBSTACLE_FLAG, (short) 0, active));
        brick.get(Physics.class).body.setRestitution(0.55f);
//        brick.get(Physics.class).body.setRollingFriction(0.8f);
//        brick.get(Physics.class).body.setFriction(0.8f);
        Engine.entityManager.add(brick);

        return brick;
    }

    private static com.badlogic.gdx.graphics.g3d.Model brickModelWide;
    private static com.badlogic.gdx.graphics.g3d.Model brickModelLong;

    public static List<Entity> createWallAroundPoint(float wide, int levels, float x, float y, float z) {
        List<Entity> bricks = new ArrayList();
        float brickWidth = 1.0f;
        float brickHeight = 0.5f;
        float brickDepth = 0.5f;

        if (brickModelWide == null) {
            brickModelWide = ModelFactory.getBoxModel(brickDepth, brickHeight, brickWidth);
        }
        if (brickModelLong == null) {
            brickModelLong = ModelFactory.getBoxModel(brickWidth, brickHeight, brickDepth);
        }

        float offest = -(((wide * brickWidth + brickDepth) / 2f) - brickDepth / 2f);
        System.out.println(offest);

        for (int i = 0; i < levels; i++) {
            for (float j = 0; j < wide; j++) {
                if (i % 2 == 0) {
                    bricks.add(createBrick(brickModelWide, brickWidth, brickHeight, brickDepth, x + offest, y + brickHeight / 2f + brickHeight * i, z + offest + brickWidth / 2f - brickDepth / 2f + brickWidth * j));
                    bricks.add(createBrick(brickModelWide, brickWidth, brickHeight, brickDepth, x + -offest, y + brickHeight / 2f + brickHeight * i, z + offest + brickWidth / 2f + brickDepth / 2f + brickWidth * j));

                    bricks.add(createBrick(brickModelLong, brickDepth, brickHeight, brickWidth, x + offest + brickWidth / 2f + brickDepth / 2f + brickWidth * j, y + brickHeight / 2f + brickHeight * i, z + offest));
                    bricks.add(createBrick(brickModelLong, brickDepth, brickHeight, brickWidth, x + offest + brickWidth / 2f - brickDepth / 2f + brickWidth * j, y + brickHeight / 2f + brickHeight * i, z + -offest));
                }else{
                    bricks.add(createBrick(brickModelWide, brickWidth, brickHeight, brickDepth, x + offest, y + brickHeight / 2f + brickHeight * i, z + offest + brickWidth / 2f - brickDepth / 2f + brickWidth * j + brickDepth));
                    bricks.add(createBrick(brickModelWide, brickWidth, brickHeight, brickDepth, x + -offest, y + brickHeight / 2f + brickHeight * i, z + offest + brickWidth / 2f + brickDepth / 2f + brickWidth * j - brickDepth));

                    bricks.add(createBrick(brickModelLong, brickDepth, brickHeight, brickWidth, x + offest + brickWidth / 2f + brickDepth / 2f + brickWidth * j - brickDepth, y + brickHeight / 2f + brickHeight * i, z + offest));
                    bricks.add(createBrick(brickModelLong, brickDepth, brickHeight, brickWidth, x + offest + brickWidth / 2f - brickDepth / 2f + brickWidth * j + brickDepth, y + brickHeight / 2f + brickHeight * i, z + -offest));
                }
            }
        }
        return bricks;
    }

}
