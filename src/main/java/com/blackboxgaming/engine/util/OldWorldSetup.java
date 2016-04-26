package com.blackboxgaming.engine.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.bullet.collision.Collision;
import com.badlogic.gdx.physics.bullet.collision.btBvhTriangleMeshShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject.CollisionFlags;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btCompoundShape;
import com.badlogic.gdx.physics.bullet.collision.btMultimaterialTriangleMeshShape;
import com.badlogic.gdx.physics.bullet.collision.btScaledBvhTriangleMeshShape;
import com.badlogic.gdx.physics.bullet.collision.btStaticPlaneShape;
import com.badlogic.gdx.utils.Array;
import com.blackboxgaming.engine.*;
import com.blackboxgaming.engine.components.*;
import com.blackboxgaming.engine.factories.*;
import com.blackboxgaming.engine.global.constants.Constants;
import com.blackboxgaming.engine.systems.ParentChildSystem;
import com.blackboxgaming.engine.systems.PhysicsSystem;
import com.blackboxgaming.engine.util.Global;
import com.blackboxgaming.engine.util.Randomizer;

/**
 *
 * @author Adrian
 */
@Deprecated
public class OldWorldSetup {

    private static Entity createHat(Entity parent, Vector3 relativePositionToParent) {
        Entity entity = new Entity();
        entity.add(new Transform());
        entity.add(new Model(ModelFactory.getConeModel(0.5f)));
        entity.add(new Child(parent, relativePositionToParent));
        Engine.entityManager.add(entity);
        return entity;
    }

    private static Entity createTorsoModel(Entity parent, Vector3 relativePositionToParent) {
        Entity entity = new Entity();
        entity.add(new Transform());
        entity.add(new Model(ModelFactory.getBoxModel(1.1f, 0.6f, 1.1f), Color.GREEN));
        entity.add(new Child(parent, relativePositionToParent));
        Engine.entityManager.add(entity);
        return entity;
    }

    private static Entity createWeapon(Entity parent, int weaponType, Vector3 relativePositionToParent) {
        Entity entity = new Entity();
        entity.add(new Transform());
        entity.add(new Model(ModelFactory.getBoxModel(1.5f, 0.125f, 0.125f)));
        entity.add(WeaponFactory.getWeapon(weaponType, new Matrix4().setToTranslation(new Vector3(0.75f, 0, 0))));
        entity.add(new Child(parent, relativePositionToParent));
        Engine.entityManager.add(entity);
        return entity;
    }

    public static void addMainCharacter(float x, float y, float z) {
        Entity entity = new Entity();
//        entity.add(new Transform(0, 0.5f, 0));
        entity.add(new Transform(x, y, z));
        entity.add(new Velocity(0f, 0f, 0f));
//        entity.get(Velocity.class).trnFlag = true;
        entity.add(new Speed(8, Global.angularSpeed, 2));
        entity.add(new Model(ModelFactory.getCubeModel(1f), Color.ORANGE));
        entity.add(new Puppet());
        entity.add(new Health(100));
        entity.add(new OrbitCameraFocus());
//        entity.add(new HUDItem("isFlying", "", "", true));
        entity.add(new Shadow());
        entity.add(new DoubleBody());
        entity.add(new Physics(CollisionShapeFactory.getCubeShape(1), 25, btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK, Constants.OBJECT_FLAG, Constants.ALL_FLAG, Collision.ACTIVE_TAG));

        Parent parent = new Parent(true);
        parent.add(createHat(entity, new Vector3(0, 1f, -0.25f)));
        parent.add(createHat(entity, new Vector3(0, 1f, 0.25f)));
//        parent.add(createWeapon(entity, WeaponFactory.WEAPON_PROJECTILE, new Vector3(0, 0, -0.5f)));
//        parent.add(createWeapon(entity, WeaponFactory.WEAPON_GUN, new Vector3(0, 0.15f, 0.5f)));
        parent.add(createWeapon(entity, WeaponFactory.WEAPON_PLASMA, new Vector3(0, 0.15f, 0.5f)));
//        parent.add(createWeapon(entity, WeaponFactory.WEAPON_GUN, new Vector3(0, -0.15f, 0.5f)));
//        parent.add(createWeapon(entity, WeaponFactory.WEAPON_GUN, new Vector3(0, 0.15f, 0.8f)));
//        parent.add(createWeapon(entity, WeaponFactory.WEAPON_GUN, new Vector3(0, -0.15f, 0.8f)));

        parent.add(createWeapon(entity, WeaponFactory.WEAPON_PLASMA, new Vector3(0, 0.15f, -0.5f)));
//        parent.add(createWeapon(entity, WeaponFactory.WEAPON_GUN, new Vector3(0, -0.15f, -0.5f)));
//        parent.add(createWeapon(entity, WeaponFactory.WEAPON_GUN, new Vector3(0, 0.15f, -0.8f)));
//        parent.add(createWeapon(entity, WeaponFactory.WEAPON_GUN, new Vector3(0, -0.15f, -0.8f)));
        entity.add(parent);

        Engine.entityManager.add(entity);
        Global.mainCharacter = entity;
        System.out.println("MainCharacter " + entity);
    }

