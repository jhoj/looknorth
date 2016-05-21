package fo.looknorth.utility;

import fo.looknorth.model.OilConsumption;

/**
 * Created by jakup on 5/20/16.
 */
public class OilConsumptionListData {
    String recorded;
    String liters;

    public OilConsumptionListData() {}

    public OilConsumptionListData(String recorded, String liters) {
        this.recorded = recorded;
        this.liters = liters;
    }

    public String getRecorded() {
        return recorded;
    }

    public void setRecorded(String recorded) {
        this.recorded = recorded;
    }

    public String getLiters() {
        return liters;
    }

    public void setLiters(String liters) {
        this.liters = liters;
    }
}
