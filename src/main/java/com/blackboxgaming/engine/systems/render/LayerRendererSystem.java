package com.blackboxgaming.engine.systems.render;

import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Layer;
import com.blackboxgaming.engine.systems.ISystem;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Adrian
 */
public class LayerRendererSystem implements ISystem, Disposable {

    public final List<Entity> layers = new LinkedList();

    @Override
    public void add(Entity entity) {
        if (!layers.contains(entity)) {
            layers.add(entity);
        }
    }

    @Override
    public void remove(Entity entity) {
        layers.remove(entity);
    }

    @Override
    public void update(float delta) {
        for (Entity entity : layers) {
            Layer layer = entity.get(Layer.class);
            layer.stage.act(delta);
            layer.stage.draw();
        }
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        for (Entity entity : layers) {
            entity.dispose();
        }
    }

}
