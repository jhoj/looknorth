package fo.looknorth.productionapp.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Collection;
import java.util.List;

/**
 * Created by jakup on 4/13/16.
 */
public class Machine {
    public int machineNumber;
    public Product currentProduct;

    public Machine() {}

    public Machine(int machineNumber, Product product)
    {
        this.machineNumber = machineNumber;
        this.currentProduct = product;
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
}
