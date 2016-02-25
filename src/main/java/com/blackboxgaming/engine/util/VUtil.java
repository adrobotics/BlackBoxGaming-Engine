package com.blackboxgaming.engine.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 *
 * @author Adrian
 */
public class VUtil {

    public static Vector2 toVector2(Vector3 v){
        return new Vector2(v.x, v.z);
    }
    
    public static Vector3 toVector3(Vector2 v){
        return new Vector3(v.x, 0, v.y);
    }
}
