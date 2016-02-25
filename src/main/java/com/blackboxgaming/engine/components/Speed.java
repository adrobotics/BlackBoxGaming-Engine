package com.blackboxgaming.engine.components;

/**
 *
 * @author Adrian
 */
public class Speed implements IComponent {

    public float speed;
    public float angularSpeed;
    public float linearBoost;
    public float angularBoost;

    public Speed(float speed) {
        this(speed, speed * 36, 2);
    }

    public Speed(float speed, float angularMomentum) {
        this(speed, angularMomentum, 2);
    }

    public Speed(float speed, float angularMomentum, float linearBoost) {
        this(speed, angularMomentum, linearBoost, 2);
    }

    public Speed(float speed, float angularMomentum, float linearBoost, float angularBoost) {
        this.speed = speed;
        this.angularSpeed = angularMomentum;
        this.linearBoost = linearBoost;
        this.angularBoost = angularBoost;
    }

    @Override
    public String toString() {
        return "Speed{" + "speed=" + speed + ", angularSpeed=" + angularSpeed + ", linearBoost=" + linearBoost + ", angularBoost=" + angularBoost + '}';
    }

}
