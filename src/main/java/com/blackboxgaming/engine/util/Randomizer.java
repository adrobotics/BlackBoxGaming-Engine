package com.blackboxgaming.engine.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.MathUtils;
import com.blackboxgaming.engine.factories.ModelFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Adrian
 */
public class Randomizer {

    public static Random rand = new Random();
    public static String[] collisionShapeName = {"box", "sphere", "cone", "capsule", "cylinder", "tile"};
    private static int csni;
    private static float lastSize;
    private static List<Color> colorPalette;

    public static void initPalette() {
        colorPalette = new ArrayList();
        // http://paletton.com/#uid=75B0z0kllllaFw0g0qFqFg0w0aF
        // red
        colorPalette.add(new Color(0.655f, 0.22f, 0.243f, 1f));
        colorPalette.add(new Color(0.984f, 0.655f, 0.671f, 1f));
        colorPalette.add(new Color(0.82f, 0.408f, 0.427f, 1f));
        colorPalette.add(new Color(0.49f, 0.082f, 0.102f, 1f));
        colorPalette.add(new Color(0.325f, 0f, 0.016f, 1f));

        // green
        colorPalette.add(new Color(0.216f, 0.545f, 0.18f, 1f));
        colorPalette.add(new Color(0.573f, 0.82f, 0.545f, 1f));
        colorPalette.add(new Color(0.373f, 0.682f, 0.341f, 1f));
        colorPalette.add(new Color(0.098f, 0.408f, 0.067f, 1f));
        colorPalette.add(new Color(0.027f, 0.275f, 0f, 1f));

        // blue
        colorPalette.add(new Color(0.141f, 0.376f, 0.408f, 1f));
        colorPalette.add(new Color(0.412f, 0.588f, 0.612f, 1f));
        colorPalette.add(new Color(0.259f, 0.478f, 0.51f, 1f));
        colorPalette.add(new Color(0.055f, 0.275f, 0.306f, 1f));
        colorPalette.add(new Color(0.004f, 0.18f, 0.204f, 1f));

        // brown
        colorPalette.add(new Color(0.667f, 0.435f, 0.224f, 1f));
        colorPalette.add(new Color(1f, 0.827f, 0.667f, 1f));
        colorPalette.add(new Color(0.502f, 0.282f, 0.082f, 1f));
        colorPalette.add(new Color(0.333f, 0.161f, 0f, 1f));
    }

    public static Color getRandomColor() {
        return getRandomColorFromPalette();
    }

    public static Color getRandomColorFromPalette() {
        if (colorPalette == null) {
            initPalette();
        }
        return colorPalette.get(MathUtils.random(colorPalette.size() - 1));
    }

    public static Color getRandomLightColor() {
        return new Color(rand.nextFloat() / 2f + 0.5f, rand.nextFloat() / 2f + 0.5f, rand.nextFloat() / 2f + 0.5f, 1f);
    }

    public static Color getRandomLightGrayishColor() {
        float value = rand.nextFloat() / 2f + 0.5f;
        return new Color(value, value, value, 1);
    }

    public static Color getRandomReddishColor() {
        return new Color(rand.nextFloat(), 0, 0, 1f);
    }

    public static Color getRandomGreenishColor() {
        return new Color(0, rand.nextFloat(), 0, 1f);
    }

    public static Color getRandomBlueishColor() {
        return new Color(0, 0, rand.nextFloat(), 1f);
    }

    public static float getRandomGenericSize(float min, float max) {
        float size = rand.nextFloat() * max;
        if (size < min) {
            size = min;
        }
        lastSize = size;
        return size;
    }

    public static float getLastSize() {
        return lastSize;
    }

    public static Model getRandomModel(float size) {
        Model m;
        switch (rand.nextInt(6)) {
            case 0:
                m = ModelFactory.getCubeModel(size);
                csni = 0;
                break;
            case 1:
                m = ModelFactory.getSphereModel(size);
                csni = 1;
                break;
            case 2:
                m = ModelFactory.getConeModel(size);
                csni = 2;
                break;
            case 3:
                m = ModelFactory.getCapsuleModel(size);
                csni = 3;
                break;
            case 4:
                m = ModelFactory.getCylinderModel(size);
                csni = 4;
                break;
            case 5:
                m = ModelFactory.getTileModel(size);
                csni = 5;
                break;
            default:
                m = null;
        }
        return m;
    }

    public static String getMatchingCollisionShapeName() {
        return collisionShapeName[csni];
    }

    public static String getRandomCollisionShapeName() {
        return collisionShapeName[rand.nextInt(collisionShapeName.length)];
    }
    
    public static boolean getRandomBoolean(){
        return rand.nextBoolean();
    }
    
    public static int getRandomInteger(){
        return rand.nextInt();
    }
    
    public static int getRandomInteger(int upperBound){
        return rand.nextInt(upperBound);
    }

}
