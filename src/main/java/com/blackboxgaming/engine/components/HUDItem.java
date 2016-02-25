package com.blackboxgaming.engine.components;

/**
 *
 * @author Adrian
 */
public class HUDItem implements IComponent {

    public String name;
    public String value;
    public String unit;
    public boolean updateable;

    public HUDItem(String name, String value, String unit) {
        this(name, value, unit, false);
    }

    public HUDItem(String name, String value, String unit, boolean updateable) {
        this.name = name;
        this.value = value;
        this.unit = unit;
        this.updateable = updateable;
    }

}