    public static void addMainCharacterDoubleBody(float x, float y, float z) {
        Entity entity = new Entity();
        entity.add(new Transform(x, y, z));
        entity.add(new Velocity(0f, 0f, 0f));
        entity.add(new Speed(8, Global.angularSpeed, 2));
//        entity.add(new Model(ModelFactory.getCubeModel(1f), Color.RED));
        entity.add(new Puppet());
        entity.add(new Health(100));
        entity.add(new OrbitCameraFocus());
//        entity.add(new HUDItem("isFlying", "", "", true));
        entity.add(new Shadow());
//        entity.add(new Physics2D(CollisionShapeFactory2D.getBoxShape(0.5f, 0.5f), BodyDef.BodyType.DynamicBody, 1, entity.get(Transform.class).transform, false));
//        entity.add(new Physics(CollisionShapeFactory.getCubeShape(1), 25, btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK, Constants.OBJECT_FLAG, Constants.ALL_FLAG, Collision.ACTIVE_TAG));

        Parent parent = new Parent(true);
        // torso, for some reason, torso can't have a model.
        Entity torso = new Entity();
        torso.add(new Child(entity, new Vector3(0, 0.3f, 0), true));
        torso.add(new Transform());
//        torso.add(new Model(ModelFactory.getBoxModel(1.1f, 0.7f, 1.1f), Color.BLUE));

        Parent torsoParent = new Parent();
//        torsoParent.add(createTorsoModel(torso, new Vector3(0, 0.0f, 0)));
//        torsoParent.add(createHat(torso, new Vector3(0, 0.5f, -0.25f)));
//        torsoParent.add(createHat(torso, new Vector3(0, 0.5f, 0.25f)));
////        torsoParent.add(createWeapon(torso, WeaponFactory.WEAPON_PLASMA, new Vector3(0, 0.0f, 0.5f)));
////        torsoParent.add(createWeapon(torso, WeaponFactory.WEAPON_PLASMA, new Vector3(0, 0.0f, -0.5f)));
//        torsoParent.add(createWeapon(torso, WeaponFactory.WEAPON_PLASMA, new Vector3(0.1f, 0.0f, 0f)));

        torso.add(torsoParent);
        parent.add(torso);
        entity.add(parent);

        entity.add(new Torso(torso));

        Engine.entityManager.add(torso);
        Engine.entityManager.add(entity);
//        if(Engine.systemManager.has(ParentChildSystem.class)){
//            Engine.systemManager.get(ParentChildSystem.class).reverse();
//        }
        Global.mainCharacter = entity;
        System.out.println("MainCharacter " + entity);
    }

