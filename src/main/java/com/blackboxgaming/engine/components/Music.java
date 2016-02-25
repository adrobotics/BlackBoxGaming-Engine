package com.blackboxgaming.engine.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.managers.AssetManager;

/**
 *
 * @author Adrian
 */
public class Music implements IComponent, Disposable {

    public final com.badlogic.gdx.audio.Music sound;

    public Music(com.badlogic.gdx.audio.Music sound) {
        this.sound = sound;
    }

    public Music(String soundName) {
        this.sound = Gdx.audio.newMusic(Gdx.files.internal(AssetManager.pathToSounds + soundName));
    }

    @Override
    public void dispose() {
        sound.dispose();
    }

}
