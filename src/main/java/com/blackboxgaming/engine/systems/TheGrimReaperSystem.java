package com.blackboxgaming.engine.systems;

import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.Entity;
import java.util.LinkedList;

/**
 *
 * @author Adrian
 */
public class TheGrimReaperSystem implements ISystem, Disposable {

    private final LinkedList<Entity> entities = new LinkedList();
    private final int maxAlive;

    public TheGrimReaperSystem(int maxAlive) {
        this.maxAlive = maxAlive;
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
        while (entities.size() > maxAlive) {
            Entity life = entities.removeFirst();
            Engine.garbageManager.markForDeletion(life);
        }
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        entities.clear();
    }

}
