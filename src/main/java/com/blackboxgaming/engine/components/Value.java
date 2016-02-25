package com.blackboxgaming.engine.components;

/**
 *
 * @author Adrian
 */
public class Value implements IComponent{

    private String valueName = "N/A";
    private float value = 0;

    public Value(String valueName, float value) {
        this.valueName = valueName;
        this.value = value;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
    
}
