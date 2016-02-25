package com.blackboxgaming.engine.collision.contactlistener;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Damage;
import com.blackboxgaming.engine.components.Enemy;
import com.blackboxgaming.engine.components.Health;
import com.blackboxgaming.engine.components.Transform;
import com.blackboxgaming.engine.components.ai.Follow;
import com.blackboxgaming.engine.util.Global;

/**
 *
 * @author Adrian
 */
public class ContactListener2D implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        changeToRandomDirection(contact);
        resolveCollisionWithBullet(contact);
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold mnfld) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse ci) {
    }

    private void changeToRandomDirection(Contact contact) {
        Entity enemy = null;
        if (Engine.entityManager.get((int) contact.getFixtureA().getBody().getUserData()).has(Enemy.class)) {
            enemy = Engine.entityManager.get((int) contact.getFixtureA().getBody().getUserData());
        } else if (Engine.entityManager.get((int) contact.getFixtureB().getBody().getUserData()).has(Enemy.class)) {
            enemy = Engine.entityManager.get((int) contact.getFixtureB().getBody().getUserData());
        }
        if (enemy != null) {
            if (enemy.has(Follow.class)) {
                return;
            }
            float angle = MathUtils.random(90, 270);
            enemy.get(Transform.class).transform.rotate(Vector3.Y, angle);
            enemy.get(Transform.class).changedDirection2D = true;
        }
    }

    private void resolveCollisionWithBullet(Contact contact) {
        if (Global.CANTACT_GROUP_PLASMA.contains((int) contact.getFixtureA().getBody().getUserData())) {
            // A is bullet
            Entity plasma = Engine.entityManager.get((int) contact.getFixtureA().getBody().getUserData());
            if (Engine.entityManager.get((int) contact.getFixtureB().getBody().getUserData()).has(Health.class)) {
                Engine.entityManager.get((int) contact.getFixtureB().getBody().getUserData()).get(Health.class).currentHealth -= plasma.get(Damage.class).damage;
            }
            Global.CANTACT_GROUP_PLASMA.remove((Integer) (int) contact.getFixtureA().getBody().getUserData());
            Engine.garbageManager.markForDeletion(plasma);
        } else if (Global.CANTACT_GROUP_PLASMA.contains((int) contact.getFixtureB().getBody().getUserData())) {
            // B is bullet
            Entity plasma = Engine.entityManager.get((int) contact.getFixtureB().getBody().getUserData());
            if (Engine.entityManager.get((int) contact.getFixtureA().getBody().getUserData()).has(Health.class)) {
                Engine.entityManager.get((int) contact.getFixtureA().getBody().getUserData()).get(Health.class).currentHealth -= plasma.get(Damage.class).damage;
            }
            Global.CANTACT_GROUP_PLASMA.remove((Integer) (int) contact.getFixtureB().getBody().getUserData());
            Engine.garbageManager.markForDeletion(plasma);
        }
    }

}
