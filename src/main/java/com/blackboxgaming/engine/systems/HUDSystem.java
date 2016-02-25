package com.blackboxgaming.engine.systems;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.components.HUDItem;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Puppet;
import com.blackboxgaming.engine.util.Global;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adrian
 */
public class HUDSystem implements ISystem, Disposable {

    private final List<Entity> entities = new ArrayList();
    private final Stage stage;
    private final Table table;
    private final BitmapFont font;
    private final Color fontColor = Color.RED;
    private final StringBuilder stringBuilder = new StringBuilder();
    private HUDItem hudItem;
    private Label label;

    public HUDSystem() {
        this.font = new BitmapFont();
        this.table = new Table().padLeft(10).padBottom(10);
        this.stage = new Stage();
    }

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
        stage.clear();
        table.clear();
        //table.debug();
        for (Entity entity : entities) {
            hudItem = (HUDItem) entity.get(HUDItem.class);
            if (hudItem.updateable) {
                updateValues(entity, hudItem);
            }
            stringBuilder.setLength(0);
            stringBuilder.append(hudItem.name).append(": ").append(hudItem.value).append(" ").append(hudItem.unit);
            label = new Label(" ", new Label.LabelStyle(font, fontColor));
            label.setText(stringBuilder);
            label.setAlignment(Align.bottom | Align.left);
            table.add(label).fill();
            table.row();
        }
        table.pack();
        stage.addActor(table);
        stage.draw();
    }
    
    private void updateValues(Entity entity, HUDItem item) {
        String value = item.value;
        switch (item.name.toLowerCase()) {
//            case "rotation":
//                Matrix4 matrix = new Matrix4();
//                Gdx.input.getRotationMatrix(matrix.val);
//                Quaternion q = matrix.getRotation(new Quaternion());
//                value = "" + q.getPitch();
//                break;
            case "camera":
                value = "" + String.format("%.2f", Global.getCamera().position.x)
                        + ", " + String.format("%.2f", Global.getCamera().position.y)
                        + ", " + String.format("%.2f", Global.getCamera().position.z);
                break;
            case "fps":
                value = "" + Global.getFps();
                break;
            case "bullet val":
                value = "" + String.format("%.2f", (Global.performanceCounter.load.value * 100f));
                break;
            case "bullet avg":
                value = "" + String.format("%.2f", (Global.performanceCounter.load.average * 100f));
                break;
            case "delta":
                value = "" + Global.getDeltaInMillis();
                break;
            case "entities":
                value = "" + Engine.entityManager.getCount();
                break;
            case "frustrum":
                value = "" + Global.VISIBLE_OBJECT_COUNT;
                break;
            case "physics":
                value = "" + (Engine.systemManager.has(PhysicsSystem.class) ? Engine.systemManager.get(PhysicsSystem.class).getCount() : "");
                break;
            case "isflying":
                value = "" + entity.get(Puppet.class).isFlying;
                break;
            case "gl-calls":
                value = "" + GLProfiler.calls;
                break;
            case "draw-calls":
                value = "" + GLProfiler.drawCalls;
                break;
            case "shader-switches":
                value = "" + GLProfiler.shaderSwitches;
                break;
            case "texture-bindings":
                value = "" + GLProfiler.textureBindings;
                break;
            case "vertices":
                value = "" + GLProfiler.vertexCount.total;
                break;
        }
        item.value = value;
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        entities.clear();
        stringBuilder.setLength(0);
        font.dispose();
        table.clear();
        stage.dispose();
    }

}
