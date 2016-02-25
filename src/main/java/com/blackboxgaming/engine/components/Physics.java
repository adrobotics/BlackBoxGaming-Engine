package com.blackboxgaming.engine.components;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody.btRigidBodyConstructionInfo;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;
import com.badlogic.gdx.utils.Disposable;

/**
 *
 * @author Adrian
 */
public class Physics implements IComponent, Disposable {
    
    public final btRigidBody body;
    
    public Physics(btCollisionShape collisionShape, float mass, int collisionType, short belongToGroup, short collideWithGroup, int physicsState) {
        Vector3 localInertia = new Vector3(0, 0, 0);
        if (mass > 0f) {
            collisionShape.calculateLocalInertia(mass, localInertia);
        }
        btRigidBodyConstructionInfo constructionInfo = new btRigidBodyConstructionInfo(mass, null, collisionShape, localInertia);
        body = new btRigidBody(constructionInfo);
        body.setCollisionFlags(body.getCollisionFlags() | collisionType);
        body.setContactCallbackFlag(belongToGroup);
        body.setContactCallbackFilter(collideWithGroup);
        body.setActivationState(physicsState);
        
        constructionInfo.dispose();
    }
    
    @Override
    public void dispose() {
        body.getMotionState().dispose();
        body.getCollisionShape().dispose();
        body.dispose();
    }
    
    public void setMotionState(Matrix4 transform) {
        body.setMotionState(new MotionState(transform));
    }
    
    public class MotionState extends btMotionState {
        
        public final Matrix4 transform;
        
        public MotionState(Matrix4 transform) {
            this.transform = transform;
        }
        
        @Override
        public void getWorldTransform(Matrix4 worldTrans) {
            worldTrans.set(transform);
        }
        
        @Override
        public void setWorldTransform(Matrix4 worldTrans) {
            transform.set(worldTrans);
        }
    }
}
