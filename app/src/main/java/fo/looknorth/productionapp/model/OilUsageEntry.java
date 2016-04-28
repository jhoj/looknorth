package fo.looknorth.productionapp.model;

/**
 * Created by jakup on 4/27/16.
 */
public class OilUsageEntry {
    public String time;
    public float recommendedUsage;
    public float actualUsage;

    public OilUsageEntry(String time, float recommendedUsage, float actualUsage) {
        this.time = time;
        this.recommendedUsage = recommendedUsage;
        this.actualUsage = actualUsage;
    }

    @Override
    public String toString() {
        return time + "\t" + recommendedUsage + "\t" + actualUsage;
    }
}
