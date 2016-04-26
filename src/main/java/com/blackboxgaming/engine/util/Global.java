package com.blackboxgaming.engine.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.PerformanceCounter;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.input.OrbitCameraListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adrian
 */
public class Global {

    public static boolean debugLevelUp = true;
    public static final boolean showSplashScreen = true;
    public static final boolean showMenuScreen = true;
    public static boolean profiling = false;

    public static boolean renderHealthBar = false;
    public static final float splashScreenTime = 3000;
    @Deprecated
    public static boolean START_WITH_ANDROID_GESTURE_LISTENER = false;
    public static Button scoreButton;
    public static Label LEVEL_LABEL;
    public static Label HEALTH_LABEL;

    // debug
    public static boolean DEBUG_FRUSTRUM_CULLING_SHAPES = false;
    public static boolean DEBUG_BOUNGING_BOX_SHAPES = false;
    public static boolean DEBUG_ROTATION = false;
    public static boolean DEBUG_PHYSICS = false;
    public static boolean SHADOW = false;
    public static boolean BOOST = false;

    public static Entity mainCharacter;

    public static boolean SYNC_KEYBOARD_CAM_ROTATION = false;

    // pysics
    public static final List<Integer> PHYSICS_CONTACT_GROUP_WALL = new ArrayList();
    public static final List<Integer> PHYSICS_CONTACT_GROUP_PUPPET = new ArrayList();
    public static final List<Integer> PHYSICS_CONTACT_GROUP_OBSTACLE = new ArrayList();

    public static List<Integer> CONTACT_GROUP_GROUND = new ArrayList();
    public static List<Integer> CANTACT_GROUP_PLASMA = new ArrayList();

    // environment
    public static final boolean FRUSTRUM_CULLING = true;
    private static Environment environment = null;
    private static Camera camera = null;
    private static CameraInputController cameraController = null;

    // physics
    private static btCollisionConfiguration collisionConfig;
    private static btDispatcher dispatcher;
    private static btBroadphaseInterface broadphase;
    private static btConstraintSolver constraintSolver;
    private static btDynamicsWorld dynamicsWorld = null;
    private static World dynamicsWorld2D = null;
    public static float gravity = -10;
    private final static float gravity2D = 0;
    public final static float friction = 1f;
    public static boolean leftMouseCameraMove = false;

    // stuff
    public static int VISIBLE_OBJECT_COUNT = 0;
    public static float angle, speed = 300f;

    private static float size = 1;

    private static DirectionalShadowLight shadowLight;
    public static float mapWidth = 100;
    public static float angularSpeed = 360;
    public static int weaponSwitcherID;
    public static boolean gameOver = false;
    public static int planeId;
    public static int planeId2;
    public static float planeRange = 9;
    public static boolean touchLeft;
    public static boolean touchRight;
    public static int glassId;
    @Deprecated
    public static PerformanceCounter performanceCounter;
    public static boolean loopAmbientSound = false;

    public static float rotationSpeed = 180;
    public static float rotationCenter = -7.5f;
    public static float mass = 1;
    public static int plane;
    public static float platformLength = 30;
    public static float tipRatio = 0.3f;
    public static float tipHeight = 3.5f;
    public static float boxWidth = 0.9f * 10f;
    public static float boxLength = 1.6f * 10f;
    public static float boxDepth = 0.6f * 10f;
    public static float tennisBallDiameter = 0.686f;

    public static String gameScreen = "game";

    private static Vector3 lightDirection = new Vector3(0.5f, -0.75f, -0.25f);
    public static boolean hideMaxHealth = false;
    public static long lastPrint;
    public static Label BRICK_LABEL;
    public static Image swipeIcon;

    public static DirectionalShadowLight getShadowLight() {
        if (shadowLight == null) {
            shadowLight = new DirectionalShadowLight(1024 * 2, 1024 * 2, 50f, 50f, 1f, 100f);
            shadowLight.set(Color.BLACK, lightDirection);
        }
        return shadowLight;
    }

