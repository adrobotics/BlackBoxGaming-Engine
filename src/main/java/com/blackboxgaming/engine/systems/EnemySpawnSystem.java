package com.blackboxgaming.engine.systems;

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
public class EnemySpawnSystem implements ISystem, Disposable {

    private final List<Entity> entities = new ArrayList();
    private long delay = 1500;
    private long start = 0;
    private int neutral, boss, following;

    public EnemySpawnSystem(int neutral, int boss, int following) {
        this.neutral = neutral;
        this.boss = boss;
        this.following = following;
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
        if (entities.isEmpty()) {
            addMoreEnemies(10, 10);
        }
    }

    private void addMoreEnemies(int n, int m) {
        if (start == 0) {
            start = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - start >= delay) {
            System.out.println("Spawning " + n + " new enemies.");
            WorldSetupUtil.addNeutralEnemies(Global.mapWidth / 2, neutral);
            WorldSetupUtil.addBossEnemies(Global.mapWidth / 2, boss);
            WorldSetupUtil.addFollowingEnemies(Global.mapWidth/2, following);
            start = 0;
        }
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        entities.clear();
    }

}
