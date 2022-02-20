package demo;

import common.Machine;
import common.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Machine_0030 extends Machine {

    private int id; 
    private boolean
        isCorrect, // false if faulty, true otherwise
        isDone; // if machine has finished round 2 of protocol


    private static int faultyTargets; // number of targets ignored if leader is faulty

    private Integer round0_decision, // leader's decision
                round1_left_cnt, // number of 'left' msgs in round 1
                round1_right_cnt, // number of 'right' msgs in round 1
                round2_left_cnt,
                round2_right_cnt;

    private int stepSize;
    private Location position; 
    private Location direction; 

    private ArrayList<Machine> currMachines; // list of all machines
    private int t, // number of faulty machines
                n; // total number of machines


    public Machine_0030() {

        // starting from origin
        // towards positive x-axis

        position = new Location(0, 0);
        direction = new Location(1, 0);
    }
    
    public void setMachines(ArrayList<Machine> machines) {

        // setting up data members

        this.currMachines = machines;
        
        id = currMachines.indexOf(this);

        n = machines.size();
        t = n/3;
    }

	public void setState(boolean isCorrect) {

        // initializing round variables

        faultyTargets =
        round0_decision = 
        round1_left_cnt = 
        round1_right_cnt = 
        round2_left_cnt = 
        round2_right_cnt = 0;

        isDone = false;
        this.isCorrect = isCorrect;
    }

	public void setLeader() {

        Random rand = new Random();
        int decision = rand.nextInt(2) + 1;

        if(isCorrect == true) {
            // if not faulty
            // send single direction to all machines

            for(Machine machine : currMachines) {

                machine.sendMessage(id, 0, 0, decision);
            }

        } else {

            // else
            // at most 't' faulty targets

            ArrayList<Integer> shouldSend = new ArrayList<> ();
            faultyTargets = rand.nextInt(t);
            
            for(int i = 0; i < faultyTargets; i++) {
                shouldSend.add(0);
            }
            for(int i = faultyTargets; i < n; i++) {
                shouldSend.add(1);
            }

            Collections.shuffle(shouldSend);

            // avoid sending to targets
            for(int i = 0 ; i < n; i++) {
                if(shouldSend.get(i) == 1) {
                    
                    currMachines.get(i).sendMessage(id, 0, 0, decision);
                }
            }
        }

        this.sendMessage(id, 0, 0, decision);
    }

    public void sendMessage(int sourceId, int phaseNum, int roundNum, int decision) {

        Random rand = new Random();

        // round 0
        if(roundNum == 0) {

            round0_decision = decision;

            if(round0_decision != 0) {
                for(Machine machine : currMachines) {

                    if(isCorrect) {
                        
                        machine.sendMessage(id, 0, 1, round0_decision);
                    } else {

                        // if faulty, send random direction
                        machine.sendMessage(id, 0, 1, rand.nextInt(2) + 1);
                    }
                }
            }
        }

        // round 1
        else if(roundNum == 1) {

            if(decision == 1) {
                round1_left_cnt++;
            } else {
                round1_right_cnt++;
            }

            if(round1_left_cnt + round1_right_cnt >= n-faultyTargets-1) {

                for(Machine machine : currMachines) {

                    if(isCorrect) {
                        
                        machine.sendMessage(id, 0, 2, (round1_left_cnt > round1_right_cnt) ? 1 : 2 );
                    } else {

                        // if faulty, send random direction
                        machine.sendMessage(id, 0, 2, rand.nextInt(2) + 1);
                    }
                }
            }
        }

        // round 2
        else if(roundNum == 2) {

            // debugger();

            if(isDone) {
                return;
            }

            if(decision == 1) {
                round2_left_cnt++;
            } else {
                round2_right_cnt++;
            }

            // if end of round detected
            if(round2_left_cnt + round2_right_cnt >= n-faultyTargets-1) {

                // if(round2_left_cnt < 2*t + 1 && round2_right_cnt < 2*t + 1) {
                //     System.out.println("Error");
                //     // System.out.println(id);
                //     // System.out.println(round2_left_cnt);
                //     // System.out.println(round2_right_cnt);
                //     // System.out.println(t);
                //     // System.out.println(faultyTargets);
                //     // System.out.println("\n\n");
                // }
                
                if(false) {

                }

                else {

                    if(isCorrect == true) {

                        if(round2_left_cnt > round2_right_cnt) {

                            turnLeft();
    
                        } else {
    
                            turnRight();
                        }
                    }
                    
                    else {

                        // turn randomly if faulty 
                        
                        int x = rand.nextInt(2);

                        if(x == 0) {

                            turnLeft();

                        } else {

                            turnRight();
                        }
                    }
                }

                isDone = true;
            }
        }

    }

    // change direction clockwise
    public void turnLeft() {

        if(direction.getX() == 0 && direction.getY() == 1) {

            direction.setLoc(-1, 0);
        }
        else if(direction.getX() == 1 && direction.getY() == 0) {

            direction.setLoc(0, 1);
        }
        else if(direction.getX() == 0 && direction.getY() == -1) {
            
            direction.setLoc(1, 0);
        }
        else if(direction.getX() == -1 && direction.getY() == 0) {
            
            direction.setLoc(0, -1);
        }
    }

    // change direction anti-clockwise
    public void turnRight() {

        if(direction.getX() == 0 && direction.getY() == 1) {

            direction.setLoc(1, 0);
        }
        else if(direction.getX() == 1 && direction.getY() == 0) {

            direction.setLoc(0, -1);
        }
        else if(direction.getX() == 0 && direction.getY() == -1) {
            
            direction.setLoc(-1, 0);
        }
        else if(direction.getX() == -1 && direction.getY() == 0) {
            
            direction.setLoc(0, 1);
        }
    }

    public void debugger() {

        Integer ID = new Integer(id);
        System.out.println(
            ID.toString() 
            + "    round0: " + round0_decision.toString() 
            + ", round1: " + round1_left_cnt.toString() + "______" + round1_right_cnt.toString() 
            + ",   round2: " + round2_left_cnt.toString() + "_______" + round2_right_cnt.toString());

        System.out.print("\n\n");
    }

	public void setStepSize(int stepSize) {

        this.stepSize = stepSize;
    }
	
	protected void move() {

        position.setLoc(position.getX() + direction.getX()*stepSize, position.getY() + direction.getY()*stepSize);
    }
	
	public String name() {

        return "0030_" + id;
    }

	public Location getPosition() {

        return new Location(position.getX(), position.getY());
    }
	
}
