package com.blackboxgaming.engine.systems.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.TextMessage;
import com.blackboxgaming.engine.systems.AbstractSystem;

/**
 * Renders text on screen
 *
 * @author Adrian
 */
public class TextRendererSystem extends AbstractSystem {

    private final Stage stage = new Stage();
    private final BitmapFont font;

    public TextRendererSystem() {
        this(14, Color.GREEN);
    }

    public TextRendererSystem(int fontSize, Color textColor) {
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = fontSize;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 1;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/arial.ttf"));
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    @Override
    public void add(Entity entity) {
        if (accept(entity) && !entities.contains(entity)) {
            entities.add(entity);
            TextMessage text = entity.get(TextMessage.class);
            Label label = new Label(text.text, new LabelStyle(font, text.color));
            label.setAlignment(text.alignment);
            label.setX(text.x);
            label.setY(text.y);
            text.label = label;
            stage.addActor(label);
        }
    }

    @Override
    public void remove(Entity entity) {
        if (entity.has(TextMessage.class)) {
            entity.get(TextMessage.class).label.remove();
        }
        entities.remove(entity);
    }

    @Override
    public void markRequiredComponents() {
        requiredComponents.add(TextMessage.class);
    }

    @Override
    public void update(float delta) {
        stage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        font.dispose();
        stage.dispose();
    }

}
