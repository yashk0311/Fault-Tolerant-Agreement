package demo;

import java.util.*;

import common.Game;
import common.Machine;

public class Game_0033 extends Game {

	private ArrayList<Machine> mac = new ArrayList<Machine>();
	private int numFault;

	@Override
	public void addMachines(ArrayList<Machine> machines, int numFaulty) {
		mac = machines;
		numFault = numFaulty;
	}

	@Override
	public void startPhase() {
		ArrayList<Integer> faulty_machines=new ArrayList<Integer>();
		Random rand =new Random();

		while(faulty_machines.size()!=numFault)
		{
			int k=rand.nextInt(mac.size());
			if(faulty_machines.contains(k)==false)
				faulty_machines.add(k);
		}

		for(int i=0;i<mac.size();i++)
		{
			if(faulty_machines.contains(i)==true)
				mac.get(i).setState(false);

			else	
				mac.get(i).setState(true);
			
			mac.get(i).setMachines(mac);
		}

		int l=rand.nextInt(mac.size());
		mac.get(l).setLeader();
	}

}
