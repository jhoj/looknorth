package fo.looknorth.logik;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import fo.looknorth.model.Machine;
import fo.looknorth.model.OilUsageEntry;
import fo.looknorth.model.Product;
import fo.looknorth.model.ProductionCounter;

/**
 * Created by jakup on 4/27/16.
 */
public class Logik {

    public static Logik instance;

    public Machine[] machines;
    public HashMap<Integer, OilUsageEntry[]> oilUsageLinePoints;
    public String[] records;
    public ArrayList<Product> productList;

    public void lavTestData() {
        /******************************
        *       OIL USAGE
        ******************************/
        machines = new Machine[5];
        machines[0] = new Machine();
        machines[1] = new Machine();
        machines[2] = new Machine();
        machines[3] = new Machine();
        machines[4] = new Machine();
        machines[0].machineNumber = 1;
        machines[1].machineNumber = 2;
        machines[2].machineNumber = 3;
        machines[3].machineNumber = 4;
        machines[4].machineNumber = 5;

        OilUsageEntry[] totalEntries = new OilUsageEntry[2];
        totalEntries[0] = new OilUsageEntry("Total", 0.9f, 0.8f);
        totalEntries[1] = new OilUsageEntry("Total", 0.9f, 0.7f);

        OilUsageEntry[] m1Entries = new OilUsageEntry[2];
        m1Entries[0] = new OilUsageEntry("M1", 0.9f, 0.3f);
        m1Entries[1] = new OilUsageEntry("M1", 0.9f, 0.3f);

        OilUsageEntry[] m2Entries = new OilUsageEntry[2];
        m2Entries[0] = new OilUsageEntry("M2", 0.2f, 0.8f);
        m2Entries[1] = new OilUsageEntry("M2", 0.2f, 0.7f);

        OilUsageEntry[] m3Entries = new OilUsageEntry[2];
        m3Entries[0] = new OilUsageEntry("M3", 0.2f, 0.8f);
        m3Entries[1] = new OilUsageEntry("M3", 0.2f, 0.7f);

        OilUsageEntry[] m4Entries = new OilUsageEntry[2];
        m4Entries[0] = new OilUsageEntry("M4", 0.2f, 0.8f);
        m4Entries[1] = new OilUsageEntry("M4", 0.2f, 0.7f);

        OilUsageEntry[] m5Entries = new OilUsageEntry[2];
        m5Entries[0] = new OilUsageEntry("M5", 0.2f, 0.8f);
        m5Entries[1] = new OilUsageEntry("M5", 0.2f, 0.7f);

        oilUsageLinePoints = new HashMap<>();
        oilUsageLinePoints.put(0, totalEntries);  // total
        oilUsageLinePoints.put(1, m1Entries); // machine 1
        oilUsageLinePoints.put(2, m2Entries); // machine 2
        oilUsageLinePoints.put(3, m3Entries);  // machine 3
        oilUsageLinePoints.put(4, m4Entries); // machine 4
        oilUsageLinePoints.put(5, m5Entries); // machine 5

        records = new String[2];
        records[0] = totalEntries[0].toString();
        records[1] = totalEntries[1].toString();

        /*********************************
        *       Products in Production
        *********************************/
        machines[0].currentProduct = new Product(1,"Product 1", 5);
        machines[1].currentProduct = new Product(2,"Product 2", 4);
        machines[2].currentProduct = new Product(3,"Product 3", 3);
        machines[3].currentProduct = new Product(4,"Product 4", 2);
        machines[4].currentProduct = new Product(5,"Product 5", 1);

        productList = new ArrayList<>();
        productList.add(new Product(0, "Not active", 0));
        productList.add(new Product(2001, "Kassi - 20 kg",1));
        productList.add(new Product(2003, "Kassi - 3 kg, 30 x 40 cm", 1));
        productList.add(new Product(2004, "Lok - 3 og 5 kg, 30 x 40 cm", 1));
        productList.add(new Product(2005, "Kassi - 5 kg, 30 x 40 cm", 1));
        productList.add(new Product(2008, "Kassi - 40 x 60 cm",1));
        productList.add(new Product(2009, "Lok - til 40 x 60 cm kassa",1));
        productList.add(new Product(2026, "Flogkassi - 20 kg",1));
        productList.add(new Product(2027, "Lok - til flogkassa 20 kg",1));
        productList.add(new Product(2030, "Lok - til hummarakassa",1));
        productList.add(new Product(2033, "Hummarakassi - (AIR300)",1));
        productList.add(new Product(2340, "Kassi - 340 litur",1));
        productList.add(new Product(2341, "Lok - 340 litur",1));
        productList.add(new Product(3001, "Flot - 90 cm O270",1));
        productList.add(new Product(3004, "Flot - 120 cm O370 - halv",1));
        productList.add(new Product(10025, "Plata - L150H 25 mm",1));
        productList.add(new Product(10050, "Plata - L150H 50 mm",1));
        productList.add(new Product(10075, "Plata - L150H 75 mm",1));
        productList.add(new Product(10100, "Plata - L150H 100 mm",1));
        productList.add(new Product(10125, "Plata - L150H 125 mm",1));
        productList.add(new Product(10150, "Plata - L150H 150 mm",1));
        productList.add(new Product(20050, "Plata - L120H 50 mm",1));
        productList.add(new Product(20075, "Plata - L120H 75 mm",1));
        productList.add(new Product(20100, "Plata - L120H 100 mm",1));
        productList.add(new Product(20150, "Plata - L120H 150 mm",1));
        productList.add(new Product(30025, "Plata - L80H 25 mm",1));
        productList.add(new Product(30050, "Plata - L80H 50 mm",1));
        productList.add(new Product(30100, "Plata - L80H 100 mm",1));
        productList.add(new Product(31075, "Plata - L80H 75 mm",1));
        productList.add(new Product(32125, "Plata - L80H 125 mm",1));
        productList.add(new Product(32150, "Plata - L80H 150 mm",1));
        productList.add(new Product(39050, "Plata - L250H 50 mm",1));
        productList.add(new Product(39100, "Plata - L250H 100 mm",1));

        /************************
         *      PRODUCTION
         ***********************/
        int quantity = 2;
        int quantity1 = 1;

        Product p1 = new Product(1, "Test Product 1", quantity);
        Product p2 = new Product(2, "Test Product 2", quantity1);
        //todo remove this test method when working
        ProductionCounter p1m1 = new ProductionCounter(p1);
        ProductionCounter p2m1 = new ProductionCounter(p2);

        p1m1.productionCycles = 1;
        p2m1.productionCycles = 1;

        //m1 p1 total = 2 * 1
        p1m1.updateQuantityProduced();
        //m1 p2 total = 1 * 1
        p2m1.updateQuantityProduced();

        //production for total
        machines[0].productionCounterList = new ArrayList<ProductionCounter>();
        machines[0].productionCounterList.add(p1m1);
        machines[0].productionCounterList.add(p2m1);


    }

    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        return sdf.format(Calendar.getInstance().getTime());
    }
}
