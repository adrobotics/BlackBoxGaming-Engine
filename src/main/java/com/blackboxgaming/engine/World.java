package com.blackboxgaming.engine;

import com.blackboxgaming.engine.components.Cell;
import com.blackboxgaming.engine.components.Model;
import com.blackboxgaming.engine.components.Transform;
import com.blackboxgaming.engine.factories.ModelFactory;
import com.blackboxgaming.engine.util.Randomizer;

/**
 * This class contains everything related to the game world
 *
 * @author Adrian
 */
public class World {

    public static void createPremordialSoup(int size, int ratio) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    int ii = i - size / 2, jj = j - size / 2, kk = k - size / 2;
                    int dist = ii * ii + jj * jj + kk * kk;
                    if (dist < (size/2) * (size/2)) {
                        if (Randomizer.getRandomInteger() % ratio == 0) {
                            createCell(i - size / 2, j - size / 2, k - size / 2);
                            count++;
                        }
                    }
                }
            }
        }
        System.out.println("Primordial soup contains " + count + " cells");
    }

    public static void createGlider() {
        createCell(1, 0, 0);
        createCell(-1, 0, 0);
        createCell(0, 0, 1);
        createCell(-1, 0, 1);
        createCell(-1, 0, -1);

        createCell(1, 1, 0);
        createCell(-1, 1, 0);
        createCell(0, 1, 1);
        createCell(-1, 1, 1);
        createCell(-1, 1, -1);
    }

    public static void createSurrounded() {
        int dist = 2;
        for (int i = -dist; i <= dist; i++) {
            for (int j = -dist; j <= dist; j++) {
                for (int k = -dist; k <= dist; k++) {
                    if (Math.abs(i) == 2 || Math.abs(j) == 2 || Math.abs(k) == 2) {
                        createCell(i, j, k);
                    }
                }
            }
        }
    }

    private static Entity createCell(int x, int y, int z) {
        Entity e = new Entity();
        e.add(new Cell(x, y, z));
        e.add(new Transform(x, y, z));
        e.add(new Model(ModelFactory.getTheSameCubeModel(1), Randomizer.getRandomColor(), true));
        Engine.entityManager.add(e);
        return e;
    }

}
