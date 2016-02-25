package com.blackboxgaming.engine.factories;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCapsuleShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btCompoundShape;
import com.badlogic.gdx.physics.bullet.collision.btConeShape;
import com.badlogic.gdx.physics.bullet.collision.btConvexHullShape;
import com.badlogic.gdx.physics.bullet.collision.btCylinderShape;
import com.badlogic.gdx.physics.bullet.collision.btSphereShape;
import com.blackboxgaming.engine.util.Global;

/**
 *
 * @author Adrian
 */
public class CollisionShapeFactory {

    public static btBoxShape getCubeShape(float size) {
        return new btBoxShape(new Vector3(0.5f * size, 0.5f * size, 0.5f * size));
    }

    public static btSphereShape getSphereShape(float size) {
        return new btSphereShape(0.5f * size);
    }

    public static btCompoundShape getSphereShape(float size, Vector3 origin) {
        btCompoundShape collistionShape = new btCompoundShape();
        collistionShape.addChildShape(new Matrix4().translate(origin), new btSphereShape(0.5f * size));
        return collistionShape;
    }

    public static btConeShape getConeShape(float size) {
        return new btConeShape(0.5f * size, 2f * size);
    }

    public static btCapsuleShape getCapsuleShape(float size) {
        return new btCapsuleShape(0.5f * size, 1f * size);
    }

    public static btCylinderShape getCylinderShape(float size) {
        return new btCylinderShape(new Vector3(0.5f * size, size, 0.5f * size));
    }

    public static btCylinderShape getCylinderShape(float radius, float length) {
        return new btCylinderShape(new Vector3(radius, 0.5f * length, radius));
    }

    public static btBoxShape getTileShape(float size) {
        return new btBoxShape(new Vector3(0.5f * size, size / 20f, 0.5f * size));
    }

    public static btBoxShape getBoxShape(float width, float height, float depth) {
        return new btBoxShape(new Vector3(0.5f * width, 0.5f * height, 0.5f * depth));
    }

    public static btCompoundShape getBoxShape(float width, float height, float depth, Vector3 origin) {
        btCompoundShape collistionShape = new btCompoundShape();
        collistionShape.addChildShape(new Matrix4().translate(origin), new btBoxShape(new Vector3(0.5f * width, 0.5f * height, 0.5f * depth)));
        return collistionShape;
    }

    public static btCollisionShape getCollisionShape(String shapeName, float size) {
        btCollisionShape shape = null;
        if (shapeName == null) {
            return null;
        }
        switch (shapeName) {
            case "box":
                shape = getCubeShape(size);
                break;
            case "sphere":
                shape = getSphereShape(size);
                break;
            case "cone":
                shape = getConeShape(size);
                break;
            case "capsule":
                shape = getCapsuleShape(size);
                break;
            case "cylinder":
                shape = getCylinderShape(size);
                break;
            case "tile":
                shape = getTileShape(size);
                break;
        }
        return shape;
    }

