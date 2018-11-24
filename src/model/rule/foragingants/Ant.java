package model.rule.foragingants;

import java.util.concurrent.ThreadLocalRandom;

public class Ant {
    public static final int N = 0;
    public static final int NE = 1;
    public static final int E = 2;
    public static final int SE = 3;
    public static final int S = 4;
    public static final int SW = 5;
    public static final int W = 6;
    public static final int NW = 7;

    private int myOrientation;
    private boolean hasMoved;

    public boolean getHasFoodItem() {
        return hasFoodItem;
    }

    public void setHasFoodItem(boolean hasFoodItem) {
        this.hasFoodItem = hasFoodItem;
    }

    private boolean hasFoodItem;


    public Ant(){
        myOrientation = ThreadLocalRandom.current().nextInt(0, 8);
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

}
