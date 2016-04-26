package com.blackboxgaming.engine.components;

/**
 * Component used to name an entity.
 *
 * @author Adrian
 */
public class Name implements IComponent {

    public String value = "N/A";

    public Name(String name) {
        this.value = name;
    }

    @Override
    public String toString() {
        return "Name{" + "name=" + value + '}';
    }
}
