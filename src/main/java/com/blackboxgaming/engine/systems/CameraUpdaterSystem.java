package com.blackboxgaming.engine.systems;

import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.util.Global;

/**
 *
 * @author Adrian
 */
public class CameraUpdaterSystem implements ISystem {

    @Override
    public void add(Entity entity) {
    }

    @Override
    public void remove(Entity entity) {
    }

    @Override
    public void update(float delta) {
        Global.getCamera().update();
    }

}
