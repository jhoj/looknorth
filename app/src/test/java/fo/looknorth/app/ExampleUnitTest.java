package fo.looknorth.app;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

//    @Test
//    public void useInterfacesToListEverything() throws Exception {
//        IRecommendedOilConsumptionRepository averageOilList = new RecommendedOilConsumption();
//        List<RecommendedOilConsumption> recommendedOilConsumptionList = averageOilList.getAverageOilConsumptions();
//        IMachineRepository machineRepository = new Machine();
//        List<Machine> machineList = machineRepository.getMachines();
//        IOilConsumptionRepository oilList = new OilConsumption();
//        List<OilConsumption> oilConsumptionList = oilList.getOilConsumptions();
//        IProductionRepository productionRepository = new Production();
//        List<Production> productionList = productionRepository.getProductions();
//        IProductRepository productRepository = new Product();
//        List<Product> productList = productRepository.getProducts();
//
//        for (Product p: productList) {
//            System.out.println(p);
//        }
//        for (Production p: productionList) {
//            System.out.println(p);
//        }
//
//        for (OilConsumption o: oilConsumptionList) {
//            System.out.println(o);
//        }
//
//        for (Machine m: machineList) {
//            System.out.println(m);
//        }
//
//        for (RecommendedOilConsumption a: recommendedOilConsumptionList) {
//            System.out.println(a);
//        }
//    }
}