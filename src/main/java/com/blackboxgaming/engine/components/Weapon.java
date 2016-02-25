package com.blackboxgaming.engine.components;

import com.badlogic.gdx.math.Matrix4;

/**
 *
 * @author Adrian
 */
public class Weapon implements IComponent {

    public final int weaponType;
    public final Matrix4 nozzleRelativeToWeapon;
    public com.badlogic.gdx.graphics.g3d.Model projectileModel;
    public float damage;
    public float range;
    public float speed;
    public float projectileSpeed;
    public Model nozzleFireModel;
    public Model hitSplashModel;
    public Model bulletModel;

    /**
     * Time between shots in milliseconds.
     */
    public float fireRate;
    public long lastFired;

    public Weapon(int weaponType, Matrix4 nozzleRelativeToWeapon) {
        this.weaponType = weaponType;
        this.nozzleRelativeToWeapon = nozzleRelativeToWeapon;
    }
    
}
