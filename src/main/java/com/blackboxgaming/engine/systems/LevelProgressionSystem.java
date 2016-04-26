package com.blackboxgaming.engine.systems;

import com.blackboxgaming.engine.systems.render.LayerRendererSystem;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Layer;
import com.blackboxgaming.engine.factories.BrickLayerFactory;
import com.blackboxgaming.engine.factories.BrickLayerFactory.BrickLayerConfig;
import com.blackboxgaming.engine.factories.BrickLayerFactory.BrickLayerFormation;
import static com.blackboxgaming.engine.factories.BrickLayerFactory.BrickLayerFormation.*;
import com.blackboxgaming.engine.factories.ModelFactory;
import com.blackboxgaming.engine.util.Global;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adrian
 */
public class LevelProgressionSystem implements ISystem, Disposable {

    public final List<Entity> entities = new ArrayList();
    public int level;
    public long levelUpDelay = 250;
    public long levelUpTime;
    private boolean levelUp;
    public int maxObstacles = 150;
    private BrickLayerConfig config;
    private final Vector3 brickDimenstion = new Vector3(1.0f, 0.5f, 0.5f);
    private final Vector3 wallPosition = new Vector3(Global.boxLength / 4f, 0, 0);
    private final Vector3 wallDimenstion = new Vector3();
    private com.badlogic.gdx.graphics.g3d.Model brickModel;
    public int health = 1;
    private float score = 1;
    public int nrOfBricks;

    private int wallWidth = 3;
    private int wallHeight = 8;

    private float restitution = 0.55f;
    private float mass = 5;

    private BrickLayerFormation formation = BrickLayerFormation.LINE;

    public LevelProgressionSystem() {
        this(1);
    }

    public LevelProgressionSystem(int level) {
        for (int i = 0; i < level; i++) {
            setLevelUp();
        }
    }

    @Override
    public void add(Entity entity) {
        if (!entities.contains(entity)) {
            entities.add(entity);
        }
    }

    @Override
    public void remove(Entity entity) {
        entities.remove(entity);
    }

    @Override
    public void update(float delta) {

        if (levelUp && levelUpDelay < System.currentTimeMillis() - levelUpTime) {
            levelUp = false;
            System.out.println("Current level: " + level + "; bricks = " + nrOfBricks);

            wallDimenstion.set(wallWidth, wallHeight, 1);

            if (brickModel == null) {
                brickModel = ModelFactory.getBoxModel(brickDimenstion.x, brickDimenstion.y, brickDimenstion.z);
            }

            entities.clear();
            if (formation == LINE) {
                config = new BrickLayerConfig(brickModel, wallPosition, brickDimenstion, formation, nrOfBricks, health, score, mass, restitution, wallDimenstion);
                entities.addAll(BrickLayerFactory.layBricks(config));
            } else if (formation == CASTLE) {
                config = new BrickLayerConfig(brickModel, wallPosition, brickDimenstion, formation, nrOfBricks, health, score, mass, restitution, wallDimenstion);
                entities.addAll(BrickLayerFactory.layBricks(config));

                if (level >= 273) {
                    // second castle wall
                    Vector3 wallDimenstionCastle = new Vector3(wallDimenstion);
                    wallDimenstionCastle.x += 2;
                    config = new BrickLayerConfig(brickModel, wallPosition, brickDimenstion, formation, nrOfBricks - 16, health, score, mass, restitution, wallDimenstionCastle);
                    entities.addAll(BrickLayerFactory.layBricks(config));
                }
            }

            for (Entity entity : entities) {
                Engine.entityManager.add(entity);
            }
        }

        if (!levelUp && entities.isEmpty()) {
            setLevelUp();
        }
    }

    private void setLevelUp() {
        this.levelUpTime = System.currentTimeMillis();
        this.levelUp = true;
        this.level++;
        nrOfBricks++;

        // remove hand
        if (level >= 5) {
            if (Engine.systemManager.has(LayerRendererSystem.class)) {
                for (Entity entity : Engine.systemManager.get(LayerRendererSystem.class).layers) {
                    if (entity.has(Layer.class)) {
                        if (entity.get(Layer.class).layerName.equalsIgnoreCase("topLayer")) {
                            if (Global.swipeIcon != null) {
                                entity.get(Layer.class).table.removeActor(Global.swipeIcon);
                            }
                        }
                    }
                }
            }
        }

        if (level == 21) {
            formation = BrickLayerFormation.LINE;
            upTheStackes();
        } else if (level == 49) {
            formation = BrickLayerFormation.LINE;
            upTheStackes();
        } else if (level == 85) {
            formation = BrickLayerFormation.LINE;
            upTheStackes();
        } else if (level == 129) {
            formation = BrickLayerFormation.LINE;
            upTheStackes();
        } else if (level == 181) {
            formation = BrickLayerFormation.LINE;
            upTheStackes();
        } else if (level == 241) {
            formation = BrickLayerFormation.CASTLE;
            wallWidth = 1;  // will be ++
            upTheStackes();
        } else if (level == 257) {
            formation = BrickLayerFormation.CASTLE;
            wallWidth = 1;  // will be ++
            upTheStackes();
        }

        if (Global.LEVEL_LABEL != null) {
            Global.LEVEL_LABEL.setText("" + level);
        }
        if (Global.HEALTH_LABEL != null) {
            Global.HEALTH_LABEL.setText("" + health);
        }
        if (Global.BRICK_LABEL != null) {
            Global.BRICK_LABEL.setText("" + nrOfBricks);
        }

        Engine.preferences.putInteger("level", level);
        Engine.preferences.flush();
    }

    public void upTheStackes() {
        nrOfBricks = 1;
        wallWidth++;
        health += 1;
        score *= 2f;
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        entities.clear();
    }

}
