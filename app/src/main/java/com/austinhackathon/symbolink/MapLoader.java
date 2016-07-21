package com.austinhackathon.symbolink;

import java.util.HashMap;

import com.austinhackathon.symbolink.Cell.CellType;

public class MapLoader {

    private static HashMap<Integer, CellType> cellTypesMap;

    public MapLoader() {
        cellTypesMap = new HashMap<>();
        cellTypesMap.put(0, CellType.TYPE0);
        cellTypesMap.put(1, CellType.TYPE1);
        cellTypesMap.put(2, CellType.TYPE2);
        cellTypesMap.put(3, CellType.TYPE3);
        cellTypesMap.put(4, CellType.TYPE4);
        cellTypesMap.put(5, CellType.TYPE5);
        cellTypesMap.put(6, CellType.TYPE6);
        cellTypesMap.put(7, CellType.TYPE7);
        cellTypesMap.put(8, CellType.TYPE8);
        cellTypesMap.put(9, CellType.TYPE9);
        cellTypesMap.put(10, CellType.TYPE10);
        cellTypesMap.put(11, CellType.TYPE11);
        cellTypesMap.put(12, CellType.TYPE12);
        cellTypesMap.put(13, CellType.TYPE13);
        cellTypesMap.put(14, CellType.TYPE14);
        cellTypesMap.put(15, CellType.TYPE15);
        cellTypesMap.put(16, CellType.TYPE16);
        cellTypesMap.put(17, CellType.TYPE17);
        cellTypesMap.put(18, CellType.TYPE18);
        cellTypesMap.put(19, CellType.TYPE19);
    }

    public Grid createGrid(int[][] map) {
        int rows = map.length;
        int cols = map[0].length;
        CellType[][] celltypes = new CellType[rows][cols];
        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < cols ; ++j) {
                celltypes[i][j] = cellTypesMap.get(map[i][j]);
            }
        }
        return new Grid(celltypes);
    }

}
