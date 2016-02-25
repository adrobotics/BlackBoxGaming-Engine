package com.blackboxgaming.engine;

import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.components.IComponent;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents an entity in an entity system. Each instance of an
 * entity has a unique {@link #id} and a map of {@link #components}. Does not
 * allow multiple components of the same type.
 *
 * @author Adrian
 */
public class Entity {

    /**
     * Gets incremented every time a new instance is created
     */
    private static int NEXT_ID = 1;
    /**
     * Unique id
     */
    public final int id = NEXT_ID++;
    /**
     * Maps the components of this entity by class type. No duplicates are
     * allowed
     */
    private final Map<Class<? extends IComponent>, IComponent> components = new HashMap();

    /**
     * Checks if this entity has a certain type of component
     *
     * @param type of component to look for
     * @return true if this entity contains a component of given type, false
     * otherwise
     * @see IComponent
     */
    public boolean has(Class<? extends IComponent> type) {
        return components.containsKey(type);
    }

    /**
     * Adds a component to this entity. Doesn't allow duplicates. Components
     * don't get overwritten
     *
     * @param component to add
     * @see IComponent
     */
    public void add(IComponent component) {
        if (!has(component.getClass())) {
            components.put(component.getClass(), component);
        }
    }

    /**
     * Returns a component of the given class belonging to this entity
     *
     * @param <E> type of the component
     * @param type of component
     * @return component if exists, null otherwise
     * @see IComponent
     */
    public <E extends IComponent> E get(Class<E> type) {
        return (E) components.get(type);
    }

    /**
     * Removes a component of a given type from this entity
     *
     * @param <E> type of the component
     * @param type of component
     * @return component that has been removed, null if didn't exist in the
     * first place
     * @see IComponent
     */
    public <E extends IComponent> E remove(Class<E> type) {
        return (E) components.remove(type);
    }

    /**
     * Calls dispose() on all of this entities components that implement the
     * Disposable interface
     *
     * @see IComponent
     * @see Disposable
     */
    public void dispose() {
        for (Map.Entry<Class<? extends IComponent>, IComponent> entrySet : components.entrySet()) {
            if (entrySet.getValue() instanceof Disposable) {
                ((Disposable) entrySet.getValue()).dispose();
            }
        }
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

    /**
     * Returns {@link #id} and all of the {@link #components} and their values
     * of this entity in string form
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Entity{id=").append(id).append(", components=[");
        for (Map.Entry<Class<? extends IComponent>, IComponent> entrySet : components.entrySet()) {
            sb.append(entrySet.getValue()).append(", ");
        }
        if (!components.isEmpty()) {
            sb.replace(sb.length() - 2, sb.length(), "");
        }
        sb.append("]}");
        return sb.toString();
    }

}
