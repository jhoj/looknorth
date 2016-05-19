package fo.looknorth.model;

import java.util.List;

/**
 * Created by jakup on 5/18/16.
 */
public interface IOilConsumptionRepository {
    List<OilConsumption> getOilConsumptions();
    OilConsumption getLastEntry();
    OilConsumption getLastEntry(String url);
}
