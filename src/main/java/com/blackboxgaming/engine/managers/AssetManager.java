package com.blackboxgaming.engine.managers;

import com.badlogic.gdx.utils.Disposable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adrian
 */
public class AssetManager implements Disposable {

    public final com.badlogic.gdx.assets.AssetManager assetManager = new com.badlogic.gdx.assets.AssetManager();
    private boolean waitOnLoad;
    private final List<String> assetList = new ArrayList();
    public static final String pathToModels = "models/";
    public static final String pathToSounds = "sounds/";
    public static final String pathToTrees = pathToModels + "trees/";
    public static final String pathToGround = pathToModels + "ground/";
    public static final String pathToMonsters = pathToModels + "monsters/";
    public static final String pathToBoxes = pathToModels + "boxes/";

    public AssetManager() {
        this(true);
    }

    public AssetManager(boolean waitOnLoad) {
        this.waitOnLoad = waitOnLoad;
    }

    public void init() {
//        assetList.add(pathToTrees + "bigtooth_aspen.g3db");
//        assetList.add(pathToTrees + "black_spruce.g3db");
//        assetList.add(pathToTrees + "tree.g3db");
//        assetList.add(pathToTrees + "tree1.g3db");
//        assetList.add(pathToTrees + "tree2.g3db");
//        assetList.add(pathToTrees + "tree3.g3db");
//        assetList.add(pathToTrees + "rock1.g3db");
//        assetList.add(pathToTrees + "rock2.g3db");
//        assetList.add(pathToTrees + "rock3.g3db");
        assetList.add(pathToModels + "sky_box1.g3db");
//        assetList.add(pathToGround + "ground.g3db");
//        assetList.add(pathToMonsters + "Zombie2.g3db");
//        assetList.add(pathToModels + "Box.g3db");
        assetList.add(pathToBoxes + "box.g3db");
        assetList.add(pathToBoxes + "box2.g3db");
        assetList.add(pathToBoxes + "box2physics.g3db");
        assetList.add(pathToBoxes + "box2min.g3db");
        assetList.add(pathToBoxes + "box2minhole.g3db");

        System.out.println(this.getClass() + " loading " + assetList.size() + " models");
        long start = System.currentTimeMillis();

        for (String asset : assetList) {
            assetManager.load(asset, com.badlogic.gdx.graphics.g3d.Model.class);
        }
        if (waitOnLoad) {
            updateAssetManager();
        }

        long stop = System.currentTimeMillis();
        System.out.println(this.getClass() + " loading took " + (stop - start) / 1000f + " sec");
    }

    public void addModel(String filename) {
        addModel(filename, true);
    }

    public void addModel(String filename, boolean update) {
        System.out.println(this.getClass() + " loading " + filename);
        long start = System.currentTimeMillis();
        assetList.add(pathToModels + filename);
        assetManager.load(pathToModels + filename, com.badlogic.gdx.graphics.g3d.Model.class);
        if (update) {
            updateAssetManager();
        }
        long stop = System.currentTimeMillis();
        System.out.println(this.getClass() + " loading took " + (stop - start) / 1000f + " sec");
    }

    public com.badlogic.gdx.graphics.g3d.Model getModel(String modelName) {
        return assetManager.get(pathToModels + modelName + ".g3db", com.badlogic.gdx.graphics.g3d.Model.class);
    }

    public com.badlogic.gdx.graphics.g3d.Model getTreeModel(String modelName) {
        return assetManager.get(pathToTrees + modelName + ".g3db", com.badlogic.gdx.graphics.g3d.Model.class);
    }

    public com.badlogic.gdx.graphics.g3d.Model getMonsterModel(String modelName) {
        return assetManager.get(pathToMonsters + modelName + ".g3db", com.badlogic.gdx.graphics.g3d.Model.class);
    }

    public void updateAssetManager() {
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
        assetList.clear();
    }

}