    public static void addMap(float width, float height) {
        Entity grid = new Entity();
        grid.add(new Transform(0, 0, 0));
        grid.add(new Model(Engine.assetManager.getModel("ground/ground"), true));
//        grid.add(new Physics(CollisionShapeFactory.getBoxShape(width, 1, width), 0, btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT, Constants.GROUND_FLAG, (short) 0, Collision.DISABLE_DEACTIVATION));
        Engine.entityManager.add(grid);
        Global.CONTACT_GROUP_GROUND.add(grid.id);

        Entity wall;
        // x
        wall = new Entity();
        wall.add(new Transform(width / 2f + height / 2f, height / 2f, 0));
//        wall.add(new Model(ModelFactory.getBoxModel(height, height, width), Color.BLUE));
//        wall.add(new Physics(CollisionShapeFactory.getBoxShape(height, height, width), 0, btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT, Constants.GROUND_FLAG, (short) 0, Collision.DISABLE_DEACTIVATION));
        wall
                .add(new Physics2D(CollisionShapeFactory2D.getBoxShape(height / 2f, width / 2f), BodyType.StaticBody, 0, wall.get(Transform.class
                                ).transform, true));
        Engine.entityManager.add(wall);

        Global.PHYSICS_CONTACT_GROUP_WALL.add(wall.id);
        // -x
        wall = new Entity();

        wall.add(
                new Transform(-width / 2f - height / 2f, height / 2f, 0));
//        wall.add(new Model(ModelFactory.getBoxModel(height, height, width), Color.BLUE));
        wall.add(
                new Physics2D(CollisionShapeFactory2D.getBoxShape(height / 2f, width / 2f), BodyType.StaticBody, 0, wall.get(Transform.class
                        ).transform, true));
//        wall.add(new Physics(CollisionShapeFactory.getBoxShape(height, height, width), 0, btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT, Constants.GROUND_FLAG, (short) 0, Collision.DISABLE_DEACTIVATION));
        Engine.entityManager.add(wall);

        Global.PHYSICS_CONTACT_GROUP_WALL.add(wall.id);

        // z
        wall = new Entity();

        wall.add(
                new Transform(0, height / 2f, width / 2f + height / 2f));
//        wall.add(new Model(ModelFactory.getBoxModel(width, height, height), Color.BLUE));
        wall.add(
                new Physics2D(CollisionShapeFactory2D.getBoxShape(width / 2f, height / 2f), BodyType.StaticBody, 0, wall.get(Transform.class
                        ).transform, true));
//        wall.add(new Physics(CollisionShapeFactory.getBoxShape(width, height, height), 0, btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT, Constants.GROUND_FLAG, (short) 0, Collision.DISABLE_DEACTIVATION));
        Engine.entityManager.add(wall);

        Global.PHYSICS_CONTACT_GROUP_WALL.add(wall.id);
        // -z
        wall = new Entity();

        wall.add(
                new Transform(0, height / 2f, -width / 2f - height / 2f));
//        wall.add(new Model(ModelFactory.getBoxModel(width, height, height), Color.BLUE));
        wall.add(
                new Physics2D(CollisionShapeFactory2D.getBoxShape(width / 2f, height / 2f), BodyType.StaticBody, 0, wall.get(Transform.class
                        ).transform, true));
//        wall.add(new Physics(CollisionShapeFactory.getBoxShape(width, height, height), 0, btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT, Constants.GROUND_FLAG, (short) 0, Collision.DISABLE_DEACTIVATION));
        Engine.entityManager.add(wall);

        Global.PHYSICS_CONTACT_GROUP_WALL.add(wall.id);
    }

    public static void setCamera(float x, float y, float z) {
        Global.getCamera().position.set(x, y, z);
        Global.getCamera().lookAt(0, 0, 0);
        Global.getCamera().up.set(Vector3.Y);
        Global.getCamera().update();
    }

    public static void addWeaponSwitcher(float x, float y, float z) {
        Entity switcher = new Entity();
        switcher.add(new Transform(x, y, z));
        switcher.add(new Model(ModelFactory.getCubeModel(1), Color.YELLOW));
        switcher.add(new Shadow());
        switcher.add(new Physics(CollisionShapeFactory.getCubeShape(1), 0, btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT, Constants.GROUND_FLAG, (short) 0, Collision.DISABLE_DEACTIVATION));
        Engine.entityManager.add(switcher);
        Global.PHYSICS_CONTACT_GROUP_OBSTACLE.add(switcher.id);
        Global.weaponSwitcherID = switcher.id;
    }

    public static void addCameraFocus() {
        Entity cameraFocus = new Entity();
        cameraFocus.add(new Transform());
        cameraFocus.add(new OrbitCameraFocus());
        Engine.entityManager.add(cameraFocus);
    }

