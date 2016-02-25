package com.blackboxgaming.engine.collision.contactlistener;

import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.components.Damage;
import com.blackboxgaming.engine.components.Health;

/**
 *
 * @author Adrian
 */
public class BallContactListener extends ContactListener {

    @Override
    public void onContactStarted(int userValue0, boolean match0, int userValue1, boolean match1) {
        if (Engine.entityManager.get(userValue0).has(Damage.class)) {
            if (Engine.entityManager.get(userValue1).has(Health.class)) {
                Engine.entityManager.get(userValue1).get(Health.class).currentHealth -= Engine.entityManager.get(userValue0).get(Damage.class).damage;
            }
        } else if (Engine.entityManager.get(userValue1).has(Damage.class)) {
            if (Engine.entityManager.get(userValue0).has(Health.class)) {
                Engine.entityManager.get(userValue0).get(Health.class).currentHealth -= Engine.entityManager.get(userValue1).get(Damage.class).damage;
            }
        }
    }

}
