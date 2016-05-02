package com.blackboxgaming.engine.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.bullet.collision.Collision;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.systems.conway.ConwayUtil;
import com.blackboxgaming.engine.components.Cell;
import com.blackboxgaming.engine.components.HUDItem;
import com.blackboxgaming.engine.components.Health;
import com.blackboxgaming.engine.components.Model;
import com.blackboxgaming.engine.components.OrbitCameraFocus;
import com.blackboxgaming.engine.components.Parent;
import com.blackboxgaming.engine.components.Physics;
import com.blackboxgaming.engine.components.Physics2D;
import com.blackboxgaming.engine.components.Transform;
import com.blackboxgaming.engine.components.Velocity;
import com.blackboxgaming.engine.components.ai.Follow;
import com.blackboxgaming.engine.factories.CollisionShapeFactory;
import com.blackboxgaming.engine.factories.CollisionShapeFactory2D;
import com.blackboxgaming.engine.factories.ModelFactory;
import com.blackboxgaming.engine.factories.WeaponFactory;
import com.blackboxgaming.engine.global.constants.Constants;
import com.blackboxgaming.engine.systems.AbyssSystem;
import com.blackboxgaming.engine.systems.ConwaySystem;
import com.blackboxgaming.engine.systems.render.HealthBarRendererSystem;
import com.blackboxgaming.engine.systems.HealthSystem;
import com.blackboxgaming.engine.systems.ParentChildSystem;
import com.blackboxgaming.engine.systems.PhysicsSystem;
import com.blackboxgaming.engine.systems.PhysicsSystem2D;
import com.blackboxgaming.engine.systems.TimedDeathSystem;
import com.blackboxgaming.engine.systems.VelocitySystem;
import com.blackboxgaming.engine.systems.WeaponSystem;
import com.blackboxgaming.engine.systems.ai.FollowSystem;
import com.blackboxgaming.engine.util.Randomizer;
import com.blackboxgaming.engine.util.OldButNotThatOldWorldSetup;
import static com.blackboxgaming.engine.util.OldButNotThatOldWorldSetup.createWeapon;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Adrian
 */
public class DemoKeyListener implements InputProcessor {

