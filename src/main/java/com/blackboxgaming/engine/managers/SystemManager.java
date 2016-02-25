package com.blackboxgaming.engine.managers;

import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.systems.ISystem;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Adrian
 */
public class SystemManager implements Disposable {

    private final Map<Class<? extends ISystem>, ISystem> map = new LinkedHashMap();

    public boolean has(Class<? extends ISystem> type) {
        return map.containsKey(type);
    }

    public void add(ISystem system) {
        if (!has(system.getClass())) {
            map.put(system.getClass(), system);
            // add entities to this system
        }
    }

    public <E extends ISystem> E get(Class<E> type) {
        return (E) map.get(type);
    }

    public void remove(Class<? extends ISystem> type) {
        map.remove(type);
    }

    public void remove(Entity entity) {
        for (Map.Entry<Class<? extends ISystem>, ISystem> entrySet : map.entrySet()) {
            entrySet.getValue().remove(entity);
        }
    }

    public void update(float delta) {
        for (Map.Entry<Class<? extends ISystem>, ISystem> entrySet : map.entrySet()) {
            entrySet.getValue().update(delta);
        }
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        for (Map.Entry<Class<? extends ISystem>, ISystem> entrySet : map.entrySet()) {
            if (entrySet.getValue() instanceof Disposable) {
                ((Disposable) entrySet.getValue()).dispose();
            }

        }
    }

}
