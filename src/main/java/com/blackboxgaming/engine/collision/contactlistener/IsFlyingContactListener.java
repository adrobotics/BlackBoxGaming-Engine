package com.blackboxgaming.engine.collision.contactlistener;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Damage;
import com.blackboxgaming.engine.components.Enemy;
import com.blackboxgaming.engine.components.Health;
import com.blackboxgaming.engine.components.Parent;
import com.blackboxgaming.engine.components.Physics;
import com.blackboxgaming.engine.components.Puppet;
import com.blackboxgaming.engine.components.Transform;
import com.blackboxgaming.engine.components.Weapon;
import com.blackboxgaming.engine.components.ai.Follow;
import com.blackboxgaming.engine.util.Global;

/**
 *
 * @author Adrian
 */
public class IsFlyingContactListener extends ContactListener {

    private final Vector3 v = new Vector3();
    private final Quaternion q = new Quaternion();

    @Override
    public void onContactStarted(int userValue0, boolean match0, int userValue1, boolean match1) {
        if (Global.CONTACT_GROUP_GROUND.contains((Integer) userValue0) && Engine.entityManager.get(userValue1).has(Puppet.class)) {
            Engine.entityManager.get(userValue1).get(Puppet.class).isFlying = false;
        } else if (Global.CONTACT_GROUP_GROUND.contains((Integer) userValue1) && Engine.entityManager.get(userValue0).has(Puppet.class)) {
            Engine.entityManager.get(userValue0).get(Puppet.class).isFlying = false;
        }

        if (!Global.CONTACT_GROUP_GROUND.contains((Integer) userValue0) && !Global.CONTACT_GROUP_GROUND.contains((Integer) userValue1)) {
            if (Global.CANTACT_GROUP_PLASMA.contains(userValue0)) {
                Entity plasma = Engine.entityManager.get(userValue0);
                if (Engine.entityManager.get(userValue1).has(Health.class)) {
                    Engine.entityManager.get(userValue1).get(Health.class).currentHealth -= plasma.get(Damage.class).damage;
                }
                Global.CANTACT_GROUP_PLASMA.remove((Integer) userValue0);
                Engine.garbageManager.markForDeletion(plasma);
            } else if (Global.CANTACT_GROUP_PLASMA.contains(userValue1)) {
                Entity plasma = Engine.entityManager.get(userValue1);
                if (Engine.entityManager.get(userValue0).has(Health.class)) {
                    Engine.entityManager.get(userValue0).get(Health.class).currentHealth -= plasma.get(Damage.class).damage;
                }
                Global.CANTACT_GROUP_PLASMA.remove((Integer) userValue1);
                Engine.garbageManager.markForDeletion(plasma);
            }
        }

        if (Global.weaponSwitcherID == userValue0 && Global.mainCharacter.id == userValue1) {
            Entity character = Engine.entityManager.get(userValue1);
            if (character.has(Parent.class)) {
                Parent parent = character.get(Parent.class);
                for (Entity child : parent.children) {
                    if (child.has(Weapon.class)) {

                    }
                }
            }
        } else if (Global.weaponSwitcherID == userValue1 && Global.mainCharacter.id == userValue0) {
            Entity character = Engine.entityManager.get(userValue0);
            if (character.has(Parent.class)) {
                Parent parent = character.get(Parent.class);
                for (Entity child : parent.children) {
                    if (child.has(Weapon.class)) {

                    }
                }
            }
        }
    }

    @Override
    public void onContactProcessed(int userValue0, int userValue1) {
        Entity enemy = null;
        if (Engine.entityManager.get(userValue1).has(Enemy.class) && !Global.CONTACT_GROUP_GROUND.contains((Integer) userValue0)) {
            enemy = Engine.entityManager.get(userValue1);
        } else if (Engine.entityManager.get(userValue0).has(Enemy.class) && !Global.CONTACT_GROUP_GROUND.contains((Integer) userValue1)) {
            enemy = Engine.entityManager.get(userValue0);
        }
        if (enemy != null) {
            if(enemy.has(Follow.class)){
                return;
            }
            float angle = MathUtils.random(360);
            enemy.get(Transform.class).transform.rotate(Vector3.Y, angle);
            enemy.get(Physics.class).body.proceedToTransform(enemy.get(Transform.class).transform);
        }
    }

    @Override
    public void onContactEnded(int userValue0, boolean match0, int userValue1, boolean match1) {
        if (Global.CONTACT_GROUP_GROUND.contains((Integer) userValue0) && Engine.entityManager.get(userValue1).has(Puppet.class)) {
            Engine.entityManager.get(userValue1).get(Puppet.class).isFlying = true;
        } else if (Global.CONTACT_GROUP_GROUND.contains((Integer) userValue1) && Engine.entityManager.get(userValue0).has(Puppet.class)) {
            Engine.entityManager.get(userValue0).get(Puppet.class).isFlying = true;
        }
    }

}