    public static btCollisionShape getTreeCollisionShape(String treeName) {
        if (treeName == null) {
            return null;
        }
        btCompoundShape collistionShape = new btCompoundShape();
        switch (treeName) {
            case "bigtooth_aspen":
                collistionShape.addChildShape(new Matrix4().translate(0, 5, 0), new btCylinderShape(new Vector3(0.25f, 5, 0.25f)));
                collistionShape.addChildShape(new Matrix4().translate(0, 15, 0), new btSphereShape(5));
                break;
            case "black_spruce":
                collistionShape.addChildShape(new Matrix4().translate(0, 1f, 0), new btCylinderShape(new Vector3(0.175f, 1f, 0.175f)));
                collistionShape.addChildShape(new Matrix4().translate(0, 6, 0), new btConeShape(2.5f, 8));
                break;
            case "river_burch":
                collistionShape.addChildShape(new Matrix4().translate(0, 2f, -0.95f).rotate(new Quaternion(0.000000f, 0.843391f, 0.537300f, 0.000000f)).rotate(Vector3.X, 90), new btCylinderShape(new Vector3(0.25f, 2f, 0.25f)));
                collistionShape.addChildShape(new Matrix4().translate(0.55f, 2f, 0.55f).rotate(new Quaternion(0.079459f, 0.603553f, 0.786566f, 0.103553f)).rotate(Vector3.X, 90), new btCylinderShape(new Vector3(0.25f, 2f, 0.25f)));
                collistionShape.addChildShape(new Matrix4().translate(-0.35f, 2f, 0.55f).rotate(new Quaternion(-0.053057f, 0.606445f, 0.790334f, -0.069145f)).rotate(Vector3.X, 90), new btCylinderShape(new Vector3(0.25f, 2f, 0.25f)));
                collistionShape.addChildShape(new Matrix4().translate(0, 7.5f, -3.5f), new btSphereShape(6f));
                collistionShape.addChildShape(new Matrix4().translate(2, 7.5f, 2f), new btSphereShape(6f));
                collistionShape.addChildShape(new Matrix4().translate(-1.6f, 7.5f, 2f), new btSphereShape(6f));
                break;
        }

        return collistionShape;
    }

    public static btCollisionShape getConcaveShape() {
        float halfWidth = Global.planeRange;
        float halfHeight = 1.25f / 2f;
        float halfDepth = 5;
        
        float tipLength = (Global.platformLength/2f - Global.planeRange);
        float tipHeight = Global.tipHeight;

        btCompoundShape collistionShape = new btCompoundShape();
        collistionShape.addChildShape(new Matrix4(), CollisionShapeFactory.getBoxShape(halfWidth * 2f, halfHeight * 2f, halfDepth * 2f));
        btConvexHullShape right = new btConvexHullShape();
        right.addPoint(new Vector3(halfWidth, halfHeight, halfDepth));
        right.addPoint(new Vector3(halfWidth + tipLength, halfHeight + tipHeight, halfDepth));
        right.addPoint(new Vector3(halfWidth + tipLength, -halfHeight - tipHeight, halfDepth));
        right.addPoint(new Vector3(halfWidth, -halfHeight, halfDepth));
        
        right.addPoint(new Vector3(halfWidth, halfHeight, -halfDepth));
        right.addPoint(new Vector3(halfWidth + tipLength, halfHeight + tipHeight, -halfDepth));
        right.addPoint(new Vector3(halfWidth + tipLength, -halfHeight - tipHeight, -halfDepth));
        right.addPoint(new Vector3(halfWidth, -halfHeight, -halfDepth));
        
        btConvexHullShape left = new btConvexHullShape();
        left.addPoint(new Vector3(-halfWidth, halfHeight, halfDepth));
        left.addPoint(new Vector3(-halfWidth - tipLength, halfHeight + tipHeight, halfDepth));
        left.addPoint(new Vector3(-halfWidth - tipLength, -halfHeight - tipHeight, halfDepth));
        left.addPoint(new Vector3(-halfWidth, -halfHeight, halfDepth));
        
        left.addPoint(new Vector3(-halfWidth, halfHeight, -halfDepth));
        left.addPoint(new Vector3(-halfWidth - tipLength, halfHeight + tipHeight, -halfDepth));
        left.addPoint(new Vector3(-halfWidth -tipLength, -halfHeight - tipHeight, -halfDepth));
        left.addPoint(new Vector3(-halfWidth, -halfHeight, -halfDepth));
        
//        btShapeHull f = new btShapeHull(right);
//        float margin = right.getMargin();
//        f.buildHull(margin);
//        btConvexHullShape right2 = new btConvexHullShape(f);
        
        collistionShape.addChildShape(new Matrix4(), left);
        collistionShape.addChildShape(new Matrix4(), right);

        return collistionShape;
    }

}
