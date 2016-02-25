package com.blackboxgaming.engine.systems;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.util.Global;
import com.blackboxgaming.engine.util.WorldSetupUtil;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adrian
 */
public class ObstacleSpawnerSystem implements ISystem, Disposable {

    private final List<Entity> entities = new ArrayList();
    private final List<Entity> bricks = new ArrayList();
    public int maxObstacles;

    public ObstacleSpawnerSystem(int maxObstacles) {
        this.maxObstacles = maxObstacles;
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
        while (entities.size() < maxObstacles) {
            if (MathUtils.randomBoolean()) {
                bricks.addAll(WorldSetupUtil.createWall(Global.boxWidth - Global.boxWidth / 6f, Global.boxLength / 4f, 8, 1));
            } else {
                bricks.addAll(WorldSetupUtil.createWallAroundPoint(2, 6, Global.boxLength / 4f, 0, 0));
                bricks.addAll(WorldSetupUtil.createWallAroundPoint(4, 4, Global.boxLength / 4f, 0, 0));
            }
            for (Entity brick : bricks) {
                entities.add(brick);
            }
            bricks.clear();
        }
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        entities.clear();
    }

}
