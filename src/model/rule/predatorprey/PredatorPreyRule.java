package model.rule.predatorprey;

import model.Cell;
import model.CellManager;
import model.rule.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Extension of main.model.rule.Rule to apply rules specifically for SegregationLife
 * Returns nextState for myCell
 *
 * @author Scott McConnell skm44
 * @author Christopher Lin cl349
 **/


public class PredatorPreyRule extends Rule {

    final static int PASSES = 2;
    static final int EMPTY = 0;
    static final int SHARK = 1;
    static final int FISH = 2;
    static int FISH_REPRODUCTION_TIME = 4;
    static int SHARK_REPRODUCTION_TIME = 12;
    private ArrayList<Fish> fishList;
    private ArrayList<Shark> sharkList;

    public int getFishReproductionTime(){
        return FISH_REPRODUCTION_TIME;
    }

    public int getSharkReproductionTime(){
        return SHARK_REPRODUCTION_TIME;
    }

    public void setFishReproductionTime(int time){
        FISH_REPRODUCTION_TIME = time;
    }

    public void setSharkReproductionTime(int time){
        SHARK_REPRODUCTION_TIME = time;
    }

    public PredatorPreyRule() {
        fishList = new ArrayList<>();
        sharkList = new ArrayList<>();
        myNumStates = 3;
    }

    public int getPasses() {
        return PASSES;
    }

    @Override
    public Class<Cell> getCellType() {
        return Cell.class;
    }

    public void applyRule(Cell cell, List<Cell> neighborsArray, int passNum) {
        if (passNum == 0) {
            //maintain parity between cells and list of sharks/fish
            updateLists(cell);
        }
        if (passNum == 1) {
            //throw out diagonal cells
            CellManager.throwOutDiagonals(cell, neighborsArray);
            if (cell.getNextState() == FISH) {
                handleFish(cell, neighborsArray);

            }
            if (cell.getNextState() == SHARK) {
                handleShark(cell, neighborsArray);
            }
        }
    }

    private void handleShark(Cell cell, List<Cell> neighborsArray) {
        Shark currentShark = getCurrentShark(cell);
        if (currentShark != null && !killShark(currentShark)) {
            reproduceShark(currentShark, neighborsArray);
            sharkEatFish(currentShark, neighborsArray);
        }
    }

    private void handleFish(Cell cell, List<Cell> neighborsArray) {
        Fish currentFish = getCurrentFish(cell);
        if (currentFish != null) {
            reproduceFish(currentFish, neighborsArray);
            moveFish(currentFish, neighborsArray);
        }
    }

    private boolean killShark(Shark shark) {
        shark.setEnergy(shark.getEnergy() - 1);
        if (shark.getEnergy() <= 0) {
            shark.getCell().setNextState(EMPTY);
            sharkList.remove(shark);
            return true;
        }
        return false;
    }

    private void updateLists(Cell cell) {
        if (cell.getCurrentState() == FISH) {
            for (Fish f : fishList) {
                if (f.getCell() == cell) {
                    return;
                }
            }
            fishList.add(new Fish(cell));
        }
        if (cell.getCurrentState() == SHARK) {
            for (Shark s : sharkList) {
                if (s.getCell() == cell) {
                    return;
                }
            }
            sharkList.add(new Shark(cell));
        }
    }


    private void moveFish(Fish fish, List<Cell> neighbors) {
        var emptyAdjacent = new ArrayList<Cell>();
        for (Cell c : neighbors) {
            if (c.getNextState() == EMPTY) {
                emptyAdjacent.add(c);
            }
        }
        if (emptyAdjacent.size() > 0) {
            fish.getCell().setNextState(EMPTY);
            Cell destinationCell = emptyAdjacent.get(ThreadLocalRandom.current().nextInt(0, emptyAdjacent.size()));
            destinationCell.setNextState(FISH);
            fish.setCell(destinationCell);
        }
    }

    private Fish getCurrentFish(Cell cell) {
        Fish currentFish = null;
        for (Fish f : fishList) {
            if (f.getCell() == cell) {
                currentFish = f;
            }
        }
        return currentFish;
    }

    private void reproduceFish(Fish fish, List<Cell> neighbors) {
        fish.setAge(fish.getAge() + 1);
        var emptyAdjacent = new ArrayList<Cell>();
        for (Cell c : neighbors) {
            if (c.getNextState() == EMPTY) {
                emptyAdjacent.add(c);
            }
        }

        if (fish.getAge() > FISH_REPRODUCTION_TIME && emptyAdjacent.size() > 0) {
            Cell destinationCell = emptyAdjacent.get(ThreadLocalRandom.current().nextInt(0, emptyAdjacent.size()));
            fishList.add(new Fish(destinationCell));
            destinationCell.setNextState(FISH);
            fish.setAge(0);
        }
    }

    private void sharkEatFish(Shark shark, List<Cell> neighbors) {
        var fishNeighbors = new ArrayList<Cell>();
        for (Cell c : neighbors) {
            if (c.getNextState() == FISH) {
                fishNeighbors.add(c);
            }
        }
        if (fishNeighbors.size() > 0) {
            Cell fishToEat = fishNeighbors.get(ThreadLocalRandom.current().nextInt(0, fishNeighbors.size()));
            for (int i = 0; i < fishList.size(); i++) {
                if (fishList.get(i).getCell() == fishToEat) {
                    fishList.remove(fishList.get(i));
                }
            }
            fishToEat.setNextState(EMPTY);
            shark.setEnergy(shark.getEnergy() + Fish.ENERGY_YIELD);
        } else {
            moveShark(shark, neighbors);
        }
    }

    private void moveShark(Shark shark, List<Cell> neighbors) {
        var emptyAdjacent = new ArrayList<Cell>();
        for (Cell c : neighbors) {
            if (c.getNextState() == EMPTY) {
                emptyAdjacent.add(c);
            }
        }
        if (emptyAdjacent.size() > 0) {
            shark.getCell().setNextState(EMPTY);
            Cell destinationCell = emptyAdjacent.get(ThreadLocalRandom.current().nextInt(0, emptyAdjacent.size()));
            destinationCell.setNextState(SHARK);
            shark.setCell(destinationCell);
        }
    }

    private void reproduceShark(Shark shark, List<Cell> neighbors) {
        shark.setAge(shark.getAge() + 1);
        var emptyAdjacent = new ArrayList<Cell>();
        for (Cell c : neighbors) {
            if (c.getNextState() == EMPTY) {
                emptyAdjacent.add(c);
            }
        }
        if (shark.getAge() > SHARK_REPRODUCTION_TIME && emptyAdjacent.size() > 0) {
            Cell destinationCell = emptyAdjacent.get(ThreadLocalRandom.current().nextInt(0, emptyAdjacent.size()));
            sharkList.add(new Shark(destinationCell));
            shark.setAge(0);
        }
    }

    private Shark getCurrentShark(Cell cell) {
        Shark currentShark = null;
        for (Shark s : sharkList) {
            if (s.getCell() == cell) {
                currentShark = s;
            }
        }
        return currentShark;
    }


}
