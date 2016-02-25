package com.blackboxgaming.engine.systems;

import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.components.Name;
import com.blackboxgaming.engine.components.HUDItem;
import com.blackboxgaming.engine.components.Velocity;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.util.Global;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Adrian
 */
public class UpdaterSystem implements ISystem, Disposable {

    private final List<Entity> entities = new ArrayList();
    
    @Override
    public void add(Entity e) {
        if (!entities.contains(e)) {
            entities.add(e);
        }
    }

    @Override
    public void remove(Entity entity) {
        entities.remove(entity);
    }
    
    @Override
    public void update(float delta) {
        // update HUD
        for (Entity entity : entities) {
            Name name = (Name) entity.get(Name.class);
            if (name != null) {
                if (name.name.equals("Time")) {
                    HUDItem text = (HUDItem) entity.get(HUDItem.class);
                    text.value = "" + new Date();
                }
                if (name.name.equals("FPS")) {
                    HUDItem text = (HUDItem) entity.get(HUDItem.class);
                    text.value = "" + Global.getFps();
                }
                if (name.name.equals("Delta")) {
                    HUDItem text = (HUDItem) entity.get(HUDItem.class);
                    text.value = "" + Global.getDeltaInMillis();
                }
                if (name.name.equals("Visible Objects")) {
                    HUDItem text = (HUDItem) entity.get(HUDItem.class);
                    text.value = "" + Global.VISIBLE_OBJECT_COUNT;
                }
                if (name.name.equals("SpecificObject")) {
                    HUDItem text = (HUDItem) entity.get(HUDItem.class);
                    Velocity velocity = (Velocity) entity.get(Velocity.class);
//                    text.value = "" + velocity.toPrintable();
                }
            }
        }
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        entities.clear();
    }

}
