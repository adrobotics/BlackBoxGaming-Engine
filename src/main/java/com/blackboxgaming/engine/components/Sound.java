package com.blackboxgaming.engine.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.managers.AssetManager;

/**
 *
 * @author Adrian
 */
public class Sound implements IComponent, Disposable {

    public final com.badlogic.gdx.audio.Sound sound;

    public Sound(com.badlogic.gdx.audio.Sound sound) {
        this.sound = sound;
    }

    public Sound(String soundName) {
        this.sound = Gdx.audio.newSound(Gdx.files.internal(AssetManager.pathToSounds + soundName));
    }

    @Override
    public void dispose() {
        sound.dispose();
    }

}
