package com.blackboxgaming.engine.collision.collisionShapes;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.collision.Ray;

/**
 *
 * @author Adrian
 */
public interface IShape {

    public abstract boolean isVisible(Matrix4 transform);

    public abstract float intersects(Matrix4 transform, Ray ray);

}
