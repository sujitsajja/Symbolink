package com.austinhackathon.symbolink;

import java.util.ArrayList;
import java.util.List;

public class Path {
    public boolean isValid;
    private List<Cell> list = new ArrayList<>();
    public List<Cell> getPath() {
        return list;
    }
    public ArrayList<Integer> getPathForGame() {
        ArrayList<Integer> res = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            res.add((list.get(i).row)*10+list.get(i).col);
        }
        return res;
    }
}
