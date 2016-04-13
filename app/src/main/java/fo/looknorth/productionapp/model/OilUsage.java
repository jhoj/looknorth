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
public class OilUsage {

    private int id;
    private int machineId;
    private float liters;
    private String recorded;

    public OilUsage(int id, int machineId, float liters, String recorded) {
        this.id = id;
        this.machineId = machineId;
        this.liters = liters;
        this.recorded = recorded;
    }

    public OilUsage() {
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

    public float getLiters() {
        return liters;
    }

    public void setLiters(float liters) {
        this.liters = liters;
    }

    public String getRecorded() {
        return recorded;
    }

    public void setRecorded(String recorded) {
        this.recorded = recorded;
    }

    public List<OilUsage> getDbOilUsage() {
        List<OilUsage> oilUsageList = null;

        try {
            InputStream is = new URL("http://localhost:4567/oil-usage").openStream();

            byte b[] = new byte[is.available()]; // kun små filer
            is.read(b);
            String str = new String(b, "UTF-8");
            Gson gson = new Gson();
            Type oilUsageType = new TypeToken<Collection<Machine>>(){}.getType();
            oilUsageList =  gson.fromJson(str, oilUsageType);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return oilUsageList;
    }

}
