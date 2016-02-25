package com.blackboxgaming.engine.systems;

import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Damage;
import com.blackboxgaming.engine.components.Death;
import com.blackboxgaming.engine.components.Health;
import com.blackboxgaming.engine.components.JustFire;
import com.blackboxgaming.engine.components.Model;
import com.blackboxgaming.engine.components.Physics2D;
import com.blackboxgaming.engine.components.Transform;
import com.blackboxgaming.engine.components.Velocity;
import com.blackboxgaming.engine.components.Weapon;
import com.blackboxgaming.engine.factories.CollisionShapeFactory2D;
import com.blackboxgaming.engine.factories.ModelFactory;
import static com.blackboxgaming.engine.factories.WeaponFactory.*;
import com.blackboxgaming.engine.input.PlayerKeyListener;
import com.blackboxgaming.engine.util.Global;
import com.blackboxgaming.engine.util.VUtil;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adrian
 */
public class WeaponSystem implements ISystem, Disposable {

    private final List<Entity> entities = new ArrayList();
    private final Vector3 from = new Vector3();
    private final Vector2 from2D = new Vector2();
    private final Vector3 to = new Vector3();
    private final Vector2 to2D = new Vector2();
    private final Vector3 hitNormal = new Vector3();
    private final Vector3 hitPoint = new Vector3();
    private final Matrix4 splashTransform = new Matrix4();
    private final Vector2 splashAngleAroundY = new Vector2();
    private ClosestRayResultCallback callback;
    private Matrix4 parentTransform;
    private Matrix4 nozzleRelativeToWeapon;
    private Matrix4 shotOriginAbsolute;
    private boolean firing;
    private final MeleeCalback2D meleeCallback2D;

    public WeaponSystem() {
        this.meleeCallback2D = new MeleeCalback2D();
        if (Engine.systemManager.has(PhysicsSystem.class)) {
            callback = new ClosestRayResultCallback(from, to);
        }
    }

    @Override
    public void add(Entity entity) {
        if (!entities.contains(entity)) {
            entities.add(entity);
        }
    }

    @Override
    public void remove(Entity entity) {
        entities.remove(entity);
    }

    @Override
    public void update(float delta) {
        firing = PlayerKeyListener.space;
        for (Entity entity : entities) {
            if (firing || entity.has(JustFire.class)) {
                if (entity.has(JustFire.class)) {
                    entity.remove(JustFire.class);
                }
                Weapon weapon = entity.get(Weapon.class);
                if (weapon.lastFired + (long) weapon.fireRate <= System.currentTimeMillis()) {
                    weapon.lastFired = System.currentTimeMillis();
                    parentTransform = entity.get(Transform.class).transform;
                    nozzleRelativeToWeapon = entity.get(Weapon.class).nozzleRelativeToWeapon;
                    shotOriginAbsolute = parentTransform.cpy().mul(nozzleRelativeToWeapon);
                    resolveShot(weapon.weaponType, weapon, shotOriginAbsolute);
                }
            }
        }
    }

    private void resolveShot(int type, Weapon weapon, Matrix4 origin) {
        switch (type) {
            case WEAPON_GUN:
                shootGun(origin, weapon);
                break;
            case WEAPON_LASER:
                break;
            case WEAPON_PROJECTILE:
                shootProjectile(origin);
                break;
            case WEAPON_PLASMA:
                shootPlasma(origin, weapon);
                break;
            case WEAPON_GRENADE:
                break;
            case WEAPON_MELEE:
                shootMelee(origin, weapon);
                break;
            default:
                System.out.println("Can't resolve shot");
                break;
        }
    }

    private void shootGun(Matrix4 origin, Weapon weapon) {
        // nozzle fire model
        Entity entity;
        entity = new Entity();
        entity.add(new Transform(origin.cpy().translate(0.5f, 0, 0).rotate(Vector3.X, 90).rotate(Vector3.Z, 90)));
        entity.add(weapon.nozzleFireModel);
        entity.add(new Death(50));
        Engine.entityManager.add(entity);

        // shot collision
        from.set(origin.getTranslation(from));
        to.set((origin.translate(weapon.range, 0, 0)).getTranslation(to));
        callback.setRayFromWorld(from);
        callback.setRayToWorld(to);
        callback.setCollisionObject(null);
        callback.setClosestHitFraction(1f);
        Global.getDynamicsWorld().rayTest(from, to, callback);

        // damage on hit
        if (callback.hasHit()) {
            decreaseHealth(Engine.entityManager.get(callback.getCollisionObject().getUserValue()), weapon.damage);
        }
        // hit splash
        if (callback.hasHit()) {
            callback.getHitNormalWorld(hitNormal);
            callback.getHitPointWorld(hitPoint);

            splashTransform.idt();
            splashAngleAroundY.set(hitNormal.x, hitNormal.z);
            splashTransform.rotate(Vector3.Y, -splashAngleAroundY.angle());
            splashTransform.trn(hitPoint);

            entity = new Entity();
            entity.add(new Transform(splashTransform));
            entity.add(weapon.hitSplashModel);
            entity.add(new Death(100));
            Engine.entityManager.add(entity);
        }
    }

