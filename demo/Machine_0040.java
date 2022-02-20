package demo;

import common.Location;
import common.Machine;

import java.util.*;

public class Machine_0040 extends Machine {

    public Machine_0040() {

        name = name();

    }

    public void run() {

        {
            while (true) {
                move();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void setMachines(ArrayList<Machine> machines) {
        this.machines = machines;

    }

    @Override
    public void setStepSize(int stepSize) {
        step = stepSize;
    }

    @Override
    public void setState(boolean iscorrect) {
        this.iscorrect = iscorrect;

    }

    public int id_finder() {

        for (int i = 0; i < machines.size(); i++) {
            if (this.equals(machines.get(i)))
                return i;

        }
        return 0;

    }

    public ArrayList<Integer> random_generator(int num) {

        ArrayList<Integer> random_numbers = new ArrayList<Integer>();

        for (int i = 0; i < num; i++) {
            random_numbers.add((int) (Math.random() * machines.size()));
        }
        for (int i = 1; i < num; i++)
            while (random_numbers.subList(0, i).contains(random_numbers.get(i)))

                random_numbers.set(i, (int) (Math.random() * machines.size()));

        return random_numbers;
    }

    @Override
    public void setLeader() {

        this.isleader = true;
        int random_direction = (int) (Math.random() * (2));

        int temp;
        if (machines.size() % 3 == 0)
            temp = this.machines.size() / 3 - 1;
        else
            temp = this.machines.size() / 3;

        int resultx = 2 * temp + 1;

        // ArrayList<Integer> x = random_generator(resultx);
        int i;
        if (this.iscorrect == true && isleader == true) {
            for (i = 0; i < machines.size(); i++) {

                machines.get(i).sendMessage(this.id_finder(), phase, 0, random_direction);

            }
        }

        if (this.iscorrect == false && isleader == true) {

            for (i = 1; i < machines.size() - 1; i++) {
                {
                    machines.get(i).sendMessage(this.id_finder(), phase, 0, random_direction);
                    // leader_decision.set(i, random_direction);
                }

            }

        }

        if (this.iscorrect == true) {

            for (i = 0; i < machines.size(); i++) {
                for (int j = 0; j < machines.size(); j++) {
                    machines.get(j).sendMessage(i, phase, 1, random_direction);
                }

            }
        }

        else {

            for (i = 1; i < machines.size() - 1; i++) {
                for (int j = 0; j < machines.size(); j++) {
                    machines.get(j).sendMessage(i, phase, 1, random_direction);
                }

            }

        }

        this.isleader = false;

    }

    @Override
    public void sendMessage(int sourceId, int phaseNum, int roundNum, int decision) {

        if (roundNum == 0) {
            this.leader_decision = decision;

            this.round = roundNum;
            this.phase = phaseNum;

        }
        if (roundNum == 1) {
            if (iscorrect == true) {

                int temp;
                if (machines.size() % 3 == 0)
                    temp = this.machines.size() / 3 - 1;
                else
                    temp = this.machines.size() / 3;

                int resultx = 2 * temp + 1;
                if (decision == 1)
                    one_round_1++;
                if (decision == 0)
                    zero_round_1++;
                turn(decision);
                if (one_round_1 + zero_round_1 >= resultx)

                    if (one_round_1 + zero_round_1 >= resultx) {

                        if (one_round_1 > zero_round_1)

                            for (int i = 0; i < machines.size(); i++) {

                                machines.get(i).sendMessage(id, phase, 2, 1);

                            }
                        else {
                            for (int i = 0; i < machines.size(); i++) {

                                machines.get(i).sendMessage(id, phase, 2, 0);

                            }
                        }

                    }

            }

            if (iscorrect == false) {

                int temp;
                if (machines.size() % 3 == 0)
                    temp = this.machines.size() / 3 - 1;
                else
                    temp = this.machines.size() / 3;

                int resultx = 2 * temp + 1;
                if (decision == 1)
                    one_round_1++;
                else
                    zero_round_1++;

                if (one_round_1 + zero_round_1 >= resultx) {

                    if (one_round_1 > zero_round_1)

                        for (int i = 0; i < machines.size(); i++) {

                            machines.get(i).sendMessage(id, phase, 2, 1);

                        }
                    else {
                        for (int i = 0; i < machines.size(); i++) {

                            machines.get(i).sendMessage(id, phase, 2, 0);

                        }
                    }

                }

            }

        }

        if (roundNum == 2) {

            round = roundNum;
            phase = phaseNum;
            if (decision == 1)
                one_round_2++;
            else
                zero_round_2++;

            int temp;
            if (machines.size() % 3 == 0)
                temp = this.machines.size() / 3 - 1;
            else
                temp = this.machines.size() / 3;

            int resultx = 2 * temp + 1;
            if (one_round_2 >= resultx)
                turn(one_round_2);

            else if (zero_round_2 >= resultx)
                turn(zero_round_2);

            leader_decision = -1;

            zero_round_1 = 0;
            one_round_1 = 0;
            zero_round_2 = 0;
            one_round_2 = 0;

            round = 0;

        }

    }

    void turn(int decisionr) {

        if (decisionr == 1) {
            // System.out.println(ands);

            int x = dir.getX();
            int y = dir.getY();

            if (dir.getY() == 0) {
                dir.setLoc(0, -x);
            } else if (dir.getY() != 0) {
                dir.setLoc(y, 0);
            }

        } else {

            int x = dir.getX();
            int y = dir.getY();

            if (dir.getX() == 0) {
                dir.setLoc(-y, 0);
            } else if (dir.getX() != 0) {
                dir.setLoc(0, x);
            }

        }
    }

    @Override
    public void move() {

        pos.setLoc(pos.getX() + dir.getX() * (step/4),
                pos.getY() + dir.getY() * (step/4));

    }

    @Override
    public String name() {
        return "0040" + String.valueOf(id_finder());

    }

    @Override
    public Location getPosition() {

        return new Location(pos.getX(), pos.getY());
    }

    private int step;
    private Location pos = new Location(0, 0);
    private Location dir = new Location(1, 0); // using Location as a 2d vector. Bad!

    private ArrayList<Machine> machines = new ArrayList<Machine>();

    String name;
    private int id;

    public int phase = -1;
    public int round;

    private boolean iscorrect = true;

    private int leader_decision = -1;
    private boolean isleader = false;

    private int zero_round_1 = 0;
    private int one_round_1 = 0;
    private int zero_round_2 = 0;
    private int one_round_2 = 0;

}
