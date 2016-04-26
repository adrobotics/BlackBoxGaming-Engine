package com.blackboxgaming.engine.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Layer;
import com.blackboxgaming.engine.input.JoystickController;
import com.blackboxgaming.engine.systems.LevelProgressionSystem;
import com.blackboxgaming.engine.systems.OrbitCameraSystem;
import com.blackboxgaming.engine.util.Global;

/**
 *
 * @author Adrian
 */
@Deprecated
public class LayerFactory {

    public static Layer createLevelAndHealthLayer() {
        Stage stage = new Stage(new StretchViewport(486, 864));
        Skin skin = new Skin();
        Table table = new Table();
        table.top().pad(10);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/fonts/Century Gothic Bold.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 40;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

        skin.add("star", new Texture("ui/icons/star.png"));
        skin.add("shield", new Texture("ui/icons/shield.png"));
        skin.add("brick", new Texture("ui/icons/brick.png"));
        skin.add("swipe128", new Texture("ui/icons/swipe128.png"));

        float padding = 10;
        Image icon = new Image(skin.getDrawable("star"));
        if (Global.debugLevelUp) {
            icon.addListener(new ClickListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    if (Engine.systemManager.has(LevelProgressionSystem.class)) {
                        for (Entity entity : Engine.systemManager.get(LevelProgressionSystem.class).entities) {
                            Engine.garbageManager.markForDeletion(entity);
                        }
                    }
                    super.touchUp(event, x, y, pointer, button);
                }
            });
        }
        table.add(icon).pad(padding);
        Label levelLabel = new Label("", labelStyle);
        table.add(levelLabel).pad(padding);
        icon = new Image(skin.getDrawable("shield"));
        table.add(icon).pad(padding);
        Label healthLabel = new Label("", labelStyle);
        table.add(healthLabel).pad(padding);
        icon = new Image(skin.getDrawable("brick"));
        table.add(icon).pad(padding);
        Label brickLabel = new Label("", labelStyle);
        table.add(brickLabel).pad(padding);

        table.row();

        // swipe
        if (Engine.systemManager.has(LevelProgressionSystem.class)) {
            if (Engine.systemManager.get(LevelProgressionSystem.class).level < 5) {
                icon = new Image(skin.getDrawable("swipe128"));
                table.add(icon).padTop(350).colspan(6);
                Global.swipeIcon = icon;

            }
        }

