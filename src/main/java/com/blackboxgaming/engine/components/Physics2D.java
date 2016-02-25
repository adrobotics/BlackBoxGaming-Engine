package com.blackboxgaming.engine.components;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Disposable;
import com.blackboxgaming.engine.util.Global;

/**
 *
 * @author Adrian
 */
public class Physics2D implements IComponent, Disposable {

    public Body body;

    public Physics2D(Shape shape, BodyType bodyType, float density, Matrix4 transform, boolean isSleepingAllowed) {
        this(shape, bodyType, density, transform, isSleepingAllowed, false);
    }
    
    public Physics2D(Shape shape, BodyType bodyType, float density, Matrix4 transform, boolean isSleepingAllowed, boolean isBullet) {
        Vector3 position = transform.getTranslation(Vector3.Zero);

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(position.x, position.z));
        bodyDef.type = bodyType;
        this.body = Global.getDynamicsWorld2D().createBody(bodyDef);
        this.body.setSleepingAllowed(isSleepingAllowed);
        this.body.setBullet(isBullet);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        body.createFixture(fixtureDef);
        
        shape.dispose();
    }

    @Override
    public void dispose() {
    }

}
