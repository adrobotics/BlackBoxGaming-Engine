package com.blackboxgaming.engine.factories;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Matrix4;
import com.blackboxgaming.engine.components.Model;
import com.blackboxgaming.engine.components.Weapon;
import com.blackboxgaming.engine.util.Randomizer;

/**
 *
 * @author Adrian
 */
public class WeaponFactory {

    public static final int WEAPON_GUN = 1;
    public static final int WEAPON_LASER = 2;
    public static final int WEAPON_PROJECTILE = 3;
    public static final int WEAPON_PLASMA = 4;
    public static final int WEAPON_GRENADE = 5;
    public static final int WEAPON_MELEE = 6;

    public static Weapon getWeapon(int type, Matrix4 nozzle) {
        Weapon weapon;
        switch (type) {
            case WEAPON_GUN:
                weapon = createGun(nozzle);
                break;
            case WEAPON_LASER:
                weapon = null;
                break;
            case WEAPON_PROJECTILE:
                weapon = createProjectile(nozzle);
                break;
            case WEAPON_PLASMA:
                weapon = createPlasma(nozzle);
                break;
            case WEAPON_GRENADE:
                weapon = null;
                break;
            case WEAPON_MELEE:
                weapon = createMelee(nozzle);
                break;
            default:
                weapon = null;
                System.out.println("Can't resolve weapon type");
                break;
        }
        return weapon;
    }

    private static Weapon createGun(Matrix4 nozzle) {
        Weapon weapon = new Weapon(WEAPON_GUN, nozzle);
        weapon.damage = 1;
        weapon.fireRate = 100;
        weapon.range = 50;
        weapon.nozzleFireModel = new Model(ModelFactory.getConeModel(0.5f), Color.RED);
        weapon.nozzleFireModel.someoneElseHandlesDisposing = true;
        weapon.hitSplashModel = new Model(ModelFactory.getBoxModel(3, 0.1f, 0.1f), Randomizer.getRandomColor());
        weapon.hitSplashModel.someoneElseHandlesDisposing = true;
        return weapon;
    }

    private static Weapon createProjectile(Matrix4 nozzle) {
        Weapon weapon = new Weapon(WEAPON_PROJECTILE, nozzle);
        weapon.damage = 1;
        weapon.fireRate = 500;
        return weapon;
    }
    
    private static Weapon createPlasma(Matrix4 nozzle) {
        Weapon weapon = new Weapon(WEAPON_PLASMA, nozzle);
        weapon.damage = 8f;
        weapon.speed = 50f;
        weapon.fireRate = 100;
        weapon.bulletModel = new Model(ModelFactory.getSphereModel(0.5f), Randomizer.getRandomColor());
        weapon.bulletModel.someoneElseHandlesDisposing = true;
        return weapon;
    }

    private static Weapon createMelee(Matrix4 nozzle) {
        Weapon weapon = new Weapon(WEAPON_MELEE, nozzle);
        weapon.damage = 2.5f;
        weapon.fireRate = 250;
        weapon.range = 2;
        weapon.nozzleFireModel = new Model(ModelFactory.getBoxModel(0.25f, 1.25f, 1.25f), Color.RED);
        weapon.nozzleFireModel.someoneElseHandlesDisposing = true;
        return weapon;
    }

}
