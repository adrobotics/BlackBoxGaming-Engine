package com.blackboxgaming.engine.systems.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.MinimapModel;
import com.blackboxgaming.engine.components.Model;
import com.blackboxgaming.engine.components.OrbitCameraFocus;
import com.blackboxgaming.engine.components.Transform;
import com.blackboxgaming.engine.systems.AbstractSystem;
import com.blackboxgaming.engine.util.Global;
import com.blackboxgaming.engine.util.VUtil;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author adrian.popa
 */
public class MinimapRendererSystem extends AbstractSystem {

    private final ShapeRenderer renderer = new ShapeRenderer();
    private final float size = 200;
    private final float scale = 4;
    private final float centerOffsetX = Gdx.graphics.getWidth() - size / 2f - 25;
    private final float centerOffsetY = Gdx.graphics.getHeight() - size / 2f - 25;
    private final Vector3 position = new Vector3();
    private Model model;
    private static Matrix4 focusTransform;

    public MinimapRendererSystem() {
        entities = new LinkedHashSet();
    }

    public static void focusOn(Entity focus) {
        if (focus.has(Transform.class)) {
            focusTransform = focus.get(Transform.class).transform;
        }
    }

    @Override
    public void add(Entity entity) {
        if (accept(entity) && !entities.contains(entity)) {
            entities.add(entity);
            // only sorts on add so to not waste time
            List<Entity> sorted = new LinkedList(entities);
            Collections.sort(sorted, new HeightOrderComparator());
            entities = new LinkedHashSet(sorted);
        }
    }

    @Override
    public void markRequiredComponents() {
        requiredComponents.add(Transform.class);
        requiredComponents.add(Model.class);
    }

    @Override
    public void update(float delta) {
        Global.getCamera().update();
        // margins
        renderer.begin(ShapeType.Line);
        renderer.setColor(Color.BLACK);
        renderer.rect(centerOffsetX - size / 2f, centerOffsetY - size / 2f, size, size);
        renderer.end();

        renderer.begin(ShapeType.Filled);
        renderer.setColor(Color.RED);
        for (Entity entity : entities) {
            if (entity.has(MinimapModel.class) && entity.get(MinimapModel.class).ignore) {
                continue;
            }
            entity.get(Transform.class).transform.getTranslation(position);
            model = entity.get(Model.class);
            Vector2 position2D = VUtil.toVector2(position);
            if (entity.has(MinimapModel.class)) {
                renderer.setColor(entity.get(MinimapModel.class).color);
            } else {
                renderer.setColor(((ColorAttribute) (model.modelInstance.materials.get(0).get(ColorAttribute.Diffuse))).color);
            }
            float focusOffsetX = 0;
            float focusOffsetY = 0;
            if (focusTransform != null) {
                focusOffsetX = focusTransform.getTranslation(new Vector3()).x;
                focusOffsetY = focusTransform.getTranslation(new Vector3()).z;
            }

            // I was really tired while writing this part :))
            float x = position2D.x * scale + centerOffsetX - model.boundingBox.getWidth() * scale / 2f - focusOffsetX * scale;
            float trueX = x;
            float y = position2D.y * scale + centerOffsetY - model.boundingBox.getDepth() * scale / 2f - focusOffsetY * scale;
            float trueY = y;
            if (x < centerOffsetX - size / 2f) {
                x = centerOffsetX - size / 2f;
            }
            if (y < centerOffsetY - size / 2f) {
                y = centerOffsetY - size / 2f;
            }
            
            if(trueX > centerOffsetX + size / 2f || trueY > centerOffsetY + size / 2f){
                continue;
            }

            float width = model.boundingBox.getWidth() * scale;
            float height = model.boundingBox.getDepth() * scale;
            
            if(trueX + width < centerOffsetX - size / 2f || trueY + height < centerOffsetY - size / 2f){
                continue;
            }
            
            if (trueX + width > centerOffsetX + size / 2f) {
                width = centerOffsetX + size / 2f - trueX;
            } else if (trueX + width < centerOffsetX + size / 2f) {
                width -= x - trueX;
            }
            if (trueY + height > centerOffsetY + size / 2f) {
                height = centerOffsetY + size / 2f - trueY;
            } else if (trueY + height < centerOffsetY + size / 2f) {
                height -= y - trueY;
            }
            
            renderer.rect(x, y, width, height);
        }
        renderer.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        renderer.dispose();
    }

    private class HeightOrderComparator implements Comparator<Entity> {

        @Override
        public int compare(Entity o1, Entity o2) {
            float o1Y = o1.get(Transform.class
            ).transform.getTranslation(position).y;

            float o2Y = o2.get(Transform.class
            ).transform.getTranslation(position).y;
            if (o1Y > o2Y) {
                return 1;
            } else if (o1Y < o2Y) {
                return -1;
            } else {
                return 0;
            }
        }
    }

}
