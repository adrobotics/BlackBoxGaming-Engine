package com.blackboxgaming.engine.components;

/**
 * Marks the time to live from the moment this component is instantiated.
 *
 * @author Adrian
 */
public class Death implements IComponent {

    public final long startTime;
    public final int timeToLive;

    /**
     * Marks the time to live from the moment this component is instantiated.
     *
     * @param timeToLive in milliseconds
     */
    public Death(int timeToLive) {
        this.startTime = System.currentTimeMillis();
        this.timeToLive = timeToLive;
    }

}
