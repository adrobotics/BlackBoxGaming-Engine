package com.blackboxgaming.engine.systems.ai;

import com.blackboxgaming.engine.components.ai.Fear;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.systems.ISystem;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adrian
 */
@Deprecated
public class FearSystem implements ISystem {

    public static List<Entity> fearers = new ArrayList();

    @Override
    public void add(Entity entity) {
        fearers.add(entity);
    }

    @Override
    public void update(float delta) {
        for (Entity fearer : fearers) {
            fear(fearer, ((Fear) fearer.get(Fear.class)).target);
        }
    }

    private void fear(Entity fearer, Entity fearee) {
//        Speed speed = (Speed) fearer.get(Speed.class);
//        Fear fearComponent = (Fear) fearer.get(Fear.class);
//        Transform fearerP = (Transform) fearer.get(Transform.class);
//        Transform feareeP = (Transform) fearee.get(Transform.class);
////        Vector3 fearerV3 = new Vector3(fearerP.position);
////        Vector3 feareeV3 = new Vector3(feareeP.position);
////        if (fearerV3.dst(feareeV3) < fearComponent.distance) {
////            Vector3 velocity = new Vector3(feareeV3.sub(fearerV3).nor().scl(speed.speed));
////            fearerP.position.set(fearerP.position.cpy().sub(velocity));
//            
//            GameObject obj = (GameObject) fearer.get(GameObject.class);
////            obj.transform.setTranslation(fearerP.position);
//            obj.body.setCenterOfMassTransform(obj.transform);
////        }
    }

    @Override
    public void remove(Entity entity) {
    }

}
