package fo.looknorth.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by jakup on 4/13/16.
 */
public class Production implements IProductionRepository {

    private int id;
    private int machineId;
    private int productId;
    private String recorded;

    public Production() {}
    public Production(int id, int machineId, int productId, Timestamp recorded) {
        this.id = id;
        this.machineId = machineId;
        this.productId = productId;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd HH:MM:SS");
        this.recorded = sdf.format(new Date(recorded.getTime()));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getRecorded() {
        return recorded;
    }

    public void setRecorded(String recorded) {
        this.recorded = recorded;
    }

    public List<Production> getDbProduction() {
        List<Production> productionList = null;

        try {
            InputStream is = new URL("http://localhost:4567/production").openStream();

            byte b[] = new byte[is.available()]; // kun små filer
            is.read(b);
            String str = new String(b, "UTF-8");
            Gson gson = new Gson();
            Type productionType = new TypeToken<Collection<Production>>(){}.getType();
            productionList =  gson.fromJson(str, productionType);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return productionList;
    }

    @Override
    public String toString() {
        return "Production{" +
                "id=" + id +
                ", machineId=" + machineId +
                ", productId=" + productId +
                ", recorded='" + recorded + '\'' +
                '}';
    }

    @Override
    public List<Production> getProductions() {
        return new Production().getDbProduction();
    }
}
