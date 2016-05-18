package fo.looknorth.app;

import org.junit.Test;

import java.util.List;

import fo.looknorth.model.RecommendedOilConsumption;
import fo.looknorth.model.IRecommendedOilConsumptionRepository;
import fo.looknorth.model.IMachineRepository;
import fo.looknorth.model.IOilConsumptionRepository;
import fo.looknorth.model.IProductRepository;
import fo.looknorth.model.IProductionRepository;
import fo.looknorth.model.Machine;
import fo.looknorth.model.OilConsumption;
import fo.looknorth.model.Product;
import fo.looknorth.model.Production;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void useInterfacesToListEverything() throws Exception {
        IRecommendedOilConsumptionRepository averageOilList = new RecommendedOilConsumption();
        List<RecommendedOilConsumption> recommendedOilConsumptionList = averageOilList.getAverageOilConsumptions();
        IMachineRepository machineRepository = new Machine();
        List<Machine> machineList = machineRepository.getMachines();
        IOilConsumptionRepository oilList = new OilConsumption();
        List<OilConsumption> oilConsumptionList = oilList.getOilConsumptions();
        IProductionRepository productionRepository = new Production();
        List<Production> productionList = productionRepository.getProductions();
        IProductRepository productRepository = new Product();
        List<Product> productList = productRepository.getProducts();

        for (Product p: productList) {
            System.out.println(p);
        }
        for (Production p: productionList) {
            System.out.println(p);
        }

        for (OilConsumption o: oilConsumptionList) {
            System.out.println(o);
        }

        for (Machine m: machineList) {
            System.out.println(m);
        }

        for (RecommendedOilConsumption a: recommendedOilConsumptionList) {
            System.out.println(a);
        }
    }
}