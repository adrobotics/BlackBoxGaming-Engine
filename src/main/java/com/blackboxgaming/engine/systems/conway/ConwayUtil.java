package com.blackboxgaming.engine.systems.conway;

import com.blackboxgaming.engine.Engine;
import com.blackboxgaming.engine.Entity;
import com.blackboxgaming.engine.components.Cell;
import com.blackboxgaming.engine.components.Model;
import com.blackboxgaming.engine.components.Name;
import com.blackboxgaming.engine.components.Transform;
import com.blackboxgaming.engine.factories.ModelFactory;
import com.blackboxgaming.engine.util.Randomizer;

/**
 * This class adds cells in different patterns.
 *
 * @author Adrian
 */
public class ConwayUtil {

    /**
     * Creates a sphere of cells in the center of the screen.
     *
     * @param diameter
     * @param ratio Fill = 100/ratio. Ex: 1=100% fill and 2=50% fill.
     */
    public static void createPremordialSoup(int diameter, int ratio) {
        int count = 0;
        for (int i = 0; i < diameter; i++) {
            for (int j = 0; j < diameter; j++) {
                for (int k = 0; k < diameter; k++) {
                    int ii = i - diameter / 2, jj = j - diameter / 2, kk = k - diameter / 2;
                    int dist = ii * ii + jj * jj + kk * kk;
                    if (dist < (diameter / 2) * (diameter / 2)) {
                        if (Randomizer.getRandomInteger() % ratio == 0) {
                            createCell(i - diameter / 2, j - diameter / 2, k - diameter / 2);
                            count++;
                        }
                    }
                }
            }
        }
        System.out.println("Primordial soup contains " + count + " cells");
    }

    /**
     * Glider in the center of the screen. Only works for rule Life 5766.
     */
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

    private static Entity createCell(int x, int y, int z) {
        Entity e = new Entity();
        e.add(new Cell(x, y, z));
        e.add(new Transform(x, y, z));
        e.add(new Name("Cell"));
        e.add(new Model(ModelFactory.getTheSameCubeModel(1), Randomizer.getRandomColor(), true));
        Engine.entityManager.add(e);
        return e;
    }

}
