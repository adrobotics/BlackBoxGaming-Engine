package com.blackboxgaming.engine.components;

/**
 *
 * @author Adrian
 */
public class Health implements IComponent {

    public float maxHealth;
    public float currentHealth;

    public Health(float maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = this.maxHealth;
    }

    public boolean isDead() {
        return currentHealth <= 0;
    }

    @Override
    public String toString() {
        return "Health{" + "maxHealth=" + maxHealth + ", currentHealth=" + currentHealth + '}';
    }

}
