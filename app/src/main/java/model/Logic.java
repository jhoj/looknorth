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

        int quantity = 2;
        int quantity1 = 1;

        Product p1 = new Product(1, "Test Product 1", quantity);
        Product p2 = new Product(2, "Test Product 2", quantity1);
        //todo remove this test method when working
        Production p1m1 = new Production(p1);
        Production p2m1 = new Production(p2);

        p1m1.productionCycles = 1;
        p2m1.productionCycles = 1;

        //m1 p1 total = 2 * 1
        p1m1.updateQuantityProduced();
        //m1 p2 total = 1 * 1
        p2m1.updateQuantityProduced();
        //production for machine 1
        machines.get(0).productionList.add(p1m1);
        machines.get(0).productionList.add(p2m1);
    }
}
