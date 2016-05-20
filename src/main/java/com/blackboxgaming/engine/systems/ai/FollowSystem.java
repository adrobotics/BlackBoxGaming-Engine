package com.blackboxgaming.engine.systems.ai;

import com.badlogic.gdx.math.Vector3;
import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.components.Transform;
import com.blackboxgaming.engine.components.ai.Follow;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Cell;
import com.blackboxgaming.engine.components.JustFire;
import com.blackboxgaming.engine.components.Parent;
import com.blackboxgaming.engine.components.Velocity;
import com.blackboxgaming.engine.components.Weapon;
import com.blackboxgaming.engine.systems.AbstractSystem;
import com.blackboxgaming.engine.util.Randomizer;
import java.util.List;

/**
 * Rotates entities that have a {@link Follow} component towards their target.
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
            if (!Engine.entityManager.has(entity.get(Follow.class).target)) {
                // fing new target
                List<Entity> cells = Engine.entityManager.get(Cell.class);
                if (cells.size() > 1) {
                    Entity newTarget;
                    do {
                        newTarget = cells.get(Randomizer.getRandomInteger(cells.size()));
                    } while (newTarget.equals(entity)); // so it won't select itself
                    entity.get(Follow.class).target = newTarget;
                }else{
                    entity.get(Velocity.class).linear.setZero();
                }
            }
            follow(entity, entity.get(Follow.class).target);
        }
    }

    private void follow(Entity follower, Entity target) {
        Follow followerComponent = (Follow) follower.get(Follow.class);
        Transform followerP = (Transform) follower.get(Transform.class);
        Transform targetTransform = (Transform) target.get(Transform.class);
        Vector3 followerV3 = new Vector3(followerP.transform.getTranslation(Vector3.Zero));
        Vector3 targetVector3 = new Vector3(targetTransform.transform.getTranslation(Vector3.Zero));

        followerP.transform.setToLookAt(targetVector3.sub(followerV3).nor().scl(1), Vector3.Y)
                .inv()
                .rotate(Vector3.Y, 90)
                .trn(followerV3);

        if (followerV3.dst(targetVector3) > followerComponent.distance) {
            if (follower.has(Parent.class)) {
                for (Entity child : follower.get(Parent.class).children) {
                    if (child.has(Weapon.class)) {
                        child.add(JustFire.instance);
                    }
                }
            }
        }
    }
}
