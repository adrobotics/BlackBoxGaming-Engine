package com.blackboxgaming.engine.systems.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
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
    private final float halfSize = size / 2f;
    private final float scale = 4;
    private final float margin = 25;
    private final float centerOffsetX = Gdx.graphics.getWidth() - halfSize - margin;
    private final float centerOffsetY = Gdx.graphics.getHeight() - halfSize - margin;
    private final Vector2 focus = new Vector2();
    private final Vector2 position = new Vector2();
    private Model model;
    private static Matrix4 focusTransform = new Matrix4();
    private static Matrix4 objectTransform = new Matrix4();
    private final static Quaternion tmp = new Quaternion();
    public static boolean rotateIcons;
    public static boolean focusOrientation;

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
        renderer.identity();
        renderer.translate(centerOffsetX, centerOffsetY, 0);
        renderer.rect(-halfSize, -halfSize, size, size);
        renderer.end();

        // scale and invert y for center focus point
        focus.set(VUtil.toVector2(focusTransform.getTranslation(new Vector3()))).scl(scale).scl(1, -1);

        renderer.begin(ShapeType.Filled);
        renderer.identity();
        renderer.translate(centerOffsetX - focus.x, centerOffsetY - focus.y, 0);
        for (Entity entity : entities) {
            if (entity.has(MinimapModel.class) && entity.get(MinimapModel.class).ignore) {
                // skip if set to ignore
                continue;
            }

            // color
            model = entity.get(Model.class);
            if (entity.has(MinimapModel.class)) {
                // get color from component
                renderer.setColor(entity.get(MinimapModel.class).color);
            } else {
                // get color from model
                renderer.setColor(((ColorAttribute) (model.modelInstance.materials.get(0).get(ColorAttribute.Diffuse))).color);
            }

            // position and width of object, invert y
            objectTransform = entity.get(Transform.class).transform;
            position.set(VUtil.toVector2(objectTransform.getTranslation(new Vector3()))).scl(scale).scl(1, -1);
            float width = model.boundingBox.getWidth() * scale;
            float height = model.boundingBox.getDepth() * scale;

            // drawing variables
            float drawWidth = width;
            float drawHeight = height;
            float drawX = position.x - drawWidth / 2f;
            float drawY = position.y - drawHeight / 2f;

            if (drawX + drawWidth >= focus.x + halfSize) {
                if (drawX >= focus.x + halfSize) {
                    // outside right
                    continue;
                } else {
                    // partially inside
                    drawWidth = focus.x + halfSize - drawX;
                }
            }
            if (drawX <= focus.x - halfSize) {
                if (drawX + drawWidth <= focus.x - halfSize) {
                    // outside left
                    continue;
                } else {
                    // partially inside
                    drawWidth -= focus.x - halfSize - drawX;
                    drawX = focus.x - halfSize;
                }
            }
            if (drawY + drawHeight >= focus.y + halfSize) {
                if (drawY >= focus.y + halfSize) {
                    // outside top
                    continue;
                } else {
                    // partially inside
                    drawHeight = focus.y + halfSize - drawY;
                }
            }
            if (drawY <= focus.y - halfSize) {
                if (drawY + drawHeight <= focus.y - halfSize) {
                    // outside bottom
                    continue;
                } else {
                    // partially inside
                    drawHeight -= focus.y - halfSize - drawY;
                    drawY = focus.y - halfSize;
                }
            }

            // render
            if (rotateIcons) {
                renderer.rect(drawX, drawY, drawWidth / 2f, drawHeight / 2f, drawWidth, drawHeight, 1, 1, objectTransform.getRotation(tmp).getYaw());
            } else {
                renderer.rect(drawX, drawY, drawWidth, drawHeight);
            }
        }
        if (focusOrientation) {
            renderer.setColor(Color.BLACK);
            renderer.line(focus, (VUtil.toVector2(focusTransform.cpy().translate(2, 0, 0).getTranslation(new Vector3()))).scl(scale).scl(1, -1));
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
            float o1Y = o1.get(Transform.class).transform.getTranslation(new Vector3()).y;
            float o2Y = o2.get(Transform.class).transform.getTranslation(new Vector3()).y;
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
