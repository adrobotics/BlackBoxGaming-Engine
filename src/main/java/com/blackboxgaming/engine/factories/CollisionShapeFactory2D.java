package com.blackboxgaming.engine.factories;

import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 *
 * @author Adrian
 */
public class CollisionShapeFactory2D {

    public static PolygonShape getBoxShape(float halfWidth, float halfHeight) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(halfWidth, halfHeight);
        return shape;
    }
    
    public static CircleShape getCircleShape(float radius) {
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        return shape;
    }

}
