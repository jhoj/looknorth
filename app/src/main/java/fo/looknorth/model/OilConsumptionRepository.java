package fo.looknorth.model;

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
public class OilConsumptionRepository implements IOilConsumptionRepository {

    private int id;
    private int machineId;
    private float liters;
    private String recorded;

    public OilConsumptionRepository(int id, int machineId, float liters, String recorded) {
        this.id = id;
        this.machineId = machineId;
        this.liters = liters;
        this.recorded = recorded;
    }

    public OilConsumptionRepository() {
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

    private List<OilConsumptionRepository> getDbOilConsumption() {
        List<OilConsumptionRepository> oilConsumptionList = null;

        try {
            InputStream is = new URL("http://localhost:4567/oil-consumption").openStream();

            byte b[] = new byte[is.available()]; // kun små filer
            is.read(b);
            String str = new String(b, "UTF-8");
            Gson gson = new Gson();
            Type oilUsageType = new TypeToken<Collection<OilConsumptionRepository>>(){}.getType();
            oilConsumptionList =  gson.fromJson(str, oilUsageType);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return oilConsumptionList;
    }

    @Override
    public String toString() {
        return "OilConsumption{" +
                "id=" + id +
                ", machineId=" + machineId +
                ", liters=" + liters +
                ", recorded='" + recorded + '\'' +
                '}';
    }

    @Override
    public List<OilConsumptionRepository> getOilConsumptions() {
        return new OilConsumptionRepository().getDbOilConsumption();
    }

    @Override
    public OilConsumptionRepository getLastEntry() {
        OilConsumptionRepository oilConsumption = null;

        try {
            InputStream is = new URL("http://localhost:4567/oil-consumption/last").openStream();

            byte b[] = new byte[is.available()]; // kun små filer
            is.read(b);
            String str = new String(b, "UTF-8");
            Gson gson = new Gson();
            oilConsumption = gson.fromJson(str, OilConsumptionRepository.class);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return oilConsumption;
    }

    @Override
    public OilConsumptionRepository getLastEntry(String url) {
        OilConsumptionRepository oilConsumption = null;

        try {
            InputStream is = new URL(url).openStream();

            byte b[] = new byte[is.available()]; // kun små filer
            is.read(b);
            String str = new String(b, "UTF-8");
            Gson gson = new Gson();
            oilConsumption = gson.fromJson(str, OilConsumptionRepository.class);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return oilConsumption;
    }

    public static void main(String[] args) {
        OilConsumptionRepository o = new OilConsumptionRepository();

        List<OilConsumptionRepository> list = o.getOilConsumptions();

        for (OilConsumptionRepository o1 : list) {
            System.out.println(o1.toString());
        }

        OilConsumptionRepository o1 = o.getLastEntry();
        System.out.println(o1.toString());
    }
}