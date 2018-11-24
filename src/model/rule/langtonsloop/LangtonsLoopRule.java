package model.rule.langtonsloop;


import model.Cell;
import model.CellManager;
import model.rule.Rule;

import java.util.ArrayList;
import java.util.List;

/**
 * Extension of main.model.rule.Rule to apply rules specifically for Langton's Loop
 * Returns nextState for myCell
 *
 * @author Scott McConnell skm44
 **/
public class LangtonsLoopRule extends Rule {
    static final int NUM_PASSES = 1;
    static final int VACANT = 0; //black
    static final int SIGNAL = 1; //dark blue
    static final int SHEATH = 2; //red
    static final int UNKNOWN1 = 3;
    static final int TURN = 4; //green
    static final int MESSENGER = 5; //pink
    static final int UNKNOWN2 = 6;
    static final int ADVANCE = 7; //light blue


    static final int EAST = 0;
    static final int NORTH = 1;
    static final int WEST = 2;
    static final int SOUTH = 4;


    //Taken from http://necsi.org/postdocs/sayama/sdsr/java/loops.java
    public static final String langtonRules = new String(     // Langton Loop rules
            "000000 000012 000020 000030 000050 000063 000071 000112 000122 000132 000212 "+
                    "000220 000230 000262 000272 000320 000525 000622 000722 001022 001120 002020 "+
                    "002030 002050 002125 002220 002322 005222 012321 012421 012525 012621 012721 "+
                    "012751 014221 014321 014421 014721 016251 017221 017255 017521 017621 017721 "+
                    "025271 100011 100061 100077 100111 100121 100211 100244 100277 100511 101011 "+
                    "101111 101244 101277 102026 102121 102211 102244 102263 102277 102327 102424 "+
                    "102626 102644 102677 102710 102727 105427 111121 111221 111244 111251 111261 "+
                    "111277 111522 112121 112221 112244 112251 112277 112321 112424 112621 112727 "+
                    "113221 122244 122277 122434 122547 123244 123277 124255 124267 125275 200012 "+
                    "200022 200042 200071 200122 200152 200212 200222 200232 200242 200250 200262 "+
                    "200272 200326 200423 200517 200522 200575 200722 201022 201122 201222 201422 "+
                    "201722 202022 202032 202052 202073 202122 202152 202212 202222 202272 202321 "+
                    "202422 202452 202520 202552 202622 202722 203122 203216 203226 203422 204222 "+
                    "205122 205212 205222 205521 205725 206222 206722 207122 207222 207422 207722 "+
                    "211222 211261 212222 212242 212262 212272 214222 215222 216222 217222 222272 "+
                    "222442 222462 222762 222772 300013 300022 300041 300076 300123 300421 300622 "+
                    "301021 301220 302511 401120 401220 401250 402120 402221 402326 402520 403221 "+
                    "500022 500215 500225 500232 500272 500520 502022 502122 502152 502220 502244 "+
                    "502722 512122 512220 512422 512722 600011 600021 602120 612125 612131 612225 "+
                    "700077 701120 701220 701250 702120 702221 702251 702321 702525 702720 ");

    private String[] ruleArray;



    private String[] makeRuleArray(String str) {
        String[] ruleArray = str.split(" ");
        return ruleArray;
    }

    private int genState(String search, Cell currentCell){
        int nextState = currentCell.getCurrentState();
        for(String s : ruleArray){
            if(search.equals(s.substring(0,s.length()-1))){
                nextState = Integer.parseInt(s.substring(s.length()-1),s.length());
            }
        }
        return nextState;
    }

    public LangtonsLoopRule(){
        myNumStates = 8;
        ruleArray = makeRuleArray(langtonRules);

    }

    public int getPasses() {
        return NUM_PASSES;
    }



    @Override
    public Class getCellType() {
        return Cell.class;
    }

    public void applyRule(Cell cell, List<Cell> neighborsArray, int passNum) {
        cell.setNextState(genState(makeString(cell, neighborsArray), cell));

    }

    private String makeString(Cell cell, List<Cell> neighborsArray){
        CellManager.throwOutDiagonals(cell, neighborsArray);
        Cell north;
        Cell south;
        Cell east;
        Cell west;
        ArrayList<Cell> cellsInCol = new ArrayList<>();
        ArrayList<Cell> cellsInRow = new ArrayList<>();
        for(Cell c : neighborsArray){
            if(c.getRow() == cell.getRow()){
                cellsInRow.add(c);
            }
            else{
                cellsInCol.add(c);
            }
        }
        //establish north and south
        int cellsAbove = 0;
        int cellsBelow = 0;

        for(Cell c : cellsInCol){
            if(c.getCol() < cell.getCol()){
                cellsAbove++;
            }
            else{
                cellsBelow++;
            }
        }
        //case where 2 above
        if(cellsAbove > 1){

            if(cellsInCol.get(0).getCol() < cellsInCol.get(1).getCol()){
                north = cellsInCol.get(1);
                south = cellsInCol.get(0);
            }
            else{
                north = cellsInCol.get(0);
                south = cellsInCol.get(1);
            }
        }
        else if(cellsBelow > 1){
            if(cellsInCol.get(0).getCol() < cellsInCol.get(1).getCol()){
                north = cellsInCol.get(1);
                south = cellsInCol.get(0);
            }
            else{
                north = cellsInCol.get(0);
                south = cellsInCol.get(1);
            }
        }
        else{
            if(cellsInCol.get(0).getCol() < cellsInCol.get(1).getCol()){
                north = cellsInCol.get(0);
                south = cellsInCol.get(1);
            }
            else{
                north = cellsInCol.get(1);
                south = cellsInCol.get(0);
            }
        }

        //establish east and west
        int cellsEast = 0;
        int cellsWest = 0;

        for(Cell c : cellsInRow){
            if(c.getRow() < cell.getRow()){
                cellsEast++;
            }
            else{
                cellsWest++;
            }
        }
        //case where 2 east
        if(cellsEast > 1){

            if(cellsInRow.get(0).getRow() < cellsInRow.get(1).getRow()){
                east = cellsInCol.get(0);
                west = cellsInCol.get(1);
            }
            else{
                east = cellsInCol.get(1);
                west = cellsInCol.get(0);
            }
        }
        else if(cellsWest > 1){

            if(cellsInCol.get(0).getRow() < cellsInCol.get(1).getRow()){
                east = cellsInCol.get(0);
                west = cellsInCol.get(1);
            }
            else{
                east = cellsInCol.get(1);
                west = cellsInCol.get(0);
            }
        }
        else{
            if(cellsInRow.get(0).getRow() < cellsInRow.get(1).getRow()){
                east = cellsInRow.get(1);
                west = cellsInRow.get(0);
            }
            else{
                east = cellsInCol.get(0);
                west = cellsInCol.get(1);
            }
        }
        return (Integer.toString(cell.getCurrentState())
                + Integer.toString(north.getCurrentState())
                + Integer.toString(east.getCurrentState())
                + Integer.toString(south.getCurrentState())
                + Integer.toString(west.getCurrentState()));
    }


}