    public static void addWablyPlane() {
        Global.planeRange = Global.platformLength * Global.tipRatio;
        Vector3 centerOffset = new Vector3(0, Global.rotationCenter, 0);

        // center/parent
        Entity plane = new Entity();
        plane.add(new Transform(0, 0, 0, 0, 0, 0));
        plane.add(new Puppet());
        plane.add(new Velocity());
        plane.add(new Model(ModelFactory.getSphereModel(0.5f)));
        plane.add(new Speed(8, Global.rotationSpeed, 2));

        // platform
        Parent parent = new Parent(true);
        Entity planeModel = new Entity();
        planeModel.add(new Child(plane, centerOffset, true));
        planeModel.add(new Transform());
        planeModel.add(new Model(ModelFactory.getBoxModel(Global.platformLength, 1.25f, 10), Color.valueOf("4b8934")));
//        planeModel.add(new Model(ModelFactory.getConcaveModel(), Color.valueOf("4b8934")));
//        planeModel.add(new Physics(CollisionShapeFactory.getBoxShape(length, 1.25f, 10), 0, btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT, Constants.GROUND_FLAG, (short) 0, Collision.DISABLE_DEACTIVATION));
        planeModel.add(new Physics(CollisionShapeFactory.getConcaveShape(), 0, btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT, Constants.GROUND_FLAG, (short) 0, Collision.DISABLE_DEACTIVATION));
        planeModel.get(Physics.class).body.setRestitution(0.1f);
        parent.add(planeModel);

        // second platform
        Entity secondPlaneModel = new Entity();
        secondPlaneModel = new Entity();
        secondPlaneModel.add(new Child(plane, centerOffset, true));
        secondPlaneModel.add(new Transform());
        secondPlaneModel.add(new Model(ModelFactory.getBoxModel(Global.planeRange * 2f, 1.65f, 10.35f), Color.valueOf("9bb300")));
        parent.add(secondPlaneModel);

        plane.add(parent);

        Engine.entityManager.add(secondPlaneModel);
        Engine.entityManager.add(planeModel);
        Engine.entityManager.add(plane);
        Global.plane = plane.id;
        Global.planeId = planeModel.id;
        Global.planeId2 = secondPlaneModel.id;
    }

    public static void addWablyGlass() {
        Entity glass = new Entity();
        glass.add(new Transform(0, 20, 0, 0, 90, 0));
        glass.add(new RestrictMotion());
        glass.add(new Sound("thud.mp3"));
        glass.add(new Original(new Transform(0, 3, 0, 0, 90, 0)));
        if (MathUtils.randomBoolean()) {
            glass.add(new Model(ModelFactory.getCylinderModel(1, 10, 30), Color.valueOf("009bb3")));
            glass.add(new Physics(CollisionShapeFactory.getCylinderShape(1, 10), Global.mass, btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK, Constants.OBJECT_FLAG, Constants.ALL_FLAG, Collision.DISABLE_DEACTIVATION));
        } else {
            glass.add(new Model(ModelFactory.getSphereModel(5, 25), Color.valueOf("009bb3")));
            glass.add(new Physics(CollisionShapeFactory.getSphereShape(5), Global.mass, btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK, Constants.OBJECT_FLAG, Constants.ALL_FLAG, Collision.DISABLE_DEACTIVATION));
        }
        System.out.println(Global.mass);

        glass.get(Physics.class).body.setLinearFactor(new Vector3(1, 1, 0));
        glass.get(Physics.class).body.setAngularFactor(new Vector3(0, 0, 1));
        System.out.println(glass.get(Physics.class).body.getRestitution());
        glass.get(Physics.class).body.setRestitution(0.1f);
//        glass.get(Physics.class).body.setCcdMotionThreshold(0.05f);
//        glass.get(Physics.class).body.setCcdSweptSphereRadius(0.01f);

        Engine.entityManager.add(glass);
        Global.glassId = glass.id;
    }

    public static void createBox(float width, float length, float height) {
        Entity box = new Entity();
        box.add(new Transform(0, -0.3f, 0, 0, 0, 0));
        box.add(new Model(Engine.assetManager.getModel("boxes/box2min")));
        box.get(Model.class).modelInstance.model.materials.removeIndex(0);
        box.get(Model.class).modelInstance.materials.removeIndex(0);
//        box.add(new Shadow());
//        box.add(new Physics(getBoxCollisionPlanes(width, length, height), 0, CollisionFlags.CF_STATIC_OBJECT, Constants.GROUND_FLAG, (short) 0, Collision.ACTIVE_TAG));
        box.add(new Physics(getBoxCollisionTriangles(Engine.assetManager.getModel("boxes/box2physics").meshParts), 0, CollisionFlags.CF_STATIC_OBJECT, Constants.GROUND_FLAG, (short) 0, Collision.ACTIVE_TAG));
        box.get(Physics.class).body.setRestitution(0.687f);
        box.get(Physics.class).body.setFriction(0.8f);
        box.get(Physics.class).body.setRollingFriction(0.25f);
        Engine.entityManager.add(box);
    }

