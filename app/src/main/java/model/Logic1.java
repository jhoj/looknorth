package model;

import android.app.Application;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jakup on 4/7/16.
 */
public class Logic1 {

    public List<Machine> machines = new ArrayList<>();

    public Logic1() {
        addTestData();
    }

    /**
     * adds 5 machines,
     * each machine has a current product, initally starting at "Not active"
     * Each machine has two productions added. i.e. it has produced two different
     */
    private void addTestData(){
        //todo remove this method when real data has been fetched
        //add five machines to the system
        for (int i = 0; i < 5; i++)
        {
            //add machines to system
            machines.add(new Machine(i+1));

            //create two productions for all machines
            Product p = machines.get(i).productList.get(i+2);
            Production production = new Production(p);
            Production production1 = new Production(p);
            machines.get(i).productionList.add(production);
            machines.get(i).productionList.add(production1);

            for (Machine machine: machines) {
                machine.productionList.get(0).productionCycles = 2;
                machine.productionList.get(1).productionCycles = 1;
                int totalProduction = machine.productionList.get(0).quantityProduced + machine.productionList.get(1).quantityProduced;
                machine.totalProducedItems = totalProduction;
            }

        }
    }

    public static void main(String[] args) {
        Logic1 l = new Logic1();
        System.out.println(l.machines.get(0).totalProducedItems);
    }
}
