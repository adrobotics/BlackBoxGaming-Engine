package com.blackboxgaming.engine.components;

/**
 *
 * @author Adrian
 */
public class Damage implements IComponent{

    public float damage;

    public Damage(float damage) {
        this.damage = damage;
    }

    @Override
    public String toString() {
        return "Damage{" + "damage=" + damage + '}';
    }
    
}
