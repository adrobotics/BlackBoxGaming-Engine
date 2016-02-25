package com.blackboxgaming.engine.collision.collisionShapes;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.blackboxgaming.engine.util.Global;

/**
 *
 * @author Adrian
 */
public class Sphere extends Shape {

    public float radius;

    public Sphere(BoundingBox bounds) {
        super(bounds);
        radius = bounds.getDimensions(new Vector3()).len() / 2f;
    }

    @Override
    public boolean isVisible(Matrix4 transform) {
        return Global.getCamera().frustum.sphereInFrustum(transform.getTranslation(position).add(center), radius);
    }
     
    @Override
    public float intersects(Matrix4 transform, Ray ray) {
        transform.getTranslation(position).add(center);
        final float len = ray.direction.dot(position.x-ray.origin.x, position.y-ray.origin.y, position.z-ray.origin.z);
        if (len < 0f)
            return -1f;
        float dist2 = position.dst2(ray.origin.x+ray.direction.x*len, ray.origin.y+ray.direction.y*len, ray.origin.z+ray.direction.z*len);
        return (dist2 <= radius * radius) ? dist2 : -1f;
    }

}
