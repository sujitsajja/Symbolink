package com.austinhackathon.symbolink;

public class Cell {
    int row;
    int col;
    CellType type;
    public enum CellType {
        TYPE0, TYPE1, TYPE2,
        TYPE3, TYPE4, TYPE5,
        TYPE6, TYPE7, TYPE8,
        TYPE9, TYPE10,TYPE11,
        TYPE12,TYPE13,TYPE14,
        TYPE15,TYPE16,TYPE17,
        TYPE18,TYPE19
    }

    public Cell(int row, int col, CellType type) {
        this.row = row;
        this.col = col;
        this.type = type;
    }

}
