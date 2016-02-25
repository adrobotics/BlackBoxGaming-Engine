package com.blackboxgaming.engine.managers;

import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.*;
import com.blackboxgaming.engine.components.ai.Follow;
import com.blackboxgaming.engine.systems.*;
import com.blackboxgaming.engine.systems.ai.FollowSystem;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Adrian
 */
public class EntityManager {

    private final Map<Integer, Entity> map = new HashMap();

    public boolean has(Entity entity) {
        return map.containsKey(entity.id);
    }

    public void add(Entity entity) {
        if (!has(entity)) {
            map.put(entity.id, entity);
            addToSystems(entity);
        }
    }

    public void update(Entity entity) {
        addToSystems(entity);
    }

    public Entity get(Entity entity) {
        return map.get(entity.id);
    }

    public Entity get(int id) {
        return map.get(id);
    }

    public void remove(Entity entity) {
        entity.dispose();
        map.remove(entity.id);
    }

    public int getCount() {
        return map.size();
    }

    public void dispose() {
        System.out.println("Disposing " + this.getClass());
        for (Map.Entry<Integer, Entity> entrySet : map.entrySet()) {
            entrySet.getValue().dispose();
        }
    }

    private void addToSystems(Entity entity) {
        // model renderer
        if (entity.has(Transform.class) && entity.has(Model.class)) {
            if (Engine.systemManager.has(ModelRendererSystem.class)) {
                entity.get(Model.class).modelInstance.transform = entity.get(Transform.class).transform;
                Engine.systemManager.get(ModelRendererSystem.class).add(entity);
            }
            if (Engine.systemManager.has(AbyssSystem.class)) {
                Engine.systemManager.get(AbyssSystem.class).add(entity);
            }
        }

        // velocity
        if (entity.has(Velocity.class) && entity.has(Transform.class)) {
            if (Engine.systemManager.has(VelocitySystem.class)) {
                Engine.systemManager.get(VelocitySystem.class).add(entity);
            }
        }

        // puppet
        if (entity.has(Puppet.class) && entity.has(Velocity.class) && entity.has(Transform.class) && entity.has(Speed.class)) {
            if (Engine.systemManager.has(PuppetMoverSystem.class)) {
                Engine.systemManager.get(PuppetMoverSystem.class).add(entity);
            }
        }
                
        if (entity.has(Puppet.class) && entity.has(Velocity.class) && entity.has(Transform.class) && entity.has(Speed.class)) {
            if (Engine.systemManager.has(PlaneMoverSystem.class)) {
                Engine.systemManager.get(PlaneMoverSystem.class).add(entity);
            }
        }

        // puppet joystick
        if (entity.has(Puppet.class) && entity.has(Velocity.class) && entity.has(Transform.class) && entity.has(Speed.class)) {
            if (Engine.systemManager.has(PuppetMoverJoystickSystem.class)) {
                Engine.systemManager.get(PuppetMoverJoystickSystem.class).add(entity);
            }
        }

        // orbit camera
        if (entity.has(OrbitCameraFocus.class) && entity.has(Transform.class)) {
            if (Engine.systemManager.has(OrbitCameraSystem.class)) {
                Engine.systemManager.get(OrbitCameraSystem.class).add(entity);
            }
        }

        // physics
        if (entity.has(Physics.class)) {
            if (Engine.systemManager.has(PhysicsSystem.class)) {
                Engine.systemManager.get(PhysicsSystem.class).add(entity);
            }
        }

        if (entity.has(Physics2D.class)) {
            if (Engine.systemManager.has(PhysicsSystem2D.class)) {
                Engine.systemManager.get(PhysicsSystem2D.class).add(entity);
            }
        }

        // HUD
        if (entity.has(HUDItem.class)) {
            if (Engine.systemManager.has(HUDSystem.class)) {
                Engine.systemManager.get(HUDSystem.class).add(entity);
            }
        }

        // health
        if (entity.has(Health.class)) {
            if (Engine.systemManager.has(HealthSystem.class)) {
                Engine.systemManager.get(HealthSystem.class).add(entity);
            }
        }

        // parent/child 
        if (entity.has(Child.class)) {
            if (Engine.systemManager.has(ParentChildSystem.class)) {
                Engine.systemManager.get(ParentChildSystem.class).add(entity.get(Child.class).parent);
            }
        }

        // weapon
        if (entity.has(Weapon.class)) {
            if (Engine.systemManager.has(WeaponSystem.class)) {
                Engine.systemManager.get(WeaponSystem.class).add(entity);
            }
        }

        // death
        if (entity.has(Death.class)) {
            if (Engine.systemManager.has(TimedDeathSystem.class)) {
                Engine.systemManager.get(TimedDeathSystem.class).add(entity);
            }
        }

        // layer
        if (entity.has(Layer.class)) {
            if (Engine.systemManager.has(LayerRendererSystem.class)) {
                Engine.systemManager.get(LayerRendererSystem.class).add(entity);
            }
        }

        // enemy
        if (entity.has(Enemy.class)) {
            if (Engine.systemManager.has(EnemySpawnSystem.class)) {
                Engine.systemManager.get(EnemySpawnSystem.class).add(entity);
            }
        }

        // follow
        if (entity.has(Follow.class)) {
            if (Engine.systemManager.has(FollowSystem.class)) {
                Engine.systemManager.get(FollowSystem.class).add(entity);
            }
        }
        
        // restrict
        if (entity.has(RestrictMotion.class)) {
            if (Engine.systemManager.has(RestrictMotionSystem.class)) {
                Engine.systemManager.get(RestrictMotionSystem.class).add(entity);
            }
        }
    }
}
