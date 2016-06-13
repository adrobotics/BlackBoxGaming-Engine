package com.blackboxgaming.engine.systems.conway;

import com.blackboxgaming.engine.components.Cell;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;

/**
 *
 * @author Adrian
 */
public class GenerationRezolverTest {

    Cell center;
    Set<Cell> universe;
    Set<Cell> ghosts;

    @Before
    public void reset() {
        center = new Cell(0, 0, 0);
        universe = new HashSet();
        universe.add(center);
        ghosts = new HashSet();
    }

    @Test
    public void testGetNeighbours_empty() {
        GenerationRezolver solver = new GenerationRezolver(new Rule(0, 0, 0, 0));
        assertEquals(0, solver.getNeighbours(center, universe, ghosts));
        assertEquals(26, ghosts.size());
    }

    @Test
    public void testGetNeighbours_crossFormation() {
        GenerationRezolver solver = new GenerationRezolver(new Rule(0, 0, 0, 0));
        universe.add(new Cell(0, -1, 0));
        universe.add(new Cell(0, 1, 0));
        universe.add(new Cell(1, 0, 0));
        universe.add(new Cell(-1, 0, 0));
        universe.add(new Cell(0, 0, 1));
        universe.add(new Cell(0, 0, -1));

        assertEquals(6, solver.getNeighbours(center, universe, ghosts));
        assertEquals(20, ghosts.size());
    }

    @Test
    public void testGetNeighbours_maxNeighbours() {
        GenerationRezolver solver = new GenerationRezolver(new Rule(0, 0, 0, 0));
        universe.add(new Cell(-1, -1, -1));
        universe.add(new Cell(-1, -1, 0));
        universe.add(new Cell(-1, -1, 1));
        universe.add(new Cell(-1, 0, -1));
        universe.add(new Cell(-1, 0, 0));
        universe.add(new Cell(-1, 0, 1));
        universe.add(new Cell(-1, 1, -1));
        universe.add(new Cell(-1, 1, 0));
        universe.add(new Cell(-1, 1, 1));

        universe.add(new Cell(0, -1, -1));
        universe.add(new Cell(0, -1, 0));
        universe.add(new Cell(0, -1, 1));
        universe.add(new Cell(0, 0, -1));
        universe.add(new Cell(0, 0, 0));
        universe.add(new Cell(0, 0, 1));
        universe.add(new Cell(0, 1, -1));
        universe.add(new Cell(0, 1, 0));
        universe.add(new Cell(0, 1, 1));

        universe.add(new Cell(1, -1, -1));
        universe.add(new Cell(1, -1, 0));
        universe.add(new Cell(1, -1, 1));
        universe.add(new Cell(1, 0, -1));
        universe.add(new Cell(1, 0, 0));
        universe.add(new Cell(1, 0, 1));
        universe.add(new Cell(1, 1, -1));
        universe.add(new Cell(1, 1, 0));
        universe.add(new Cell(1, 1, 1));

        assertEquals(26, solver.getNeighbours(center, universe, ghosts));
        assertEquals(0, ghosts.size());
    }

    @Test
    public void testGetNeighbours_SurroundedButDistant() {
        GenerationRezolver solver = new GenerationRezolver(new Rule(0, 0, 0, 0));
        int dist = 2;
        for (int i = -dist; i <= dist; i++) {
            for (int j = -dist; j <= dist; j++) {
                for (int k = -dist; k <= dist; k++) {
                    if (Math.abs(i) == 2 || Math.abs(j) == 2 || Math.abs(k) == 2) {
                        universe.add(new Cell(i, j, k));
                    }
                }
            }
        }

        assertEquals(98 + 1, universe.size());
        assertEquals(0, solver.getNeighbours(center, universe, ghosts));
        assertEquals(26, ghosts.size());
    }

}
