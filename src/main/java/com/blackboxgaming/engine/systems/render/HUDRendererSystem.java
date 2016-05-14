package com.blackboxgaming.engine.systems.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.components.HUDItem;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.systems.AbstractSystem;
import com.blackboxgaming.engine.systems.ConwaySystem;
import com.blackboxgaming.engine.systems.PhysicsSystem;
import com.blackboxgaming.engine.util.Global;
import java.util.LinkedHashSet;

/**
 * This system updates and draws {@link HUDItems}.
 *
 * @author Adrian
 */
public class HUDRendererSystem extends AbstractSystem {

    private final Stage stage = new Stage();
    private final Table table = new Table().padLeft(10).padBottom(10);
    private final BitmapFont font;
    private final Color fontColor = Color.RED;
    private final StringBuilder stringBuilder = new StringBuilder();
    private HUDItem hudItem;
    private Label label;

    public HUDRendererSystem() {
        entities = new LinkedHashSet();
        requiredComponents.add(HUDItem.class);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 16;
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    @Override
    public void add(Entity entity) {
        if (accept(entity) && !entities.contains(entity)) {
            boolean duplicate = false;
            for (Entity item : entities) {
                if (item.get(HUDItem.class).label.equals(entity.get(HUDItem.class).label)) {
                    duplicate = true;
                }
            }
            if (!duplicate) {
                entities.add(entity);
            }
        }
    }

    @Override
    public void update(float delta) {
        stage.clear();
        table.clear();
        //table.debug();
        for (Entity entity : entities) {
            hudItem = (HUDItem) entity.get(HUDItem.class);
            updateValues(entity, hudItem);
            stringBuilder.setLength(0);
            stringBuilder.append(hudItem.label).append(": ").append(hudItem.value).append(" ").append(hudItem.unit);
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
        switch (item.label.toLowerCase()) {
            case "fps":
                value = "" + Global.getFps();
                break;
            case "delta":
                value = "" + Global.getDeltaInMillis();
                break;
            case "entities":
                value = "" + Engine.entityManager.getCount();
                break;
            case "visible":
                value = "" + Global.VISIBLE_OBJECT_COUNT;
                break;
            case "physics":
                value = "" + (Engine.systemManager.has(PhysicsSystem.class) ? Engine.systemManager.get(PhysicsSystem.class).getCount() : "");
                break;
            case "camera":
                value = "" + String.format("%.2f", Global.getCamera().position.x)
                        + ", " + String.format("%.2f", Global.getCamera().position.y)
                        + ", " + String.format("%.2f", Global.getCamera().position.z);
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
            case "generation":
                if (Engine.systemManager.has(ConwaySystem.class)) {
                    value = "" + Engine.systemManager.get(ConwaySystem.class).getGeneration();
                } else {
                    value = "";
                }
                break;
            case "systems":
                value = "" + Engine.systemManager.size();
                break;
        }
        item.value = value;
    }

    @Override
    public void dispose() {
        super.dispose();
        stringBuilder.setLength(0);
        font.dispose();
        table.clear();
        stage.dispose();
    }

}