    private static btCollisionShape getBoxCollisionObject(float width, float length, float height) {
        float thickness = 20;
        btCompoundShape collision = new btCompoundShape();
        // bottom & top
        collision.addChildShape(new Matrix4().translate(0, -height / 2f - thickness / 2f, 0), CollisionShapeFactory.getBoxShape(length + 2f * thickness, thickness, width + 2f * thickness));
        collision.addChildShape(new Matrix4().translate(0, height / 2f + thickness / 2f, 0), CollisionShapeFactory.getBoxShape(length + 2f * thickness, thickness, width + 2f * thickness));

        // front & back
        collision.addChildShape(new Matrix4().translate(length / 2f + thickness / 2f, 0, 0), CollisionShapeFactory.getBoxShape(thickness, height, width + 2f * thickness));
        collision.addChildShape(new Matrix4().translate(-length / 2f - thickness / 2f, 0, 0), CollisionShapeFactory.getBoxShape(thickness, height, width + 2f * thickness));

        // left & right
        collision.addChildShape(new Matrix4().translate(0, 0, -width / 2f - thickness / 2f), CollisionShapeFactory.getBoxShape(length, height, thickness));
        collision.addChildShape(new Matrix4().translate(0, 0, +width / 2f + thickness / 2f), CollisionShapeFactory.getBoxShape(length, height, thickness));

        return collision;
    }

    private static btCollisionShape getBoxCollisionPlanes(float width, float length, float height) {
        btCompoundShape collision = new btCompoundShape();

        collision.addChildShape(new Matrix4(), new btStaticPlaneShape(Vector3.Y, 0));
        collision.addChildShape(new Matrix4().rotate(Vector3.X, 180).translate(0, -height, 0), new btStaticPlaneShape(Vector3.Y, 0));
        collision.addChildShape(new Matrix4().rotate(Vector3.X, 90).translate(0, -width / 2f, 0), new btStaticPlaneShape(Vector3.Y, 0));
        collision.addChildShape(new Matrix4().rotate(Vector3.X, -90).translate(0, -width / 2f, 0), new btStaticPlaneShape(Vector3.Y, 0));
        collision.addChildShape(new Matrix4().rotate(Vector3.Z, 90).translate(0, -length / 2f, 0), new btStaticPlaneShape(Vector3.Y, 0));
        collision.addChildShape(new Matrix4().rotate(Vector3.Z, -90).translate(0, -length / 2f, 0), new btStaticPlaneShape(Vector3.Y, 0));

        return collision;
    }

    private static btBvhTriangleMeshShape t;

    private static btCollisionShape getBoxCollisionTriangles(Array<MeshPart> meshParts) {
        btCompoundShape collision = new btCompoundShape();
        t = new btBvhTriangleMeshShape(meshParts);
//        btScaledBvhTriangleMeshShape s = new btScaledBvhTriangleMeshShape(t, new Vector3(Global.boxLength / 2f, Global.boxWidth / 2f, Global.boxDepth/2f));
        btScaledBvhTriangleMeshShape s = new btScaledBvhTriangleMeshShape(t, new Vector3(3f, 3f, 2.955f));

        collision.addChildShape(new Matrix4().rotate(Vector3.X, -90), s);

        return collision;
    }

    public static void createRamp() {
        Entity ramp = new Entity();
        ramp.add(new Transform(0, 0.25f, -3.75f, 0, 0, 20));
        ramp.add(new Model(ModelFactory.getTileModel(2f), Randomizer.getRandomColor()));
//        ramp.add(new Shadow());
        ramp.add(new Physics(CollisionShapeFactory.getTileShape(2f), 0, CollisionFlags.CF_STATIC_OBJECT, Constants.GROUND_FLAG, (short) 0, Collision.ACTIVE_TAG));
        ramp.get(Physics.class).body.setRestitution(0.687f);
        Engine.entityManager.add(ramp);
    }

    public static void createXZPlane(int offset) {
        Entity plane = new Entity();
        plane.add(new Transform(0, offset, 0));
        plane.add(new Physics(new btStaticPlaneShape(Vector3.Y, 0), 0, CollisionFlags.CF_STATIC_OBJECT, Constants.GROUND_FLAG, (short) 0, Collision.ACTIVE_TAG));
        Engine.entityManager.add(plane);
    }

}