//        table.setDebug(true);
        table.setFillParent(true);
        stage.addActor(table);

        Global.LEVEL_LABEL = levelLabel;
        Global.HEALTH_LABEL = healthLabel;
        Global.BRICK_LABEL = brickLabel;

        if (Engine.systemManager.has(LevelProgressionSystem.class)) {
            Global.LEVEL_LABEL.setText("" + Engine.systemManager.get(LevelProgressionSystem.class).level);
            Global.HEALTH_LABEL.setText("" + Engine.systemManager.get(LevelProgressionSystem.class).health);
            Global.BRICK_LABEL.setText("" + Engine.systemManager.get(LevelProgressionSystem.class).nrOfBricks);
        }

        Engine.inputManager.add(stage);
        return new Layer("topLayer", stage, table);
    }

    public static Layer createScoreLayer() {
        Stage stage = new Stage();
        Table table = new Table();
        BitmapFont font = new BitmapFont();
        // TODO figure out how to scale
        // font.scale(2);
        Button button;

        Skin skin = new Skin();
        skin.add("bluebox", new Texture("textures/bluebox.jpg"));
        skin.add("greenbox", new Texture("textures/greenbox.jpg"));
        skin.add("redbox", new Texture("textures/redbox2.jpg"));

        ImageTextButtonStyle style = new ImageTextButtonStyle(skin.getDrawable("redbox"), skin.getDrawable("greenbox"), skin.getDrawable("bluebox"), font);

        button = new ImageTextButton("0", style);
        button.setHeight(150);
        button.setWidth(150);
        button.setName(((ImageTextButton) button).getText().toString());
        table.add(button).pad(5).center();

        table.pack();
        table.setPosition(Gdx.graphics.getWidth() / 2 - table.getWidth() / 2, Gdx.graphics.getHeight() - table.getHeight());
        stage.addActor(table);

        Global.scoreButton = button;
        return new Layer("scoreLayer", stage);
    }

    public static Layer createFireLayer() {
        Stage stage = new Stage(new StretchViewport(640, 480));
        Table table = new Table();
        BitmapFont font = new BitmapFont();
        Button button;

        Skin skin = new Skin();
        skin.add("bluebox", new Texture("textures/bluebox.jpg"));
        skin.add("greenbox", new Texture("textures/greenbox.jpg"));
        skin.add("redbox", new Texture("textures/redbox.jpg"));

        ImageTextButtonStyle style = new ImageTextButtonStyle(skin.getDrawable("redbox"), skin.getDrawable("greenbox"), skin.getDrawable("bluebox"), font);

        button = new ImageTextButton("Fire", style);
        button.setHeight(50);
        button.setWidth(50);
        button.setName(((ImageTextButton) button).getText().toString());
        button.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.getInputProcessor().keyDown(Keys.SPACE);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.getInputProcessor().keyUp(Keys.SPACE);
            }

        });
        table.add(button).pad(50).center();

        table.pack();
        table.setPosition(640 - table.getWidth(), 0);
        stage.addActor(table);

        Engine.inputManager.add(stage);
        return new Layer("fireLayer", stage);
    }

    public static Layer createJoystickLayer() {
        Stage stage = new Stage(new StretchViewport(1280, 720));
        Table table = new Table();
        MyListener listener = new MyListener();
        BitmapFont font = new BitmapFont();
        Button button;

        Skin skin = new Skin();
        skin.add("cirlce", new Texture("textures/circle.png"));
        ImageTextButtonStyle style = new ImageTextButtonStyle(skin.getDrawable("cirlce"), skin.getDrawable("cirlce"), skin.getDrawable("cirlce"), font);

        button = new ImageTextButton("Move", style);
        button.setHeight(150);
        button.setWidth(150);
        button.setName(((ImageTextButton) button).getText().toString());
        button.addListener(new MoveListener());
        table.add(button).pad(50).expandX().left();

        button = new ImageTextButton("Fire", style);
        button.setHeight(150);
        button.setWidth(150);
        button.setName(((ImageTextButton) button).getText().toString());
        button.addListener(listener);
        table.add(button).pad(50).bottom().right();

        table.setDebug(true);
        table.setFillParent(true);
        table.bottom().left();
        stage.addActor(table);

        Engine.inputManager.add(stage);
        return new Layer("joystickLayer", stage);
    }

    public static Layer createJoystickControlLayer() {
        Stage stage = new Stage(new StretchViewport(1280, 720));
        Table table = new Table();
        BitmapFont font = new BitmapFont();
        Button button;

        Skin skin = new Skin();
        skin.add("cirlce", new Texture("textures/circle.png"));
        ImageTextButtonStyle style = new ImageTextButtonStyle(skin.getDrawable("cirlce"), skin.getDrawable("cirlce"), skin.getDrawable("cirlce"), font);

        button = new ImageTextButton("Debug Options", style);
        button.setHeight(150);
        button.setWidth(150);
        button.setName(((ImageTextButton) button).getText().toString());
        button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Engine.game.setScreen(Engine.screens.get("debug"));
                return false;
            }
        });
        table.add(button).pad(50).padLeft(100).expandX().left();
        table.row();

        button = new ImageTextButton("L Boost", style);
        button.setHeight(150);
        button.setWidth(150);
        button.setName(((ImageTextButton) button).getText().toString());
        button.addListener(new Rotate(true, false, true));
        table.add(button).pad(50).padLeft(100).expandX().left();

        button = new ImageTextButton("R Boost", style);
        button.setHeight(150);
        button.setWidth(150);
        button.setName(((ImageTextButton) button).getText().toString());
        button.addListener(new Rotate(false, true, true));
        table.add(button).pad(50).padRight(100).bottom().right();
        table.row();

        button = new ImageTextButton("Left", style);
        button.setHeight(150);
        button.setWidth(150);
        button.setName(((ImageTextButton) button).getText().toString());
        button.addListener(new Rotate(true, false, false));
        table.add(button).pad(50).padLeft(100).expandX().left();

        button = new ImageTextButton("Right", style);
        button.setHeight(150);
        button.setWidth(150);
        button.setName(((ImageTextButton) button).getText().toString());
        button.addListener(new Rotate(false, true, false));
        table.add(button).pad(50).padRight(100).bottom().right();

