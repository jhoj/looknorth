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
public class RecommendedOilConsumption implements IRecommendedOilConsumptionRepository {
    // TODO: 5/12/16 get data, add to view 
        private int id;
        private String machineCombination;
        private String productCombination;
        private float average;

        public RecommendedOilConsumption() {
        }

        public RecommendedOilConsumption(int id, String machineCombination, String productCombination, float average) {
            this.id = id;
            this.machineCombination = machineCombination;
            this.productCombination = productCombination;
            this.average = average;
        }

        public float getAverage() {
            return average;
        }

        public void setAverage(float average) {
            this.average = average;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProductCombination() {
            return productCombination;
        }

        public void setProductCombination(String productCombination) {
            this.productCombination = productCombination;
        }

        public String getMachineCombination() {
            return machineCombination;
        }

        public void setMachineCombination(String machineCombination) {
            this.machineCombination = machineCombination;
        }

    public List<RecommendedOilConsumption> getDbAverageOilConsumption() {
        List<RecommendedOilConsumption> recommendedOilConsumptionList = null;

        try {
            InputStream is = new URL("http://localhost:4567/average-oil-usage").openStream();

            byte b[] = new byte[is.available()]; // kun små filer
            is.read(b);
            String str = new String(b, "UTF-8");
            Gson gson = new Gson();
            Type averageOilUsageType = new TypeToken<Collection<RecommendedOilConsumption>>(){}.getType();
            recommendedOilConsumptionList =  gson.fromJson(str, averageOilUsageType);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return recommendedOilConsumptionList;
    }

    @Override
    public String toString() {
        return "RecommendedOilConsumption{" +
                "id=" + id +
                ", machineCombination='" + machineCombination + '\'' +
                ", productCombination='" + productCombination + '\'' +
                ", average=" + average +
                '}';
    }

    @Override
    public List<RecommendedOilConsumption> getAverageOilConsumptions() {
        return new RecommendedOilConsumption().getDbAverageOilConsumption();
    }
}
