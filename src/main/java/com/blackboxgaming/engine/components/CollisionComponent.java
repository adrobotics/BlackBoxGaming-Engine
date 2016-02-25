package com.blackboxgaming.engine.components;

/**
 *
 * @author Adrian
 */
public class CollisionComponent implements IComponent {

    public String collisionShape;
    public int collisionFlag; // I am static, kinematic or custom
    public short contactCallbackFlag; // I am a part of this group
    public short contactCallbackFilter; // I collide with this group
    public int activationState;

    public CollisionComponent(String collisionShape, int collisionFlag, short contactCallbackFlag, short contactCallbackFilter, int activationState) {
        this.collisionShape = collisionShape;
        this.collisionFlag = collisionFlag;
        this.contactCallbackFlag = contactCallbackFlag;
        this.contactCallbackFilter = contactCallbackFilter;
        this.activationState = activationState;
    }
    
}
