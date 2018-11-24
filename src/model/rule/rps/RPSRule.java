package model.rule.rps;

import model.Cell;
import model.rule.Rule;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This simulates bacterial growth with RPS autoinducers. Bacteria secrete autoinducers and can only reproduce when
 * autoinducer levels in adjacent cells do not inhibit it.
 * @author Christopher Lin cl349
 */

public class RPSRule extends Rule {

    public static final int EMPTY = 0;
    public static final int ROCK = 1;
    public static final int PAPER = 2;
    public static final int SCISSORS = 3;

    public static final int INHIBITION_THRESHOLD = 20;
    public static final double DIFFUSION_RATE = 0.3;
    public static final double EMISSION_AMT = 5;
    public static final double DECAY_RATE = 0.3;

    public RPSRule(){
        myNumStates = 4;
    }

    @Override
    public void applyRule(Cell cell, List<Cell> neighbors, int passNum) {
        //Update RPSCell bacteria given cell states. Should only run once on initialization
        if(cell.getCurrentState() != 0 && ((RPSCell) cell).getBacteria() == null){
            ((RPSCell) cell).setBacteria(new Bacteria(cell.getCurrentState()));
        }
        killBacteria(cell);

        //decay AI particles
        ((RPSCell) cell).setRock_level(((RPSCell) cell).getRock_level()*(1-DECAY_RATE));
        ((RPSCell) cell).setPaper_level(((RPSCell) cell).getPaper_level()*(1-DECAY_RATE));
        ((RPSCell) cell).setScissors_level(((RPSCell) cell).getScissors_level()*(1-DECAY_RATE));
        if(neighbors.size() > 0){
            diffuseAIParticles((RPSCell) cell, neighbors);
            reproduceBacteria((RPSCell) cell, neighbors);
            emitAIParticles((RPSCell) cell, neighbors);
            if(((RPSCell) cell).getBacteria() == null){
                cell.setNextState(EMPTY);
            }
            else{
                ((RPSCell) cell).getBacteria().incAge();
                cell.setNextState(((RPSCell) cell).getBacteria().getType());
            }
        }
    }

    private void emitAIParticles(RPSCell cell, List<Cell> neighbors) {
        if(cell.getBacteria() == null){
            return;
        }
        for(Cell c : neighbors){
            if(cell.getBacteria().getType() == ROCK){
                ((RPSCell) c).setRock_level(((RPSCell) c).getRock_level()+EMISSION_AMT);
            }
            else if(cell.getBacteria().getType() == PAPER){
                ((RPSCell) c).setPaper_level(((RPSCell) c).getPaper_level()+EMISSION_AMT);
            }
            else if(cell.getBacteria().getType() == SCISSORS){
                ((RPSCell) c).setScissors_level(((RPSCell) c).getScissors_level()+EMISSION_AMT);
            }
        }
    }

    private void killBacteria(Cell cell) {
        if(((RPSCell) cell).getBacteria() != null){
            if(((RPSCell) cell).getBacteria().getAge() > Bacteria.MAX_AGE){
                ((RPSCell) cell).setBacteria(null);
                cell.setNextState(EMPTY);
            }
        }
    }

    private void reproduceBacteria(RPSCell cell, List<Cell> neighbors) {
        if(cell.getBacteria() != null){
            boolean inhibited = false;
            for(Cell c : neighbors){
                if(cell.getBacteria().getType()==ROCK && ((RPSCell) c).getPaper_level() > INHIBITION_THRESHOLD){
                    inhibited = true;
                }
                else if(cell.getBacteria().getType()==PAPER && ((RPSCell) c).getScissors_level() > INHIBITION_THRESHOLD){
                    inhibited = true;
                }
                else if(cell.getBacteria().getType()==SCISSORS && ((RPSCell) c).getRock_level() > INHIBITION_THRESHOLD){
                    inhibited = true;
                }
            }
            int reproduceLoc = ThreadLocalRandom.current().nextInt(0, neighbors.size());
            Cell reproduceCell = neighbors.get(reproduceLoc);
            if(((RPSCell) reproduceCell).getBacteria() == null && !inhibited){
                ((RPSCell) reproduceCell).setBacteria(new Bacteria(cell.getBacteria().getType()));
            }

        }
    }

    private void diffuseAIParticles(RPSCell cell, List<Cell> neighbors) {
        if(neighbors.size() <= 0){
            return;
        }

        for(Cell c : neighbors){
            System.out.println(neighbors.size());
            ((RPSCell) c).setRock_level(((RPSCell) c).getRock_level()+ cell.getRock_level()*(DIFFUSION_RATE/neighbors.size()));
            ((RPSCell) c).setPaper_level(((RPSCell) c).getPaper_level()+ cell.getPaper_level()*(DIFFUSION_RATE/neighbors.size()));
            ((RPSCell) c).setScissors_level(((RPSCell) c).getScissors_level()+ cell.getScissors_level()*(DIFFUSION_RATE/neighbors.size()));

        }
        cell.setRock_level(cell.getRock_level()*(1-DIFFUSION_RATE));
        cell.setPaper_level(cell.getPaper_level()*(1-DIFFUSION_RATE));
        cell.setScissors_level(cell.getScissors_level()*(1-DIFFUSION_RATE));
    }

    @Override
    public Class getCellType() {
        return RPSCell.class;
    }

    @Override
    public int getPasses() {
        return 1;
    }
}
