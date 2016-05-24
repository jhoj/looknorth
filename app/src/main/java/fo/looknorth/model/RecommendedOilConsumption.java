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
public class RecommendedOilConsumption {
    // TODO: 5/12/16 get data, add to view 
        private int id;
        private String machineCombination;
        private String productCombination;
        private float average;

        public RecommendedOilConsumption() {}

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

    @Override
    public String toString() {
        return "RecommendedOilConsumption{" +
                "id=" + id +
                ", machineCombination='" + machineCombination + '\'' +
                ", productCombination='" + productCombination + '\'' +
                ", average=" + average +
                '}';
    }
}