    public static Environment getEnvironment() {
        if (environment == null) {
            float ambientLightLevel = 0.25f;
            float directionaLightLevel = 0.75f;
            environment = new Environment();
            environment.set(new ColorAttribute(ColorAttribute.AmbientLight, ambientLightLevel, ambientLightLevel, ambientLightLevel, 1f));
            environment.add(new DirectionalLight().set(new Color(directionaLightLevel, directionaLightLevel, directionaLightLevel, 1), lightDirection));
            if (Global.SHADOW) {
                environment.shadowMap = getShadowLight();
            }
        }
        return environment;
    }

    public static Camera getCamera() {
        if (camera == null) {
            camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            camera.position.set(0, 15, 0);
            camera.lookAt(0, 0, 0);
            camera.near = 0.1f;
            camera.far = 300;
            camera.update();
        }
        return camera;
    }

    public static void setCamera(float x, float y, float z) {
        Global.getCamera().position.set(x, y, z);
        Global.getCamera().lookAt(0, 0, 0);
        Global.getCamera().up.set(Vector3.Y);
        Global.getCamera().update();
    }

    public static CameraInputController getCameraController() {
        if (cameraController == null) {
            cameraController = new OrbitCameraListener(Global.getCamera());
        }
        return cameraController;
    }

    // physics
    public static btDynamicsWorld getDynamicsWorld() {
        if (dynamicsWorld == null) {
            collisionConfig = new btDefaultCollisionConfiguration();
            dispatcher = new btCollisionDispatcher(collisionConfig);
            broadphase = new btDbvtBroadphase();
            constraintSolver = new btSequentialImpulseConstraintSolver();
            dynamicsWorld = new btDiscreteDynamicsWorld(dispatcher, broadphase, constraintSolver, collisionConfig);
            dynamicsWorld.setGravity(new Vector3(0, gravity, 0));
//            dynamicsWorld.getSolverInfo().setNumIterations(50);
        }
        return dynamicsWorld;
    }

    public static World getDynamicsWorld2D() {
        if (dynamicsWorld2D == null) {
            dynamicsWorld2D = new World(new Vector2(0, gravity2D), true);
        }
        return dynamicsWorld2D;
    }

    // stuff
    public static int getFps() {
        return Gdx.graphics.getFramesPerSecond();
    }

    public static float getDeltaInSeconds() {
        return Gdx.graphics.getDeltaTime();
    }

    public static int getDeltaInMillis() {
        return (int) (Gdx.graphics.getDeltaTime() * 1000);
    }

    public static int getVisibleObjectCount() {
        return VISIBLE_OBJECT_COUNT;
    }

    public static void setVisibleObjectCount(int visibleObjectCount) {
        Global.VISIBLE_OBJECT_COUNT = visibleObjectCount;
    }

    // dispose
    public static void dispose() {
        if (dynamicsWorld != null) {
            dynamicsWorld.dispose();
            constraintSolver.dispose();
            broadphase.dispose();
            dispatcher.dispose();
            collisionConfig.dispose();
        }
        if (dynamicsWorld2D != null) {
            dynamicsWorld2D.dispose();
        }
        if (shadowLight != null) {
            shadowLight.dispose();
        }
    }

    public static float moveTowardZero(float value, float amount) {
        value = (value > amount) ? value - amount
                : (value < -amount) ? value + amount
                        : 0;
        return value;
    }

    public static float moveTowardZeroFast(float value, float amount, float speed) {
        value = (value > amount * speed) ? value - amount * speed
                : (value < -amount * speed) ? value + amount * speed
                        : 0;
        return value;
    }

    public static void threshold(Vector3 vector, float threshold) {
        vector.x = (vector.x > threshold) ? threshold : vector.x;
        vector.y = (vector.y > threshold) ? threshold : vector.y;
        vector.z = (vector.z > threshold) ? threshold : vector.z;
        vector.x = (vector.x < -threshold) ? -threshold : vector.x;
        vector.y = (vector.y < -threshold) ? -threshold : vector.y;
        vector.z = (vector.z < -threshold) ? -threshold : vector.z;
    }

}
