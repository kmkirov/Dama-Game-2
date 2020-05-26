package com.game.damagame;

public class Coords {
    // index constants
    public static int INDEX_0 = 0;
    public static int INDEX_1 = 1;
    public static int INDEX_2 = 2;

    public static int MAX_POSITION = 7;
    public static int MIN_POSITION = 0;


    public int index, position;

    public Coords()//invalid use
    {
        index = -1;
        position = -1;
    }

    public boolean equal_coords(Coords c) {
        if (index == c.index && position == c.position)
            return true;
        return false;
    }


    public Coords(int i, int p)//valid use
    {
        index = i;
        position = p;
    }

    int getIndex() {
        return index;
    }

    int getPosition() {
        return position;
    }
}