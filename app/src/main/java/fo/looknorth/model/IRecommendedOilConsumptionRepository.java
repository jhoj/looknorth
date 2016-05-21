package fo.looknorth.model;

import java.util.List;

/**
 * Created by jakup on 5/18/16.
 */
public interface IRecommendedOilConsumptionRepository {
    List<RecommendedOilConsumption> getAverageOilConsumptions();
    RecommendedOilConsumption getRecommendedOilConsumption(String productCombination);
}
