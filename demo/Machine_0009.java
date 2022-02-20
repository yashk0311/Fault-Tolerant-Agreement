package demo;

import common.*;
import java.util.*;
public class Machine_0009 extends Machine {
    
    public Machine_0009() {
        machineList = new ArrayList<Machine>();
        phase=0;
        r1leftCount=0;
        r1rightCount=0;
        r2leftCount=0;
        r2rightCount=0;
        round1=true;
    }

    @Override 
    public void setMachines(ArrayList<Machine> machines) {
        machineList.addAll(machines);
        // System.out.println(machines.size());
    }

    @Override
    public void setState(boolean isCorrect1) {
        isCorrect = isCorrect1;
    }

    @Override
    public void setLeader() {
        // System.out.println("new" + phase);
        double div = machineList.size()/3.0;
        t=(int)Math.floor(div);


        ArrayList<Boolean> send = new ArrayList<Boolean>();
        int min = 0;
        int max = t;
        int rand = (int) (Math.random() * (max - min + 1) + min); // set the number of machines the leader sends consistent messages

        int mind = 0;
        int maxd = 1;
        int decision = (int) (Math.random() * (maxd - mind + 1) + mind); // set the decision 


        for(int i = 0; i <rand; i++) send.add(false);
        for(int i = rand; i <machineList.size(); i++) send.add(true);
        Collections.shuffle(send);
        // System.out.println(send.size());
        phase++;
        // System.out.println(machineList.size());
        for(int i = 0; i <machineList.size(); i++){
            // System.out.println("m");
            if(isCorrect) machineList.get(i).sendMessage((int)getId(), phase, 0, decision);
            else if(send.get(i)) machineList.get(i).sendMessage((int)getId(), phase, 0, decision);
        }
    }

    @Override
    public void sendMessage(int sourceId, int phaseNum, int roundNum, int decision){
        double div = machineList.size()/3.0;
        t=(int)Math.floor(div);

        if(roundNum==0){
            // System.out.println("l");
            phase++;
            int min = 0;
            int max = 1;
            int rand = (int) (Math.random() * (max - min + 1) + min);
            int send = (int) (Math.random() * (max - min + 1) + min);
            for (Machine machine : machineList) {
                if (isCorrect)
                    machine.sendMessage((int)getId(), phase, 1 , decision);
                else {
                    if (send == 1) {
                        machine.sendMessage((int)getId(), phase, 1 , rand);
                    }
                }
            }
        }

        else if(roundNum==1){
            if(round1)
            {    
                if (decision == 0) {
                    r1leftCount++;
                } 
                else if (decision == 1) {
                    r1rightCount++;
                }

                if(r1leftCount+r1rightCount == 2*t+1){
                    round1 = false;
                    int round1decision;
                    if(r1leftCount>r1rightCount) round1decision = 0;
                    else round1decision = 1; 
                    for(Machine machine: machineList) {
                        machine.sendMessage((int)getId(), phase, 2, round1decision);
                    }
                }
            }
        }

        else if(roundNum == 2){    
            if (decision == 0) {
                r2leftCount++;
            } 
            else if (decision == 1) {
                r2rightCount++;
            }

            if(r2leftCount+r2rightCount == machineList.size()){

                if(r2leftCount>= 2*t+1){
                    // System.out.println(getId()+" Left");
                    if (direction.getX()==0 && direction.getY()==1) direction.setLoc(-1, 0);
                    else if (direction.getX()==-1 && direction.getY()==0) direction.setLoc(0, -1);
                    else if (direction.getX()==0 && direction.getY()==-1) direction.setLoc(1, 0);
                    else if (direction.getX()==1 && direction.getY()==0) direction.setLoc(0, 1);
                    // move();
                    
                    // System.out.println(r2leftCount + " " + t); 
                    // 0,1 to -1,0 
                    // -1,0 to 0,-1
                    // 0,-1 to 1,0
                    // 1,0 to 0,1
                    // round2=false;
                }
                else if (r2rightCount>= 2*t+1){
                    // System.out.println(getId() +"Right");
                    if (direction.getX()==-1 && direction.getY()==0) direction.setLoc(0, 1);
                    else if (direction.getX()==0 && direction.getY()==-1) direction.setLoc(-1, 0);
                    else if (direction.getX()==1 && direction.getY()==0) direction.setLoc(0, -1);
                    else if (direction.getX()==0 && direction.getY()==1) direction.setLoc(1, 0);
                    // move();

                    // System.out.println("r"+r2rightCount + " " + t);
                    // round2=false;
                }
                r2rightCount=0;
                r2leftCount=0;
                r1leftCount=0;
                r1rightCount=0;
                round1=true;
            }
        }
    }

    @Override
    public Location getPosition(){
        return location;
    }

    @Override
    public String name(){
        return "0009";
    }

    @Override
    protected void move(){
        location.setLoc(location.getX() + direction.getY()*stepSize, location.getY() + direction.getX()*stepSize);
        // System.out.println(getId() + "step" + stepSize);
    }

    @Override
    public void setStepSize(int stepSize1){
        stepSize=stepSize1;
    }

    private ArrayList<Machine> machineList;
    private int t;
    private boolean isCorrect;
    private int phase;
    private int r1leftCount;
    private int r1rightCount;
    private int r2leftCount;
    private int r2rightCount;
    private Location direction = new Location(1,0);
    private Location location = new Location(0,0);
    private int stepSize;
    private boolean round1; 
}