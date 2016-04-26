package com.blackboxgaming.engine.managers;

import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.systems.ISystem;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Manager that stores systems.
 *
 * @author Adrian
 */
public class SystemManager implements Disposable {

    private Map<Class<? extends ISystem>, ISystem> systems = new LinkedHashMap();

    public boolean has(Class<? extends ISystem> type) {
        return systems.containsKey(type);
    }

    public void add(ISystem system) {
        if (!has(system.getClass())) {
            systems.put(system.getClass(), system);
            // add entities to this system
        }
    }

    public void addBefore(ISystem system, Class<? extends ISystem> referencePoint) {
        if (!has(system.getClass())) {
            Map<Class<? extends ISystem>, ISystem> newOrder = new LinkedHashMap();
            for (Map.Entry<Class<? extends ISystem>, ISystem> entry : systems.entrySet()) {
                Class<? extends ISystem> key = entry.getKey();
                ISystem value = entry.getValue();

                if (key == referencePoint) {
                    newOrder.put(system.getClass(), system);
                }
                newOrder.put(key, value);
            }
            systems = newOrder;
            // add entities to this system
        }
    }

    public void addAfter(ISystem system, Class<? extends ISystem> referencePoint) {
        if (!has(system.getClass())) {
            Map<Class<? extends ISystem>, ISystem> newOrder = new LinkedHashMap();
            for (Map.Entry<Class<? extends ISystem>, ISystem> entry : systems.entrySet()) {
                Class<? extends ISystem> key = entry.getKey();
                ISystem value = entry.getValue();

                newOrder.put(key, value);
                if (key == referencePoint) {
                    newOrder.put(system.getClass(), system);
                }
            }
            systems = newOrder;
            // add entities to this system
        }
    }

    public <E extends ISystem> E get(Class<E> type) {
        return (E) systems.get(type);
    }

    public void remove(Class<? extends ISystem> type) {
        systems.remove(type);
    }

    public void remove(Entity entity) {
        for (Map.Entry<Class<? extends ISystem>, ISystem> entrySet : systems.entrySet()) {
            entrySet.getValue().remove(entity);
        }
    }

    public void update(float delta) {
        for (Map.Entry<Class<? extends ISystem>, ISystem> entrySet : systems.entrySet()) {
            entrySet.getValue().update(delta);
        }
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        for (Map.Entry<Class<? extends ISystem>, ISystem> entrySet : systems.entrySet()) {
            if (entrySet.getValue() instanceof Disposable) {
                ((Disposable) entrySet.getValue()).dispose();
            }

        }
    }

}
