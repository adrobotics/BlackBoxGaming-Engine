package com.blackboxgaming.engine.collision.contactlistener;

import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.components.Model;
import com.blackboxgaming.engine.components.Puppet;
import com.blackboxgaming.engine.util.Global;
import com.blackboxgaming.engine.util.Randomizer;

/**
 *
 * @author Adrian
 */
public class ColorChangerContactListener extends ContactListener {

    @Override
    public boolean onContactAdded(int userValue0, int partId0, int index0, boolean match0, int userValue1, int partId1, int index1, boolean match1) {
        // is puppet flying
        if (match0 && Global.PHYSICS_CONTACT_GROUP_PUPPET.contains(userValue0)) {
            Engine.entityManager.get(userValue0).get(Puppet.class).isFlying = false;
        }
        if (match1 && Global.PHYSICS_CONTACT_GROUP_PUPPET.contains(userValue1)) {
            Engine.entityManager.get(userValue1).get(Puppet.class).isFlying = false;
        }

        // puppet hitting puppet
        if (Global.PHYSICS_CONTACT_GROUP_PUPPET.contains(userValue0) && Global.PHYSICS_CONTACT_GROUP_PUPPET.contains(userValue1)) {
            Attribute attribute0 = Engine.entityManager.get(userValue0).get(Model.class).modelInstance.materials.get(0).get(ColorAttribute.Diffuse);
            ((ColorAttribute) attribute0).color.set(Randomizer.getRandomGreenishColor());
            Attribute attribute1 = Engine.entityManager.get(userValue1).get(Model.class).modelInstance.materials.get(0).get(ColorAttribute.Diffuse);
            ((ColorAttribute) attribute1).color.set(Randomizer.getRandomGreenishColor());
        }

        // puppet hitting wall
        if (Global.PHYSICS_CONTACT_GROUP_WALL.contains(userValue0) && Global.PHYSICS_CONTACT_GROUP_PUPPET.contains(userValue1)) {
            Attribute attribute1 = Engine.entityManager.get(userValue1).get(Model.class).modelInstance.materials.get(0).get(ColorAttribute.Diffuse);
            ((ColorAttribute) attribute1).color.set(Randomizer.getRandomBlueishColor());
        } else if (Global.PHYSICS_CONTACT_GROUP_WALL.contains(userValue1) && Global.PHYSICS_CONTACT_GROUP_PUPPET.contains(userValue0)) {
            Attribute attribute1 = Engine.entityManager.get(userValue0).get(Model.class).modelInstance.materials.get(0).get(ColorAttribute.Diffuse);
            ((ColorAttribute) attribute1).color.set(Randomizer.getRandomBlueishColor());
        }
        
        // puppet hitting obstacle
        if (Global.PHYSICS_CONTACT_GROUP_OBSTACLE.contains(userValue0) && Global.PHYSICS_CONTACT_GROUP_PUPPET.contains(userValue1)) {
            Attribute attribute1 = Engine.entityManager.get(userValue1).get(Model.class).modelInstance.materials.get(0).get(ColorAttribute.Diffuse);
            ((ColorAttribute) attribute1).color.set(Randomizer.getRandomReddishColor());
        } else if (Global.PHYSICS_CONTACT_GROUP_OBSTACLE.contains(userValue1) && Global.PHYSICS_CONTACT_GROUP_PUPPET.contains(userValue0)) {
            Attribute attribute1 = Engine.entityManager.get(userValue0).get(Model.class).modelInstance.materials.get(0).get(ColorAttribute.Diffuse);
            ((ColorAttribute) attribute1).color.set(Randomizer.getRandomReddishColor());
        }
        return true;
    }
}
