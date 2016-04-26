package com.blackboxgaming.engine.systems.ai;

import com.badlogic.gdx.math.Vector3;
import com.blackboxgaming.engine.components.Transform;
import com.blackboxgaming.engine.components.ai.Follow;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.JustFire;
import com.blackboxgaming.engine.components.Parent;
import com.blackboxgaming.engine.components.Weapon;
import com.blackboxgaming.engine.systems.AbstractSystem;

/**
 *
 * @author Adrian
 */
public class FollowSystem extends AbstractSystem {

    public FollowSystem() {
        requiredComponents.add(Follow.class);
        requiredComponents.add(Transform.class);
    }

    @Override
    public void update(float delta) {
        for (Entity entity : entities) {
            follow(entity, ((Follow) entity.get(Follow.class)).target);
        }
    }

    private void follow(Entity follower, Entity followee) {
        Follow followerComponent = (Follow) follower.get(Follow.class);
        Transform followerP = (Transform) follower.get(Transform.class);
        Transform followeeP = (Transform) followee.get(Transform.class);
        Vector3 followerV3 = new Vector3(followerP.transform.getTranslation(Vector3.Zero));
        Vector3 followeeV3 = new Vector3(followeeP.transform.getTranslation(Vector3.Zero));
        if (followerV3.dst(followeeV3) > followerComponent.distance) {
            followerP.transform.setToLookAt(followeeV3.sub(followerV3).nor().scl(1), Vector3.Y)
                    .inv()
                    .rotate(Vector3.Y, 90)
                    .trn(followerV3);
        } else if (follower.has(Parent.class)) {
            for (Entity child : follower.get(Parent.class).children) {
                if (child.has(Weapon.class)) {
                    child.add(JustFire.instance);
                }
            }
        }
    }
}
