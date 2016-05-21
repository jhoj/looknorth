package fo.looknorth.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by jakup on 4/13/16.
 */
public class Machine implements IMachineRepository, Comparable<Machine> {
    public int machineNumber;
    public String machineName;
    public Product currentProduct;
    public ArrayList<ProductionCounter> productionCounterList;

    public Machine() {}

    public Machine(int machineNumber, Product product)
    {
        this.machineName = "Machine " + machineNumber;
        this.machineNumber = machineNumber;
        this.currentProduct = product;
    }

    public int getTotalProducedItems()
    {
        int total = 0;
        for (ProductionCounter p: productionCounterList)
        {
            total += p.quantityProduced;
        }
        return total;
    }

    public List<Machine> getDbMachines() {
        List<Machine> machines = null;

        try {
            InputStream is = new URL("http://localhost:4567/machine").openStream();

            byte b[] = new byte[is.available()]; // kun små filer
            is.read(b);
            String str = new String(b, "UTF-8");
            Gson gson = new Gson();
            Type machineType = new TypeToken<Collection<Machine>>(){}.getType();
            machines =  gson.fromJson(str, machineType);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return machines;
    }

    @Override
    public String toString() {
        return "Machine{" +
                "machineNumber=" + machineNumber +
                ", machineName='" + machineName + '\'' +
                ", currentProduct=" + currentProduct +
                ", productionCounterList=" + productionCounterList +
                '}';
    }

    @Override
    public List<Machine> getMachines() {
        return new Machine().getDbMachines();
    }

    @Override
    public int compareTo(Machine another) {
        Integer thisOne = this.machineNumber;
        Integer anotherOne = another.machineNumber;

        return thisOne.compareTo(anotherOne);
    }




    public static void main(String[] args) {

        //compare machines

        List<Machine> machines = new ArrayList<>();

        //add them to list in reverse machine number.
        for (int i = 5; i > 0; i--) {
            Machine m = new Machine();
            m.machineNumber = i;
            machines.add(m);
        }

        for (Machine m: machines) {
            System.out.println("Number " + m.machineNumber);
        }

        Collections.sort(machines);

        for (Machine m: machines) {
            System.out.println(m.machineNumber);
        }
    }
}
