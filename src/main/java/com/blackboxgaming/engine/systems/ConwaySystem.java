package com.blackboxgaming.engine.systems;

import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Cell;
import com.blackboxgaming.engine.components.HUDItem;
import com.blackboxgaming.engine.components.Model;
import com.blackboxgaming.engine.components.Transform;
import com.blackboxgaming.engine.factories.ModelFactory;
import com.blackboxgaming.engine.systems.conway.GenerationRezolver;
import com.blackboxgaming.engine.systems.conway.Rule;
import com.blackboxgaming.engine.util.Randomizer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Adrian
 */
public class ConwaySystem implements ISystem, Disposable, Runnable {

    public final Map<Cell, Entity> universe = new HashMap();
    private final Set<Cell> nextGeneration = new HashSet();
    public GenerationRezolver generationRezolver;
    public static final Rule life4555 = new Rule(4, 5, 5, 5);
    public static final Rule life5766 = new Rule(5, 7, 6, 6);
    private long lastRun;
    private long timeSpent;
    private final long timeBetweenRuns;
//    private boolean generating;
    public boolean manualBreak = true;
    private Set<Entity> toBeAdded = new HashSet();
    private Set<Entity> toBeRemoved = new HashSet();

    public ConwaySystem(Rule rule) {
        this(rule, 0);
    }

    public ConwaySystem(Rule rule, long timeBetweenRuns) {
        this.generationRezolver = new GenerationRezolver(rule);
        this.timeBetweenRuns = timeBetweenRuns;
    }

    @Override
    public void add(Entity entity) {
        universe.put(entity.get(Cell.class), entity);
    }

    @Override
    public void remove(Entity entity) {
        universe.remove(entity.get(Cell.class));
    }

    public void update2(float delta) {
//        if (!manualBreak && !generating && lastRun + timeBetweenRuns - timeSpent < System.currentTimeMillis()) {
            for (Entity entity : toBeAdded) {
                Engine.entityManager.add(entity);
            }
            for (Entity entity : toBeRemoved) {
                Engine.garbageManager.markForDeletion(entity);
            }
            toBeAdded.clear();
            toBeRemoved.clear();
            lastRun = System.currentTimeMillis();
//            generating = true;
            new Thread(this).start();
//        }
    }

    @Override
    public void update(float delta) {
        if (!manualBreak && lastRun + timeBetweenRuns - timeSpent < System.currentTimeMillis()) {
            timeSpent = System.currentTimeMillis();
            lastRun = System.currentTimeMillis();
            toBeAdded.clear();
            toBeRemoved.clear();

            nextGeneration();

            for (Entity entity : toBeAdded) {
                Engine.entityManager.add(entity);
            }
            for (Entity entity : toBeRemoved) {
                Engine.garbageManager.markForDeletion(entity);
            }

            timeSpent = System.currentTimeMillis() - timeSpent;
        }
    }

    public void clearUniverse() {
        manualBreak = true;
//        while (generating) {
//            //no op.
//        }
        toBeAdded.clear();
//        for (Entity entity : toBeRemoved) {
//            Engine.garbageManager.markForDeletion(entity);
//        }
        toBeRemoved.clear();
        for (Map.Entry<Cell, Entity> entrySet : Engine.systemManager.get(ConwaySystem.class).universe.entrySet()) {
            Engine.garbageManager.markForDeletion(entrySet.getValue());
        }
    }

    @Override
    public void run() {
        timeSpent = System.currentTimeMillis();
        nextGeneration();
//        generating = false;
        timeSpent = System.currentTimeMillis() - timeSpent;
    }

    private void nextGeneration() {
        nextGeneration.clear();
        nextGeneration.addAll(generationRezolver.nextGeneration(universe.keySet()));

        // remove cells from old generation
        Iterator<Map.Entry<Cell, Entity>> iterator = universe.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Cell, Entity> entry = iterator.next();
            if (!nextGeneration.contains(entry.getKey())) {
                toBeRemoved.add(entry.getValue());
                iterator.remove();
            }
        }

        // add new cells to universe
        for (Cell cell : nextGeneration) {
            if (!universe.containsKey(cell)) {
                Entity e = new Entity();
                e.add(cell);
                e.add(new Transform(cell.x, cell.y, cell.z));
                e.add(new Model(ModelFactory.getTheSameCubeModel(1), Randomizer.getRandomColor(), true));
                universe.put(cell, e);
                toBeAdded.add(e);
            }
        }
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        universe.clear();
        nextGeneration.clear();
    }

}
