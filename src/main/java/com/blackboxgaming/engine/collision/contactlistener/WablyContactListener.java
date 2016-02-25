package com.blackboxgaming.engine.collision.contactlistener;

import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btManifoldPoint;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Damage;
import com.blackboxgaming.engine.components.Health;
import com.blackboxgaming.engine.components.Model;
import com.blackboxgaming.engine.components.Parent;
import com.blackboxgaming.engine.components.Puppet;
import com.blackboxgaming.engine.components.Sound;
import com.blackboxgaming.engine.components.Weapon;
import com.blackboxgaming.engine.util.Global;
import com.blackboxgaming.engine.util.Randomizer;

/**
 *
 * @author Adrian
 */
public class WablyContactListener extends ContactListener {

    private final Vector3 v = new Vector3();
    private final Quaternion q = new Quaternion();
    private long lastSound;
    private long soundDelay = 300;

    @Override
    public void onContactProcessed(btManifoldPoint cp, btCollisionObject colObj0, btCollisionObject colObj1) {
        if (colObj0.getUserValue() == Global.planeId) {
            cp.getLocalPointA(v);
        } else if (colObj1.getUserValue() == Global.planeId) {
            cp.getLocalPointB(v);
        }
        if (Math.abs(v.x) > Global.planeRange) {
            resolveTouch(v.x);
        }
    }

    private void resolveTouch(float range) {
        boolean addScore = false;
        if (!Global.touchLeft && !Global.touchRight) {
            // first touch
            Global.touchRight = range > 0;
            Global.touchLeft = range < 0;
            addScore = true;
        } else {
            if (range > 0 && Global.touchLeft) {
                Global.touchLeft = false;
                Global.touchRight = true;
                addScore = true;
            }

            if (range < 0 && Global.touchRight) {
                Global.touchLeft = true;
                Global.touchRight = false;
                addScore = true;
            }
        }
        if (addScore) {
            Attribute attribute = Engine.entityManager.get(Global.glassId).get(Model.class).modelInstance.materials.get(0).get(ColorAttribute.Diffuse);
            ((ColorAttribute) attribute).color.set(Randomizer.getRandomColor());
            
            int score = Integer.parseInt(((ImageTextButton) Global.scoreButton).getText().toString());
            ((ImageTextButton) Global.scoreButton).setText("" + (++score));
            System.out.println("left = " + Global.touchLeft + "; right = " + Global.touchRight);
        }
    }

    @Override
    public void onContactStarted(int userValue0, boolean match0, int userValue1, boolean match1) {
        if(Engine.entityManager.get(userValue1).has(Sound.class)){
            if(System.currentTimeMillis() > lastSound + soundDelay){
                lastSound = System.currentTimeMillis();
                Engine.entityManager.get(userValue1).get(Sound.class).sound.play();
            }
        }
        
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
    public void onContactEnded(int userValue0, boolean match0, int userValue1, boolean match1) {
        if (Global.CONTACT_GROUP_GROUND.contains((Integer) userValue0) && Engine.entityManager.get(userValue1).has(Puppet.class)) {
            Engine.entityManager.get(userValue1).get(Puppet.class).isFlying = true;
        } else if (Global.CONTACT_GROUP_GROUND.contains((Integer) userValue1) && Engine.entityManager.get(userValue0).has(Puppet.class)) {
            Engine.entityManager.get(userValue0).get(Puppet.class).isFlying = true;
        }
    }

}
