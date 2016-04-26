package com.blackboxgaming.engine.systems.render;

import com.badlogic.gdx.graphics.Color;
import static com.badlogic.gdx.graphics.GL20.GL_TRIANGLES;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Health;
import com.blackboxgaming.engine.components.Model;
import com.blackboxgaming.engine.components.Transform;
import com.blackboxgaming.engine.systems.ISystem;
import static com.blackboxgaming.engine.systems.render.ModelRendererSystem.modelBatch;
import com.blackboxgaming.engine.util.Global;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adrian
 */
public class HealthBarRendererSystem implements ISystem, Disposable {

    private final List<Entity> entities = new ArrayList();
    private Health health;
    private final ShapeRenderer shapeRenderer;
    private final ModelBuilder modelBuilder;
    private final float width = 2f;
    private final float height = 0.25f;
    private final float depth = 0;
    private ModelInstance backPlate;
    private ModelInstance frontPlate;
    private final Matrix4 barTransform = new Matrix4();
    private final Vector2 vAngle = new Vector2();
    private final Vector3 tmp = new Vector3();

    private final Vector3 v00 = new Vector3(-0.5f, -0.5f, -0.5f);
    private final Vector3 v10 = new Vector3(0.5f, -0.5f, -0.5f);
    private final Vector3 v11 = new Vector3(0.5f, 0.5f, -0.5f);
    private final Vector3 v01 = new Vector3(-0.5f, 0.5f, -0.5f);
    private final Vector3 vN = new Vector3(0, 0, -1);

    private Vector3 healthPlate00;
    private Vector3 healthPlate10;
    private Vector3 healthPlate11;
    private Vector3 healthPlate01;
    private Vector3 healthPlateNormal;

    public HealthBarRendererSystem() {
        this.modelBuilder = new ModelBuilder();
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void add(Entity entity) {
        if (!entities.contains(entity)) {
            entities.add(entity);
        }
    }

    @Override
    public void remove(Entity entity) {
        entities.remove(entity);
    }

    @Override
    public void update(float delta) {
        for (Entity entity : entities) {
            health = entity.get(Health.class);

            if (Global.hideMaxHealth && health.currentHealth == health.maxHealth) {
                continue;
            }

            ModelInstance backPlateModel = getBackPlate();
            ModelInstance frontPlateModel = getFrontPlate();

            vAngle.set(Global.getCamera().direction.x, Global.getCamera().direction.z);
            barTransform.setToTranslation(entity.get(Transform.class).transform.getTranslation(tmp));
            barTransform.rotate(Vector3.Y, -vAngle.angle() - 90);
            barTransform.trn(tmp.set(0, entity.get(Model.class).boundingBox.max.y + 1f, 0));
            frontPlateModel.transform.set(barTransform);
            backPlateModel.transform.set(barTransform);

            // render
            modelBatch.begin(Global.getCamera());
            modelBatch.render(backPlateModel, Global.getEnvironment());
            modelBatch.render(frontPlateModel, Global.getEnvironment());
            modelBatch.end();
        }
    }

    public ModelInstance getBackPlate() {
        if (backPlate == null) {
            float plateWidth = width + 0.1f;
            float plateHeight = height;
            float plateDepth = depth;
            MeshPartBuilder tile;
            Vector3 backPlate00 = v00.cpy().scl(plateWidth, plateHeight, plateDepth);
            Vector3 backPlate10 = v10.cpy().scl(plateWidth, plateHeight, plateDepth);
            Vector3 backPlate11 = v11.cpy().scl(plateWidth, plateHeight, plateDepth);
            Vector3 backPlate01 = v01.cpy().scl(plateWidth, plateHeight, plateDepth);
            Vector3 backPlateNormal = vN;

            modelBuilder.begin();
            tile = modelBuilder.part("backPlate", GL_TRIANGLES, VertexAttributes.Usage.Position, new Material(ColorAttribute.createDiffuse(Color.BLACK)));
            tile.rect(backPlate00, backPlate10, backPlate11, backPlate01, backPlateNormal);
            backPlate = new ModelInstance(modelBuilder.end());
        }
        return backPlate;
    }

    public ModelInstance getFrontPlate() {
        float plateWidth = width;
        float plateHeight = height - 0.12f;
        float plateDepth = depth - 0.01f;
        MeshPartBuilder tile;
        if (frontPlate == null) {
            healthPlate00 = v00.cpy().scl(plateWidth, plateHeight, plateDepth);
            healthPlate10 = v10.cpy().scl(plateWidth, plateHeight, plateDepth);
            healthPlate11 = v11.cpy().scl(plateWidth, plateHeight, plateDepth);
            healthPlate01 = v01.cpy().scl(plateWidth, plateHeight, plateDepth);
            healthPlateNormal = vN;

            modelBuilder.begin();
            tile = modelBuilder.part("healthPlate", GL_TRIANGLES, VertexAttributes.Usage.Position, new Material(ColorAttribute.createDiffuse(Color.GREEN)));
            tile.rect(healthPlate00, healthPlate10, healthPlate11, healthPlate01, healthPlateNormal);
            frontPlate = new ModelInstance(modelBuilder.end());
        } else {
            plateWidth = health.currentHealth * 100 / health.maxHealth / 100 * 2;
            healthPlate00 = v00.cpy().scl(plateWidth, plateHeight, plateDepth);
            healthPlate10 = v10.cpy().scl(plateWidth, plateHeight, plateDepth);
            healthPlate11 = v11.cpy().scl(plateWidth, plateHeight, plateDepth);
            healthPlate01 = v01.cpy().scl(plateWidth, plateHeight, plateDepth);
            healthPlateNormal = vN;
            modelBuilder.begin();
            tile = modelBuilder.part("healthPlate", GL_TRIANGLES, VertexAttributes.Usage.Position, new Material(ColorAttribute.createDiffuse(Color.GREEN)));
            tile.rect(healthPlate00, healthPlate10, healthPlate11, healthPlate01, healthPlateNormal);
            frontPlate.model.dispose();
            frontPlate = new ModelInstance(modelBuilder.end());
        }
        return frontPlate;
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        entities.clear();
        if (backPlate != null && backPlate.model != null) {
            backPlate.model.dispose();
        }
        if (frontPlate != null && frontPlate.model != null) {
            frontPlate.model.dispose();
        }
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
    }

}
