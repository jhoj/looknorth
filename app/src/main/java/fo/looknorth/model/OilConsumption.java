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
public class OilConsumption {

    private int id;
    private int machineId;
    private float liters;
    private String recorded;

    public OilConsumption(int id, int machineId, float liters, String recorded) {
        this.id = id;
        this.machineId = machineId;
        this.liters = liters;
        this.recorded = recorded;
    }

    public OilConsumption() {
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

    @Override
    public String toString() {
        return "OilConsumption{" +
                "id=" + id +
                ", machineId=" + machineId +
                ", liters=" + liters +
                ", recorded='" + recorded + '\'' +
                '}';
    }
}
