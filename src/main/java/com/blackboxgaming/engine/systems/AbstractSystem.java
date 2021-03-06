package com.blackboxgaming.engine.systems;

import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.IComponent;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract system class, uses a set to store entities, doesn't allow
 * duplicates.
 *
 * @author adrian.popa
 */
public abstract class AbstractSystem implements ISystem, Disposable {

    /**
     * Set of entities this systems will effect. Intentionally not final so that
     * you can override and use HashMap for speed (default), LinkedHashMap for
     * insertion order or TreeSet for sorted order.
     */
    protected Set<Entity> entities = new HashSet();

    /**
     * Set of components any entity needs to have to be accepted by this system.
     */
    protected final Set<Class<? extends IComponent>> requiredComponents = new HashSet();

    // initialisation block used to mark requiredComponents
    {
        markRequiredComponents();
    }

    /**
     * Adds this entity to this system, doesn't do anything if the entity is
     * already present.
     *
     * @param entity
     */
    @Override
    public void add(Entity entity) {
        if (accept(entity) && !entities.contains(entity)) {
            entities.add(entity);
        }
    }

    /**
     * Removes the entity from this system.
     *
     * @param entity
     */
    @Override
    public void remove(Entity entity) {
        entities.remove(entity);
    }

    /**
     * Returns whether the entity has the required components of this system
     *
     * @param entity
     * @return
     */
    protected boolean accept(Entity entity) {
        if (requiredComponents.isEmpty()) {
            System.out.println("You forgot to configure what components " + this.getClass() + " requires.");
            return false;
        }
        for (Class<? extends IComponent> componentClass : requiredComponents) {
            if (!entity.has(componentClass)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Specify what components an entity needs to have in order to be affected
     * by this system by adding it to {@link #requiredComponents}.
     */
    public abstract void markRequiredComponents();

    /**
     * Should update all entities.
     *
     * @param delta
     */
    @Override
    public abstract void update(float delta);

    /**
     * Logs the disposal of this system.
     */
    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        entities.clear();
    }

}