    @Override
    public boolean keyDown(int key) {
        switch (key) {
            case Keys.NUM_1:
                System.out.println("Adding ConwaySystem");
                if (!Engine.systemManager.has(ConwaySystem.class)) {
                    Engine.systemManager.add(new ConwaySystem(ConwaySystem.life5766, 200));
                    System.out.println("Adding premordial soup");
                    ConwayUtil.createPremordialSoup(20, 4);
                    Entity hudItem = new Entity();
                    hudItem.add(new HUDItem("Generation"));
                    Engine.entityManager.add(hudItem);
                } else {
                    System.out.println("Removing ConwaySystem");
                    Engine.systemManager.get(ConwaySystem.class).clearUniverse();
                    Engine.systemManager.get(ConwaySystem.class).dispose();
                    Engine.systemManager.remove(ConwaySystem.class);
                }
                break;
            case Keys.NUM_2:
                System.out.println("Toggle Conway System");
                if (Engine.systemManager.has(ConwaySystem.class)) {
                    boolean toggle = Engine.systemManager.get(ConwaySystem.class).manualBreak;
                    if (toggle) {
                        toggle = false;
                    } else {
                        toggle = true;
                    }
                    Engine.systemManager.get(ConwaySystem.class).manualBreak = toggle;
                }
                break;
            case Keys.NUM_3:
                System.out.println("Adding physics and abys systems");
                Engine.systemManager.add(new PhysicsSystem());
                Engine.systemManager.add(new PhysicsSystem2D());
                Engine.systemManager.add(new AbyssSystem());

                System.out.println("Adding ground");
                Entity grid = new Entity();
                grid.add(new Transform(0, 0, 0));
                grid.add(new Model(ModelFactory.getGridModel((int) 50)));
                grid.add(new Physics(CollisionShapeFactory.getBoxShape(50, 1, 50, new Vector3(0, -0.5f, 0)), 0, btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT, Constants.GROUND_FLAG, (short) 0, Collision.DISABLE_DEACTIVATION));
                Engine.entityManager.add(grid);
                break;
            case Keys.NUM_4:
                System.out.println("Adding physics to cells");
                if (Engine.systemManager.has(ConwaySystem.class)) {
//                    ISystem physicsSystem = Engine.systemManager.get(PhysicsSystem.class);
                    for (Map.Entry<Cell, Entity> entrySet : Engine.systemManager.get(ConwaySystem.class).universe.entrySet()) {
                        Entity entity = entrySet.getValue();
                        entity.add(new Physics(CollisionShapeFactory.getCubeShape(1), 25, btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK, Constants.OBJECT_FLAG, Constants.ALL_FLAG, Collision.ACTIVE_TAG));
                        entity.add(new Physics2D(CollisionShapeFactory2D.getBoxShape(0.5f, 0.5f), BodyDef.BodyType.DynamicBody, 1, entity.get(Transform.class).transform, false));
//                        physicsSystem.add(entity);
                        Engine.entityManager.update(entity);
                    }
                    System.out.println("Removing conway system");
                    Engine.systemManager.remove(ConwaySystem.class);
                }
                break;
            case Keys.NUM_5:
                System.out.println("Adding follower system");
                Engine.systemManager.add(new FollowSystem());
                List<Entity> entities = new ArrayList();
                for (Entity cell : Engine.entityManager.getEntities()) {
                    if (cell.has(Cell.class)) {
                        entities.add(cell);
                    }
                }
                for (Entity enemy : entities) {
//                    enemy.add(new Enemy());
                    enemy.add(new Follow(entities.get(Randomizer.getRandomInteger(entities.size())), 2));
                    enemy.add(new Velocity(4f, 0, 0));
                    Engine.entityManager.update(enemy);
                }
                break;
            case Keys.NUM_6:
                System.out.println("Giving all cells health and adding weapon system");
                Engine.systemManager.addAfter(new ParentChildSystem(), VelocitySystem.class);
                Engine.systemManager.add(new WeaponSystem());
                Engine.systemManager.add(new TimedDeathSystem());
                Engine.systemManager.add(new HealthSystem());
                Engine.systemManager.add(new HealthBarRendererSystem());

                System.out.println("Making all cells into enemies");
                List<Entity> entities2 = new ArrayList();
                for (Entity cell : Engine.entityManager.getEntities()) {
                    if (cell.has(Cell.class)) {
                        entities2.add(cell);
                    }
                }
                for (Entity enemy : entities2) {
                    enemy.add(new Health(100));

                    Parent parent = new Parent(true);
                    parent.add(OldButNotThatOldWorldSetup.createWeapon(enemy, WeaponFactory.WEAPON_MELEE, new Vector3(0, 0, 0.5f)));
                    enemy.add(parent);

                    Engine.entityManager.update(enemy);
                }
                break;
            case Keys.NUM_7:
                System.out.println("Adding weapon to player");
                for (Entity e : Engine.entityManager.getEntities()) {
                    if (e.has(OrbitCameraFocus.class)) {
                        System.out.println("Adding weapon to player x");
                        e.add(new Physics(CollisionShapeFactory.getCubeShape(1), 25, btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK, Constants.OBJECT_FLAG, Constants.ALL_FLAG, Collision.ACTIVE_TAG));
                        Parent parent = new Parent(true);
                        parent.add(createWeapon(e, WeaponFactory.WEAPON_PLASMA, new Vector3(0, 0, 0.5f)));
                        parent.add(createWeapon(e, WeaponFactory.WEAPON_PLASMA, new Vector3(0, 0, -0.5f)));
                        e.add(parent);
                        Engine.entityManager.update(e);
                    }
                }
                break;
            case Keys.NUM_8:
                System.out.println("Adding boss enemies");
                OldButNotThatOldWorldSetup.addBossEnemies(25, 10);
                break;
            case Keys.NUM_9:
                System.out.println("Adding dragon");
                Engine.assetManager.init();
                Engine.assetManager.loadModel("dragon/dragon.g3db");
                Entity dragon = new Entity();
                dragon.add(new Transform());
                dragon.add(new Model(Engine.assetManager.getModel("dragon/dragon.g3db")));
                Engine.entityManager.add(dragon);
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }

}