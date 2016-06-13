package com.blackboxgaming.engine.systems.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Death;
import com.blackboxgaming.engine.components.HUDMessage;
import com.blackboxgaming.engine.managers.SystemManager;
import com.blackboxgaming.engine.systems.AbstractSystem;
import com.blackboxgaming.engine.systems.TimedDeathSystem;
import com.blackboxgaming.engine.util.Global;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;

/**
 *
 * @author Adrian
 */
public class HUDMessageRendererSystem extends AbstractSystem {

    private final Stage stage = new Stage();
    private final Table table = new Table();
    private final BitmapFont font;
    private final LabelStyle labelStyle;
    private Label label;

    public HUDMessageRendererSystem() {
        this(18, Color.WHITE);
    }

    public HUDMessageRendererSystem(int fontSize, Color textColor) {
        entities = new LinkedHashSet();

        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = fontSize;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 1;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/arial.ttf"));
        font = generator.generateFont(parameter);
        generator.dispose();

        labelStyle = new LabelStyle(font, textColor);
        stage.addActor(table);
    }

    @Override
    public void markRequiredComponents() {
        requiredComponents.add(HUDMessage.class);
    }

    @Override
    public void add(Entity entity) {
        if (accept(entity) && !entities.contains(entity)) {
            LinkedList<Entity> list = new LinkedList(entities);
            Collections.reverse(list);
            list.add(entity);
            Collections.reverse(list);
            entities = new LinkedHashSet<>(list);
        }
        
    }

    
    /**
     * Creates and displays a big message for 5 seconds in the top-center of the screen. Adds {@link
     * HUDMessageRendererSystem} and {@link TimedDeathSystem} to the {@link SystemManager} if not already present.
     *
     * @param message to be displayed
     */
    public static void addTemporaryMessage(String message) {
        addTemporaryMessage(message, 5000);
    }

    /**
     * Creates and displays a big message temporarily in the top-center of the screen. Adds {@link
     * HUDMessageRendererSystem} and {@link TimedDeathSystem} to the {@link SystemManager} if not already present.
     *
     * @param message to be displayed
     * @param timeToDisplay in milliseconds
     */
    public static void addTemporaryMessage(String message, int timeToDisplay) {
        if (!Global.loaded) {
            return;
        }
        if (!Engine.systemManager.has(HUDMessageRendererSystem.class)) {
            Engine.systemManager.addAfter(new HUDMessageRendererSystem(), ModelRendererSystem.class);
        }
        if (!Engine.systemManager.has(TimedDeathSystem.class)) {
            Engine.systemManager.add(new TimedDeathSystem());
        }
        Entity hudMessage = new Entity();
        hudMessage.add(new HUDMessage(message));
        hudMessage.add(new Death(timeToDisplay));
        Engine.entityManager.add(hudMessage);
    }

    @Override
    public void update(float delta) {
        table.clear();
        //table.debug();
        for (Entity entity : entities) {
            String message = entity.get(HUDMessage.class).message;
            label = new Label(message, labelStyle);
            label.setAlignment(Align.center);
            table.add(label).fill();
            table.row();
        }
        table.pack();
        table.setX((Gdx.graphics.getWidth() - table.getWidth()) / 2);
        table.setY(Gdx.graphics.getHeight() - table.getHeight());
        stage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        font.dispose();
        table.clear();
        stage.dispose();
    }

}
