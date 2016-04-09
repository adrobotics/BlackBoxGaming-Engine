package com.blackboxgaming.engine.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Disposable;

/**
 *
 * @author Adrian
 */
public class Model implements IComponent, Disposable {

    public final ModelInstance modelInstance;
    public final BoundingBox boundingBox;
    public boolean someoneElseHandlesDisposing = false;

    public Model(com.badlogic.gdx.graphics.g3d.Model model) {
        this.modelInstance = new ModelInstance(model);
        this.boundingBox = new BoundingBox();
        this.modelInstance.calculateBoundingBox(boundingBox);
    }

    public Model(com.badlogic.gdx.graphics.g3d.Model model, boolean someoneElseHandlesDisposing) {
        this.modelInstance = new ModelInstance(model);
        this.someoneElseHandlesDisposing = someoneElseHandlesDisposing;
        this.boundingBox = new BoundingBox();
        this.modelInstance.calculateBoundingBox(boundingBox);
    }

    public Model(com.badlogic.gdx.graphics.g3d.Model model, Color color, boolean someoneElseHandlesDisposing) {
        this.modelInstance = new ModelInstance(model);
        ((ColorAttribute) (modelInstance.materials.get(0).get(ColorAttribute.Diffuse))).color.set(color);
        this.someoneElseHandlesDisposing = someoneElseHandlesDisposing;
        this.boundingBox = new BoundingBox();
        this.modelInstance.calculateBoundingBox(boundingBox);
    }

    public Model(com.badlogic.gdx.graphics.g3d.Model model, Color color) {
        this.modelInstance = new ModelInstance(model);
        ((ColorAttribute) (modelInstance.materials.get(0).get(ColorAttribute.Diffuse))).color.set(color);
        this.boundingBox = new BoundingBox();
        this.modelInstance.calculateBoundingBox(boundingBox);
    }

    public Model(ModelInstance modelInstance) {
        this.modelInstance = modelInstance;
        this.boundingBox = new BoundingBox();
        this.modelInstance.calculateBoundingBox(boundingBox);
    }

    public Model(ModelInstance modelInstance, Color color) {
        this.modelInstance = modelInstance;
        ((ColorAttribute) (modelInstance.materials.get(0).get(ColorAttribute.Diffuse))).color.set(color);
        this.boundingBox = new BoundingBox();
        this.modelInstance.calculateBoundingBox(boundingBox);
    }

    public Model(ModelInstance modelInstance, float maxHeight) {
        this.boundingBox = new BoundingBox();
        modelInstance.calculateBoundingBox(boundingBox);
        float scalingFactor = maxHeight * 100f / boundingBox.getHeight() / 100f;
        for (int i = 0; i < modelInstance.nodes.size; i++) {
            modelInstance.nodes.get(i).scale.set(scalingFactor, scalingFactor, scalingFactor);
        }
        modelInstance.calculateTransforms();
        boundingBox.clr();
        this.modelInstance = modelInstance;
        this.modelInstance.calculateBoundingBox(boundingBox);
    }

    @Override
    public String toString() {
        return "MyModel{" + "model=" + modelInstance + '}';
    }

    @Override
    public void dispose() {
        if (!someoneElseHandlesDisposing) {
//            System.out.println("Disposing " + this.getClass());
            modelInstance.model.dispose();
        }
    }

}