    private void shootMelee(Matrix4 origin, Weapon weapon) {
        if (Engine.systemManager.has(PhysicsSystem.class)) {
            // shot collision
            from.set(origin.getTranslation(from));
            to.set((origin.cpy().translate(weapon.range, 0, 0)).getTranslation(to));
            callback.setRayFromWorld(from);
            callback.setRayToWorld(to);
            callback.setCollisionObject(null);
            callback.setClosestHitFraction(1f);
            Global.getDynamicsWorld().rayTest(from, to, callback);

            // damage on hit
            if (callback.hasHit() && callback.getCollisionObject().getUserValue() == Global.mainCharacter.id) {
                // nozzle fire model
                Entity entity;
                entity = new Entity();
                entity.add(new Transform(origin));
                entity.add(weapon.nozzleFireModel);
                entity.add(new Death(100));
                Engine.entityManager.add(entity);
                decreaseHealth(Engine.entityManager.get(callback.getCollisionObject().getUserValue()), weapon.damage);
            }
        } else if (Engine.systemManager.has(PhysicsSystem2D.class)) {
            // 2D physics
            from2D.set(VUtil.toVector2(origin.cpy().translate(-0.5f, 0, 0).getTranslation(from)));
            to2D.set(VUtil.toVector2((origin.cpy().translate(weapon.range, 0, 0)).getTranslation(to)));
            Global.getDynamicsWorld2D().rayCast(meleeCallback2D, from2D, to2D);
            if (meleeCallback2D.hasHit) {
                if ((int) meleeCallback2D.fixture.getBody().getUserData() == Global.mainCharacter.id) {
                    Entity entity;
                    entity = new Entity();
                    entity.add(new Transform(origin));
                    entity.add(weapon.nozzleFireModel);
                    entity.add(new Death(100));
                    Engine.entityManager.add(entity);
                    decreaseHealth(Engine.entityManager.get((int) meleeCallback2D.fixture.getBody().getUserData()), weapon.damage);
                }
                meleeCallback2D.hasHit = false;
            }
        }
    }

    private class MeleeCalback2D implements RayCastCallback {

        public Fixture fixture;
        public boolean hasHit = false;

        @Override
        public float reportRayFixture(Fixture fixture, Vector2 vctr, Vector2 vctr1, float f) {
            this.fixture = fixture;
            hasHit = true;
            return 0;
        }

    }

    private void shootPlasma(Matrix4 origin, Weapon weapon) {
        // plasma
        Entity entity = new Entity();
        entity.add(new Transform(origin.cpy().translate(0.5f, 0, 0)));
        entity.add(new Velocity(weapon.speed, 0, 0));
//        entity.add(new Model(weapon.bulletModel.modelInstance.copy().model, ((ColorAttribute)weapon.bulletModel.modelInstance.materials.get(0).get(ColorAttribute.Diffuse)).color, true));
        entity.add(new Model(weapon.bulletModel.modelInstance.copy().model, ((ColorAttribute) weapon.bulletModel.modelInstance.materials.get(0).get(ColorAttribute.Diffuse)).color, true));
        entity.add(new Death(2000));
        entity.add(new Damage(weapon.damage));
//        entity.add(new Physics(CollisionShapeFactory.getSphereShape(0.5f), 0.5f, btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK, CollisionFlag.BULLET_COLLISION_FLAG, CollisionFlag.ALL_FLAG, Collision.ACTIVE_TAG));
        entity.add(new Physics2D(CollisionShapeFactory2D.getCircleShape(0.25f), BodyDef.BodyType.DynamicBody, 0.5f, entity.get(Transform.class).transform, false));
        Engine.entityManager.add(entity);
        Global.CANTACT_GROUP_PLASMA.add(entity.id);
    }

    private void shootProjectile(Matrix4 origin) {
        Entity bullet = new Entity();
        bullet.add(new Transform(origin));
        bullet.add(new Model(ModelFactory.getSphereModel(0.25f)));
        bullet.add(new Death(1000));
        Engine.entityManager.add(bullet);
    }

    private void decreaseHealth(Entity target, float damage) {
        if (target.has(Health.class)) {
            target.get(Health.class).currentHealth -= damage;
        }
    }

    @Override
    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        Model model;
        for (Entity entity : entities) {
            model = entity.get(Weapon.class).nozzleFireModel;
            if (model != null) {
                model.modelInstance.model.dispose();
                model = null;
            }
        }
        entities.clear();
        callback.dispose();
    }
}
