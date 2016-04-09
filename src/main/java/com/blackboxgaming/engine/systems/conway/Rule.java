package com.blackboxgaming.engine.systems.conway;

/**
 *
 * @author Adrian
 */
public class Rule {

    private final int s1, s2, b1, b2;

    public Rule(int s1, int s2, int b1, int b2) {
        this.s1 = s1;
        this.s2 = s2;
        this.b1 = b1;
        this.b2 = b2;
    }

    public boolean willSurvive(int neighbours) {
        return neighbours >= s1 && neighbours <= s2;
    }

    public boolean willGiveBirth(int neighbours) {
        return neighbours >= b1 && neighbours <= b2;
    }
}
