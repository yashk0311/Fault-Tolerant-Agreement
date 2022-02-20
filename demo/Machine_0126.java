package demo;

import java.util.*;

import common.Location;
import common.Machine;

public class Machine_0126 extends Machine {

	public Machine_0126() {
		id = nextId++;
		this.setName("0126");		
	}

	@Override
	public void setStepSize(int stepSize) {
		step = stepSize;
		return;
	}

	@Override
	public void setState(boolean isCorrect) {
		this.isCorrect = isCorrect;
		return;
	}

	@Override
	public void setLeader() {
		isLeader = true;
		return;
	}

	@Override
	public int getid(){
		return id;
	}

	@Override
	public boolean isFaulty(){
		return !isCorrect;
	}

	@Override
	public boolean isLeader(){
		return !isLeader;
	}

	@Override
	public void sendMessage(int sourceId, int phaseNum, int roundNum, int decision) {
		if(roundNum==0)
			round_result = decision;
		else if(roundNum == 1)
			decisions.add(decision);
		else
			decisions_r2.add(decision);
		return;
	}

	@Override
	public void turn(int decision){
		if(decision == 1){
			if(dir.getY()==0)
				dir.setLoc(dir.getY(), dir.getX());
			else
				dir.setLoc(-1*dir.getY(), -1*dir.getX());
		}
		else{
			if(dir.getY()==0)
				dir.setLoc(-1*dir.getY(), -1*dir.getX());
			else
				dir.setLoc(dir.getY(), dir.getX());
		}
	}

	@Override
	public void move() {
		pos.setLoc(pos.getX() + dir.getX()*step, 
					pos.getY() + dir.getY()*step);
		return;
			
	}

	@Override
	public String name() {
		return "Machine "+id;
	}

	@Override
	public Location getPosition() {
		
		return new Location(pos.getX(), pos.getY());
	}
	@Override
	public void setMachines(ArrayList<Machine> machines) {
		machine_list = machines;
		return;
	}

	@Override
	public ArrayList<Integer> getDecisions(int roundNum){
		if(roundNum==1)
			return decisions;
		else
			return decisions_r2;
	}

	@Override
	public void reset(){
		isLeader = false;
		isCorrect = true;
		round_result = 0;
		decisions.clear();
		decisions_r2.clear();
		return;
	}


	private int step;
	private Location pos = new Location(0,0);
	private Location dir = new Location(1,0); // using Location as a 2d vector. Bad!
	private static int nextId = 0;
	private int id;
	private boolean isCorrect = true;
	private boolean isLeader = false;
	private ArrayList<Machine> machine_list = new ArrayList<>();
	private int round_result = 0;
	private ArrayList<Integer> decisions = new ArrayList<>();
	private ArrayList<Integer> decisions_r2 = new ArrayList<>();

}
