package com.blackboxgaming.engine.managers;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.Engine;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Adrian
 */
public class AssetManager implements Disposable {

    public final com.badlogic.gdx.assets.AssetManager assetManager = new com.badlogic.gdx.assets.AssetManager();
    private final Map<String, com.badlogic.gdx.graphics.g3d.Model> assets = new HashMap();
    public static final String pathToModels = "models/";
    public static final String pathToSounds = "sounds/";
    public static final String pathToTrees = pathToModels + "trees/";
    public static final String pathToGround = pathToModels + "ground/";
    public static final String pathToMonsters = pathToModels + "monsters/";
    public static final String pathToBoxes = pathToModels + "boxes/";

    public AssetManager() {
    }

    /**
     * Loads default models
     */
    public void init() {
        assets.put(pathToModels + "knight/knight.g3db", null);
        assets.put(pathToModels + "knight/repo/knight.g3db", null);
        assets.put(pathToModels + "deer/deer.g3db", null);
        System.out.println(this.getClass() + " loading " + assets.size() + " models");
        long start = System.currentTimeMillis();
        for (Map.Entry<String, Model> entrySet : assets.entrySet()) {
            String modelName = entrySet.getKey();
            assetManager.load(modelName, com.badlogic.gdx.graphics.g3d.Model.class);
        }
        updateAssetManager();
        long stop = System.currentTimeMillis();
        System.out.println(this.getClass() + " loading took " + (stop - start) / 1000f + " sec");
    }

    /**
     * Loads and adds a new model to the asset manager. Model can be retrieved via {@link #getModel}
     *
     * @param modelName Name and path relative to models directory, ex: knight/knight.g3db
     */
    public void loadModel(String modelName) {
        if (modelName == null || modelName.isEmpty()) {
            System.out.println("Can't load model from null or empty modelName");
            return;
        }
        modelName = pathToModels + modelName;
        if (AssetManager.class.getClassLoader().getResource("assets/" + modelName) == null) {
            System.out.println("Model " + modelName + " does not exist");
            return;
        }
        System.out.print(this.getClass() + " loading " + modelName);
        long start = System.currentTimeMillis();
        assets.put(modelName, null);
        assetManager.load(modelName, com.badlogic.gdx.graphics.g3d.Model.class);
        updateAssetManager();
        long stop = System.currentTimeMillis();
        System.out.println(" took " + (stop - start) / 1000f + " sec");
    }

    /**
     * Retrieves an already loaded model from the asset manager. Load models via {@link addModel#loadModel}
     *
     * @param modelName Name and path relative to models directory, ex: knight/knight.g3db
     * @return The model
     */
    public com.badlogic.gdx.graphics.g3d.Model getModel(String modelName) {
        modelName = pathToModels + modelName;
        if (!assets.containsKey(modelName)) {
            System.out.println("Model not yet loaded");
            return null;
        }
        com.badlogic.gdx.graphics.g3d.Model model = assetManager.get(modelName, com.badlogic.gdx.graphics.g3d.Model.class);
        if (assets.get(modelName) == null) {
            assets.put(modelName, model);
        }
        Set<String> animationIds = new TreeSet();
        for (int i = 0; i < model.animations.size; i++) {
            animationIds.add(model.animations.get(i).id);
        }
        if (!animationIds.isEmpty()) {
            System.out.println("Model " + modelName + " has the following animations " + animationIds);
        }
        return model;
    }

    private void updateAssetManager() {
        while (!assetManager.update()) {
        }
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        try {
            assetManager.dispose();
        } catch (IllegalArgumentException ex) {
            System.out.println("very illegal argument, much error");
        }
        assets.clear();
    }

}
