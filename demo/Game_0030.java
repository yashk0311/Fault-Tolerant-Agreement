package demo;

import common.Game;
import common.Machine;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Random;


public class Game_0030 extends Game{

    private ArrayList<Machine> currMachines; // list of machines in simulation
    private int t; // number of faulty machines
    
    // update machine lists of all machines
    public void addMachines(ArrayList<Machine> machines, int numFaulty) {

        currMachines = new ArrayList<Machine> (machines);
        t = numFaulty;

        for(Machine m : currMachines) {

            m.setMachines(currMachines);
        }
    }

    // non-gui implementation
	public void startPhase() {

        Random rand = new Random();

        int n = currMachines.size();

        // simulate by randomly picking at most t faulty machines
        // and choosing a random leader

        ArrayList<Boolean> areCorrect = new ArrayList<> ();
        int numCorrect = rand.nextInt(t + 1);
        
        for(int i = 0; i < numCorrect; i++) {
            areCorrect.add(false);
        }
        for(int i = numCorrect; i < n; i++) {
            areCorrect.add(true);
        }

        Collections.shuffle(areCorrect);

        // System.out.println("num correct: ");
        // System.out.println(numCorrect);

        for(int i = 0; i < currMachines.size(); i++) {

            currMachines.get(i).setState(areCorrect.get(i));

            // System.out.println(areCorrect.get(i));
        }

        int leaderId = rand.nextInt(n);

        currMachines.get(leaderId).setLeader();
    }

    // gui implementation
    public void startPhase(int leaderId, ArrayList<Boolean> areCorrect) {

        // set states and leaders according to args
        
        for(int i = 0; i < currMachines.size(); i++) {

            currMachines.get(i).setState(areCorrect.get(i));
        }

        currMachines.get(leaderId).setLeader();
    }

}
