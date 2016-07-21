package com.austinhackathon.symbolink;

import com.austinhackathon.symbolink.Cell.CellType;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    public static int rows;
    public static int cols;
    private static Cell[][] cells;

    public Grid(CellType[][] cellTypes) {
        Grid.rows = cellTypes.length;
        Grid.cols = cellTypes[0].length;
        cells = new Cell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cells[i][j] = new Cell(i, j, cellTypes[i][j]);
            }
        }
    }

    /**
     * Link two cells by coordinates
     */
    public Path linkTwoCells(int row1, int col1, int row2, int col2) {
        return linkTwoCells(cells[row1][col1], cells[row2][col2]);
    }

    /**
     *  Link two cells.
     */
    private Path linkTwoCells(Cell cell1, Cell cell2) {
        if (cell1.type == CellType.TYPE0 || cell2.type == CellType.TYPE0) {
            return new Path();
        }
        if (isTwoCellsSameType(cell1, cell2)) {
            Path path = isTwoCellsLinked(cell1, cell2);
            if(path.isValid) {
                flip(cell1);
                flip(cell2);
            }
            return path;
        }
        return new Path();
    }

    /**
     * Is two cells same type.
     */
    private boolean isTwoCellsSameType(Cell cell1, Cell cell2) {
        return cell1.type.equals(cell2.type);
    }

    /**
     * Is two cells linked.
     */
    private Path isTwoCellsLinked(Cell cell1, Cell cell2) {
        List<Cell> neighbors1 = availableNeighbors(cell1);
        List<Cell> neighbors2 = availableNeighbors(cell2);
        return existLinkedCells(neighbors1, neighbors2, cell1, cell2);
    }

    /**
     * Flip cell.
     */
    private void flip(Cell cell) {
        cell.type = CellType.TYPE0;
    }

    private List<Cell> availableNeighbors(Cell cell) {
        int currRow = cell.row;
        int currCol = cell.col;
        List<Cell> neighbors = new ArrayList<>();
        // Add the cell itself
        neighbors.add(cell);
        // Check horizontal
        for (int i = currCol + 1; i < cols; i++) {
            Cell neighbor = cells[currRow][i];
            if (neighbor.type.equals(CellType.TYPE0)) {
                neighbors.add(neighbor);
            } else {
                break;
            }
        }
        for (int i = currCol - 1; i >= 0; i--) {
            Cell neighbor = cells[currRow][i];
            if (neighbor.type.equals(CellType.TYPE0)) {
                neighbors.add(neighbor);
            } else {
                break;
            }
        }
        // Check vertical
        for (int i = currRow + 1; i < rows; i++) {
            Cell neighbor = cells[i][currCol];
            if (neighbor.type.equals(CellType.TYPE0)) {
                neighbors.add(neighbor);
            } else {
                break;
            }
        }
        for (int i = currRow - 1; i >= 0; i--) {
            Cell neighbor = cells[i][currCol];
            if (neighbor.type.equals(CellType.TYPE0)) {
                neighbors.add(neighbor);
            } else {
                break;
            }
        }
        return neighbors;
    }

    /**
     * Check if two lists contains two individual cells are
     * linked in the same line without block.
     */
    private Path existLinkedCells(List<Cell> list1, List<Cell> list2, Cell firstCell, Cell secondCell) {
        for (Cell cell1 : list1) {
            for (Cell cell2 : list2) {
                if (checkSameLine(cell1, cell2) && twoCellsNotBlocked(cell1, cell2)) {
                    Path path = new Path();
                    path.isValid = true;
                    findAndSetPath(firstCell, secondCell, cell1, cell2, path);
                    path.getPath().add(secondCell);
                    return path;
                }
            }
        }
        return new Path();
    }

    /**
     * Find the path when given source, destination and two turing points
     * @param firstCell
     * @param secondCell
     * @param turn1Cell
     * @param turn2Cell
     */
    private void findAndSetPath(Cell firstCell, Cell secondCell, Cell turn1Cell, Cell turn2Cell, Path path) {
        List<Cell> pathList = path.getPath();
        addBetweenCells(firstCell, turn1Cell, pathList);
        addBetweenCells(turn1Cell, turn2Cell, pathList);
        addBetweenCells(turn2Cell, secondCell, pathList);
    }

    private void addBetweenCells(Cell start, Cell dest, List<Cell> cellPathList) {
        if (start.row == dest.row) {
            for (int i = start.col; i < dest.col; i++) {
                cellPathList.add(cells[start.row][i]);
            }
            for (int i = start.col; i > dest.col; i--) {
                cellPathList.add(cells[start.row][i]);
            }
        } else {
            for (int i = start.row; i < dest.row; i++) {
                cellPathList.add(cells[i][start.col]);
            }
            for (int i = start.row; i > dest.row; i--) {
                cellPathList.add(cells[i][start.col]);
            }
        }
    }

    /**
     * Check if two cells are in the same line.
     */
    private boolean checkSameLine(Cell cell1, Cell cell2) {
        return cell1.row == cell2.row || cell1.col == cell2.col;
    }

    /**
     * Check if two cells are not blocked in the same line.
     */
    private boolean twoCellsNotBlocked(Cell cell1, Cell cell2) {
        int row1 = cell1.row;
        int col1 = cell1.col;
        int row2 = cell2.row;
        int col2 = cell2.col;
        if(row1 == row2) {
            for(int i = Math.min(col1,col2) + 1; i < Math.max(col1,col2); i++) {
                if(cells[row1][i].type != CellType.TYPE0) {
                    return false;
                }
            }
        } else {
            for(int i = Math.min(row1,row2)+1; i < Math.max(row1,row2); i++) {
                if(cells[i][col1].type != CellType.TYPE0) {
                    return false;
                }
            }
        }
        return true;
    }

}
