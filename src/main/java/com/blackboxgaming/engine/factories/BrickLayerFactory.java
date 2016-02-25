package com.blackboxgaming.engine.factories;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.Collision;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Health;
import com.blackboxgaming.engine.components.Physics;
import com.blackboxgaming.engine.components.Score;
import com.blackboxgaming.engine.components.Transform;
import com.blackboxgaming.engine.global.constants.Constants;
import com.blackboxgaming.engine.util.Randomizer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adrian
 */
public class BrickLayerFactory {

    private static final List<Entity> entities = new ArrayList();

    public static List<Entity> layBricks(BrickLayerConfig config) {
        entities.clear();

        switch (config.formation) {
            case LINE:
                entities.addAll(createBrickWall(config));
                break;
            case CASTLE:
                entities.addAll(createBrickCastle(config));
                break;
        }

        return entities;
    }

    public static List<Entity> createBrickWall(BrickLayerConfig config) {
        List<Entity> bricks = new ArrayList();
        int layedBricks = 0;
        for (int k = 0; k < config.wallDim.z && layedBricks < config.nrOfBricks; k++) {
            float x = config.center.x - config.dim.z * k;
            for (int i = 0; i < config.wallDim.y && layedBricks < config.nrOfBricks; i++) {
                float y = config.dim.y / 2f + i * config.dim.y;
                for (int j = 0; j < config.wallDim.x && layedBricks < config.nrOfBricks; j++) {
                    float z = config.center.z + -config.wallDim.x / 2f * config.dim.x + config.dim.x / 2f + j * config.dim.x;

                    // first and last
//                    if (j == 0 || j == config.wallDim.x / config.dim.x - 1) {
//                        continue;
//                    }
                    if (i % 2 == 0) {
                        bricks.add(createBrick(config, x, y, z, 90));
                        layedBricks++;
                    } else {
                        if (j != 0) {
                            bricks.add(createBrick(config, x, y, z - config.dim.x / 2f, 90));
                            layedBricks++;
                        }
                    }
                }
            }
        }

        return bricks;
    }

    public static List<Entity> createBrickCastle(BrickLayerConfig config) {
        List<Entity> bricks = new ArrayList();
        int layedBricks = 0;

        float offest = -(((config.wallDim.x * config.dim.x + config.dim.z) / 2f) - config.dim.z / 2f);

        for (int i = 0; i < config.wallDim.y && layedBricks < config.nrOfBricks; i++) {
            float y = config.center.y + config.dim.y / 2f + config.dim.y * i;
            for (float j = 0; j < config.wallDim.x && layedBricks < config.nrOfBricks; j++) {
                if (i % 2 == 0) {
                    bricks.add(createBrick(config, config.center.x + offest, y, config.center.z + offest + config.dim.x / 2f - config.dim.z / 2f + config.dim.x * j, 90));
                    bricks.add(createBrick(config, config.center.x - offest, y, config.center.z - offest + config.dim.x / 2f - config.dim.z / 2f - config.dim.x * j - config.dim.x / 2f, 90));
                    bricks.add(createBrick(config, config.center.x + offest + config.dim.x / 2f + config.dim.z / 2f + config.dim.x * j, y, config.center.z + offest, 0));
                    bricks.add(createBrick(config, config.center.x - offest - config.dim.x / 2f - config.dim.z / 2f - config.dim.x * j, y, config.center.z + -offest, 0));
                } else {
                    bricks.add(createBrick(config, config.center.x + offest, y, config.center.z + offest + config.dim.x / 2f - config.dim.z / 2f + config.dim.x * j + config.dim.z, 90));
                    bricks.add(createBrick(config, config.center.x - offest, y, config.center.z - offest + config.dim.x / 2f - config.dim.z / 2f - config.dim.x * j - config.dim.z - config.dim.x / 2f, 90));
                    bricks.add(createBrick(config, config.center.x + offest + config.dim.x / 2f + config.dim.z / 2f + config.dim.x * j - config.dim.z, y, config.center.z + offest, 0));
                    bricks.add(createBrick(config, config.center.x - offest - config.dim.x / 2f - config.dim.z / 2f - config.dim.x * j + config.dim.z, y, config.center.z + -offest, 0));
//                    bricks.add(createBrick(config, config.center.x + offest + config.dim.x / 2f - config.dim.z / 2f + config.dim.x * j + config.dim.z, y, config.center.z + -offest, 0));
                }
                layedBricks++;
            }
        }
        return bricks;
    }

    private static Entity createBrick(BrickLayerConfig config, float x, float y, float z, float rotation) {
        Entity brick = new Entity();
        brick.add(new Transform(x, y, z, rotation, 0, 0));
        brick.add(new com.blackboxgaming.engine.components.Model(config.model, Randomizer.getRandomColor(), true));
        brick.add(new Health(config.health));
        brick.add(new Score(config.score));
        brick.add(new Physics(CollisionShapeFactory.getBoxShape(config.dim.x, config.dim.y, config.dim.z), config.mass, btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK, Constants.OBSTACLE_FLAG, (short) 0, Collision.DISABLE_DEACTIVATION));
        brick.get(Physics.class).body.setRestitution(config.resitution);

        return brick;
    }

    public enum BrickLayerFormation {

        LINE, CASTLE
    }

    public static class BrickLayerConfig {

        public Model model;
        public Vector3 center;
        public Vector3 dim;
        public BrickLayerFormation formation;
        public int nrOfBricks;
        public int health;
        public float score;
        public float mass;
        public float resitution;
        public Vector3 wallDim;

        public BrickLayerConfig(Model model, Vector3 center, Vector3 brickDimensions, BrickLayerFormation formation, int nrOfBricks, int health, float score, float mass, float resitution, Vector3 wallDim) {
            this.model = model;
            this.center = center;
            this.dim = brickDimensions;
            this.formation = formation;
            this.nrOfBricks = nrOfBricks;
            this.health = health;
            this.score = score;
            this.mass = mass;
            this.resitution = resitution;
            this.wallDim = wallDim;
        }

    }
}
