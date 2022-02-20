package demo;

import java.util.*;

import common.Location;
import common.Machine;

public class Machine_0033 extends Machine {

	private String name;
	private ArrayList<Machine> macs = new ArrayList<Machine>();
	private int no_of_left1, no_of_right1, no_of_left2, no_of_right2;
	private int sourceID, steps, numfaulty, next_move, phase;
	private Location loc;
	private int currdir; // 1 is up, 2 is down, 3 is right, 4 is left.
	private int leadership, n1, n2;
	private boolean isCorrect;

	public Machine_0033() {
		name = "0033";
		loc = new Location(0, 0);
		leadership = 0;
		n1 = n2 = 0;
		no_of_right2 = no_of_right1 = no_of_left2 = no_of_left1 = phase = 0;
		currdir = 3;
	}

	@Override
	public void setStepSize(int stepSize) {
		steps = stepSize;
	}

	@Override
	public void setState(boolean isCorrect) { //setting the initial states of the machine.
		n1 = n2 = 0;
		leadership = 0;
		no_of_right2 = no_of_right1 = no_of_left1 = no_of_left2 = 0;
		this.isCorrect = isCorrect;
	}

	@Override
	public void setLeader() { 
		//System.out.println("Phase " + phase + " : ................");
		leadership = 1; 
		Random rand = new Random();
		if (isCorrect == true) { //if the leader is not faulty
			int k;
			k = rand.nextInt(2); //left or right
			for (int i = 0; i < macs.size(); i++)
				macs.get(i).sendMessage(sourceID, phase, 0, k); //sending the message to all the machines
		}

		else { //if it is faulty
			int t;  //selecting t number of machines to send the message 
			if (numfaulty != 0)
				t = rand.nextInt(numfaulty);
			else
				t = 0; 
			int k;
			ArrayList<Integer> faulty_macs = new ArrayList<Integer>();
			while (faulty_macs.size() != t) {
				k = rand.nextInt(macs.size());
				if (faulty_macs.contains(k) == false) //keeping track of all the faulty machines
					faulty_macs.add(k);
			}
		}
	}

	@Override
	public void sendMessage(int sourceId, int phaseNum, int roundNum, int decision) {
		Random rand = new Random();
		if (roundNum == 0) {
			if (isCorrect == false) //if the machine is faulty then randomize the decision and propagate
				decision = rand.nextInt(3); //2 means it will not propagate the message (staying idle)

			for (int i = 0; i < macs.size(); i++) {
				if (decision != 2)
					macs.get(i).sendMessage(this.sourceID, phase, 1, decision); //moving on to the next round
			}
		}

		else if (roundNum == 1) { //increasing the counter for respective turn for voting at the end.
			if (decision == 1)
				no_of_right1++;

			else if (decision == 0)
				no_of_left1++;

			if (no_of_left1 + no_of_right1 >= 2 * numfaulty + 1) {
				if (isCorrect == false)
					decision = rand.nextInt(3);

				else if (isCorrect == true) {
					if (no_of_right1 < no_of_left1)
						next_move = 0;
					else
						next_move = 1;
				}

				if (next_move != 2 && n1 == 0) {
					for (int i = 0; i < macs.size(); i++)
						macs.get(i).sendMessage(this.sourceID, phase, 2, next_move);

					n1 = 1;
				}
			}
		}

		else if (roundNum == 2) {
			if (decision == 1)
				no_of_right2++;

			else if (decision == 0)
				no_of_left2++;

			if (no_of_right2 >= 2 * numfaulty + 1 || no_of_left2 >= 2 * numfaulty + 1) {
				if (no_of_right2 > no_of_left2)
					next_move = 1;
				else
					next_move = 0;

				if (isCorrect == false)
					next_move = rand.nextInt(2);
				n2 = 1;
			}
		}
	}

	@Override
	public void move() {
		if (n2 == 1) {
			switch (currdir) {
				case 1:
					if (next_move == 0)
						currdir = 4;

					else if (next_move == 1)
						currdir = 3;

					break;

				case 2:
					if (next_move == 1)
						currdir = 4;

					else if (next_move == 0)
						currdir = 3;

					break;

				case 3:
					if (next_move == 0)
						currdir = 1;

					else if (next_move == 1)
						currdir = 2;

					break;

				case 4:
					if (next_move == 1)
						currdir = 1;

					else if (next_move == 0)
						currdir = 2;

					break;
			}
			phase++;
			n2 = 0;
		}

		if (currdir == 1)
			loc.setLoc(loc.getX(), loc.getY() + steps);

		else if (currdir == 2)
			loc.setLoc(loc.getX(), loc.getY() - steps);

		else if (currdir == 3)
			loc.setLoc(loc.getX() + steps, loc.getY());

		else if (currdir == 4)
			loc.setLoc(loc.getX() - steps, loc.getY());
	}

	@Override
	public String name() {
		return "0033";
	}

	@Override
	public Location getPosition() {

		return loc;
	}

	@Override
	public void setMachines(ArrayList<Machine> machines) {
		macs = machines;
		if (macs.size() % 3 != 0) 
			numfaulty = macs.size() % 3;

		else if (macs.size() % 3 == 0)
			numfaulty = (macs.size() % 3) - 1; // as it is strictly less than 3t

		for (int i = 0; i < macs.size(); i++)
			if (macs.get(i) == this)
				sourceID = i;
	}

}
