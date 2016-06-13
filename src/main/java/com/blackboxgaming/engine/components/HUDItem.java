package com.blackboxgaming.engine.components;

/**
 * Component used to display information via {@link HUDSystem}.
 *
 * @author Adrian
 */
public class HUDItem implements IComponent {

    public String label;
    public String value;
    public String unit;

    public HUDItem(String label) {
        this(label, "", "");
    }

    public HUDItem(String label, String unit) {
        this(label, "", unit);
    }

    public HUDItem(String label, float value, String unit) {
        this(label, String.format("%.2f", value), unit);
    }

    public HUDItem(String label, String value, String unit) {
        this.label = label;
        this.value = value;
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "HUDItem{" + "label=" + label + ", value=" + value + ", unit=" + unit + '}';
    }
}
