package com.blackboxgaming.engine.global;

/**
 *
 * @author Adrian
 */
public class CollisionFlag {

    public final static short NOTHING = 0;
    public final static short GROUND_COLLISION_FLAG = 1 << 8;
    public final static short OBJECT_COLLISION_FLAG = 1 << 9;
    public final static short DECORATION_COLLISION_FLAG = 1 << 10;
    public final static short BULLET_COLLISION_FLAG = 1 << 11;
    public final static short COLLISION_FLAG_5 = 1 << 12;
    public final static short COLLISION_FLAG_6 = 1 << 13;
    public final static short COLLISION_FLAG_7 = 1 << 14;
    public final static short ALL_FLAG = -1;

}
