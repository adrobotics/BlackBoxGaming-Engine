package com.blackboxgaming.engine.systems;

import com.blackboxgaming.engine.Entity;

/**
 *
 * @author Adrian
 */
public interface ISystem {

    public void add(Entity entity);
    
    public void remove(Entity entity);

    public void update(float delta);
}
