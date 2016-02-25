package com.blackboxgaming.engine;

import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.components.IComponent;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Adrian
 */
public class Entity {

    private static int NEXT_ID = 1;
    public final int id = NEXT_ID++;
    private final Map<Class<? extends IComponent>, IComponent> map = new HashMap();

    public boolean has(Class<? extends IComponent> type) {
        return map.containsKey(type);
    }

    public void add(IComponent component) {
        if (!has(component.getClass())) {
            map.put(component.getClass(), component);
        }
    }

    public <E extends IComponent> E get(Class<E> type) {
        return (E) map.get(type);
    }

    public void remove(Class<? extends IComponent> type) {
        map.remove(type);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Entity other = (Entity) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Entity{" + "id=" + id + '}';
    }

    public void dispose() {
        for (Map.Entry<Class<? extends IComponent>, IComponent> entrySet : map.entrySet()) {
            if (entrySet.getValue() instanceof Disposable) {
                ((Disposable) entrySet.getValue()).dispose();
            }
        }
    }

}