//        table.setDebug(true);
        table.setFillParent(true);
        table.bottom().left();
        stage.addActor(table);

//        if (Engine.systemManager.has(OrbitCameraSystem.class) && Engine.systemManager.get(OrbitCameraSystem.class).mouseListener) {
        if (Engine.systemManager.has(OrbitCameraSystem.class)) {
//            InputProcessor tmp = Engine.systemManager.get(OrbitCameraSystem.class).orbitCameraController;
            InputProcessor tmp = null;
            Engine.inputManager.remove(tmp);
            Engine.inputManager.add(stage);
            Engine.inputManager.add(tmp);
        } else {
            Engine.inputManager.add(stage);
        }

        return new Layer("controlLayer", stage);
    }

}

class Boost extends InputListener {

    private final InputProcessor input = Gdx.input.getInputProcessor();

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        input.keyDown(Keys.SHIFT_LEFT);
        return true;
    }

    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        input.keyUp(Keys.SHIFT_LEFT);
    }
}

class Rotate extends InputListener {

    private final boolean left, right;
    private final InputProcessor input = Gdx.input.getInputProcessor();
    private boolean boost;

    public Rotate(boolean left, boolean right, boolean boost) {
        this.left = left;
        this.right = right;
        this.boost = boost;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (boost) {
            input.keyDown(Keys.SHIFT_LEFT);
        }
        if (left) {
            input.keyDown(Keys.Q);
        } else if (right) {
            input.keyDown(Keys.E);
        }
        return true;
    }

    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        if (boost) {
            input.keyUp(Keys.SHIFT_LEFT);
        }
        if (left) {
            input.keyUp(Keys.Q);
        } else if (right) {
            input.keyUp(Keys.E);
        }
    }
}

class MoveControlListener extends InputListener {

    private final JoystickController joystick;
    private final boolean reset;
    private final boolean fire;
    private final InputProcessor input = Gdx.input.getInputProcessor();

    public MoveControlListener(JoystickController joystick, boolean reset, boolean fire) {
        this.joystick = joystick;
        this.reset = reset;
        this.fire = fire;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        float dim = 150; // dimensions of button
        float dx = x - dim / 2f;
        float dy = y - dim / 2f;
        dy = -dy;
        joystick.set(dx, dy);
        if (fire) {
            input.keyDown(Keys.SPACE);
        }
        return true;
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        float dim = 150; // dimensions of button
        float dx = x - dim / 2f;
        float dy = y - dim / 2f;
        dy = -dy;
        joystick.set(dx, dy);
        if (fire) {
            input.keyDown(Keys.SPACE);
        }
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        if (reset) {
            joystick.set(0, 0);
        }
        if (fire) {
            input.keyUp(Keys.SPACE);
        }
    }
}

class RotateControlListener extends InputListener {

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button
    ) {
        InputProcessor input = Gdx.input.getInputProcessor();
        input.keyDown(Keys.SPACE);
        return true;
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        float cx = x - 75;
        cx = (cx < -25 ? -1 : (cx > 25 ? 1 : 0));
        InputProcessor input = Gdx.input.getInputProcessor();
        if (cx == 1) {
            input.keyDown(Keys.E);
            input.keyUp(Keys.Q);
        } else if (cx == -1) {
            input.keyDown(Keys.Q);
            input.keyUp(Keys.E);
        } else if (cx == 0) {
            input.keyUp(Keys.Q);
            input.keyUp(Keys.E);
        }
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button
    ) {
        InputProcessor input = Gdx.input.getInputProcessor();
        input.keyUp(Keys.Q);
        input.keyUp(Keys.E);
        input.keyUp(Keys.SPACE);
    }
}

class MoveListener extends InputListener {

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button
    ) {
        return true;
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        float cx = x - 75;
        cx = (cx < -75 ? -75 : (cx > 75 ? 75 : cx));
        float cy = y - 75;
        cy = (cy < -75 ? -75 : (cy > 75 ? 75 : cy));
        System.out.println(cx + ", " + cy);
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button
    ) {
    }
}

class MyListener extends InputListener {

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button
    ) {
        Gdx.input.getInputProcessor().keyDown(Keys.SPACE);
        System.out.println(pointer + ", " + button);
        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button
    ) {
        Gdx.input.getInputProcessor().keyUp(Keys.SPACE);
    }
}
