package com.blackboxgaming.engine.systems.conway;

import com.blackboxgaming.engine.components.Cell;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Adrian
 */
public class GenerationRezolver {

    private final Rule rule;

    public GenerationRezolver(Rule rule) {
        this.rule = rule;
    }

    public Set<Cell> nextGeneration(Set<Cell> currentGeneration) {
        Set<Cell> nextGeneration = new HashSet();
        Set<Cell> ghostCells = new HashSet();
        for (Cell cell : currentGeneration) {
            int neighbours = getNeighbours(cell, currentGeneration, ghostCells);
            if (rule.willSurvive(neighbours)) {
                nextGeneration.add(cell);
            }
        }
        for (Cell cell : ghostCells) {
            int neighbours = getNeighbours(cell, currentGeneration, null);
            if (rule.willGiveBirth(neighbours)) {
                nextGeneration.add(cell);
            }
        }
        return nextGeneration;
    }

    protected int getNeighbours(Cell cell, Set<Cell> currentGeneration, Set<Cell> ghostCells) {
        int neighbours = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    if (i == 0 && j == 0 && k == 0) {
                        continue;
                    }
                    Cell findMe = new Cell(cell.x + i, cell.y + j, cell.z + k);
                    if (currentGeneration.contains(findMe)) {
                        neighbours++;
                    } else {
                        if (ghostCells != null) {
                            ghostCells.add(findMe);
                        }
                    }
                }
            }
        }
        return neighbours;
    }

}
