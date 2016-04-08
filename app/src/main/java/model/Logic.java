package model;

import android.app.Application;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jakup on 4/7/16.
 */
public class Logic extends Application{

    public static Logic instance;
    public List<Machine> machines = new ArrayList<>();
    //forcing to use private, so that a updated string is always returned
    private String date;

    public Logic() {
        instance = this;

        machines.add(new Machine(1));
        machines.add(new Machine(2));
        machines.add(new Machine(3));
        machines.add(new Machine(4));
        machines.add(new Machine(5));

        addTestData();
    }

    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        return sdf.format(Calendar.getInstance().getTime());
    }


    private void addTestData(){

        Product p1 = new Product(1, "Test Product 1", 2);
        Product p2 = new Product(2, "Test Product 2", 1);
        //todo remove this test method when working
        Production p1m1 = new Production(p1);
        Production p1m2 = new Production(p2);
        Production p1m3 = new Production(machines.get(0).productList.get(2));
        Production p2m1 = new Production(machines.get(0).productList.get(3));
        Production p2m2 = new Production(machines.get(0).productList.get(4));
        Production p3m3 = new Production(machines.get(0).productList.get(5));
        Production p3m4 = new Production(machines.get(0).productList.get(6));
        Production p1m5 = new Production(machines.get(0).productList.get(7));

        p1m1.productionCycles = 1;
        p1m2.productionCycles = 1;
        p1m3.productionCycles = 2;
        p2m1.productionCycles = 4;
        p2m2.productionCycles = 5;
        p3m3.productionCycles = 8;
        p3m4.productionCycles = 5;
        p1m5.productionCycles = 99;

        p1m1.updateQuantityProduced();
        p1m2.updateQuantityProduced();
        p1m3.updateQuantityProduced();
        p2m1.updateQuantityProduced();
        p2m2.updateQuantityProduced();
        p3m3.updateQuantityProduced();
        p3m4.updateQuantityProduced();
        p1m5.updateQuantityProduced();

        //production for machine 1
        machines.get(0).productionList.add(p1m1);
        machines.get(0).productionList.add(p2m1);

        //machine 2
        machines.get(1).productionList.add(p2m2);
        machines.get(1).productionList.add(p1m2);
        //3
        machines.get(2).productionList.add(p1m3);
        machines.get(2).productionList.add(p3m3);
        //4
        machines.get(3).productionList.add(p3m4);
        //5
        machines.get(4).productionList.add(p1m5);

        for (Production p: machines.get(0).productionList) {
            machines.get(0).totalProducedItems += p.quantityProduced;
        }
        System.out.println(machines.get(0).totalProducedItems);
    }

    public static void main(String[] args) {
        Logic l = new Logic();
        System.out.println(l.machines.get(0).totalProducedItems);
    }
}
