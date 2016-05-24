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
public class Production {

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


    @Override
    public String toString() {
        return "Production{" +
                "id=" + id +
                ", machineId=" + machineId +
                ", productId=" + productId +
                ", recorded='" + recorded + '\'' +
                '}';
    }
}
