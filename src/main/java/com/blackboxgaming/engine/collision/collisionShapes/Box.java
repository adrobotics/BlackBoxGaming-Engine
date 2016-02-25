package com.blackboxgaming.engine.collision.collisionShapes;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.blackboxgaming.engine.util.Global;

/**
 *
 * @author Adrian
 */
public class Box extends Shape {

    public Box(BoundingBox bounds) {
        super(bounds);
    }

    @Override
    public boolean isVisible(Matrix4 transform) {
        return Global.getCamera().frustum.boundsInFrustum(transform.getTranslation(position).add(center), dimensions);
    }

    @Override
    public float intersects(Matrix4 transform, Ray ray) {
        transform.getTranslation(position).add(center);
        if (Intersector.intersectRayBoundsFast(ray, position, dimensions)) {
            final float len = ray.direction.dot(position.x - ray.origin.x, position.y - ray.origin.y, position.z - ray.origin.z);
            return position.dst2(ray.origin.x + ray.direction.x * len, ray.origin.y + ray.direction.y * len, ray.origin.z + ray.direction.z * len);
        }
        return -1f;
    }
}
