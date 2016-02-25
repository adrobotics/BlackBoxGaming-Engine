package com.blackboxgaming.engine.components;

/**
 *
 * @author Adrian
 */
public class Death implements IComponent {

    public final long startTime;
    public final int timeToLive;

    public Death(int timeToLive) {
        this.startTime = System.currentTimeMillis();
        this.timeToLive = timeToLive;
    }

}
