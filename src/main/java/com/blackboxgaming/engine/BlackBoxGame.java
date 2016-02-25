package com.blackboxgaming.engine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Adrian
 */
public abstract class BlackBoxGame extends Game{

    public Map<String, Screen> screens = new HashMap();
    
}
