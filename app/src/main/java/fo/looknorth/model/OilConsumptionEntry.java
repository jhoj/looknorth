package fo.looknorth.model;

/**
 * Created by jakup on 4/27/16.
 */
public class OilConsumptionEntry {
    public String time;
    public float recommendedUsage;
    public float actualUsage;

    public OilConsumptionEntry(String time, float recommendedUsage, float actualUsage) {
        this.time = time;
        this.recommendedUsage = recommendedUsage;
        this.actualUsage = actualUsage;
    }
    //test
    @Override
    public String toString() {
        return time + "\t" + recommendedUsage + "\t" + actualUsage;
    }
}
