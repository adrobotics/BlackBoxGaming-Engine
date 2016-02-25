package com.blackboxgaming.engine.components;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.Engine;

/**
 *
 * @author Adrian
 */
public class Layer implements IComponent, Disposable {

    public final String layerName;
    public final Stage stage;
    public final Table table;

    public Layer(String layerName, Stage stage) {
        this.layerName = layerName;
        this.stage = stage;
        this.table = null;
    }
    
    public Layer(String layerName, Stage stage, Table table) {
        this.layerName = layerName;
        this.stage = stage;
        this.table = table;
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass() + " " + layerName);
        Engine.inputManager.remove(stage);
        stage.dispose();
    }
}
