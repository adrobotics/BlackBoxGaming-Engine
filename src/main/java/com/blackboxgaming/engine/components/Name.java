package com.blackboxgaming.engine.components;

/**
 *
 * @author Adrian
 */
public class Name implements IComponent {

    public String name = "N/A";

    public Name(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Name{" + "name=" + name + '}';
    }
    
}
