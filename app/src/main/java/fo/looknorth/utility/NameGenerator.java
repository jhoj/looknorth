package fo.looknorth.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fo.looknorth.model.Machine;
import fo.looknorth.model.Product;

/**
 * Created by jakup on 5/19/16.
 */
public class NameGenerator {

    private final List<Machine> list;
    private final String productCombinationName;

    public NameGenerator(List<Machine> list) {
        this.list = list;
        this.productCombinationName = "";
    }

    public String getProductCombinationName() {
        return productCombinationName;
    }

    private String generateName(List<Machine> list) {

        //sorting machines into ascending machine number.
        Collections.sort(list);

        //getting all the products from the machines
        List<Product> products = getProductsFromMachines(list);

        //finding the names to generate a unique string for each product combination
        return translateNameToString(products);
    }

    private List<Product> getProductsFromMachines(List<Machine> list) {
        List<Product> products = new ArrayList<>();

        for (Machine m: list) {
            products.add(m.currentProduct);
        }

        return products;
    }

    private String translateNameToString(List<Product> list) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Product p : list) {

            //not adding name if not product not active.
            if (!p.name.equals("Not active")) {
                stringBuilder.append(p.name);
                stringBuilder.append(",");
            }
        }

        int lastIndex = stringBuilder.toString().length();

        stringBuilder.deleteCharAt(lastIndex - 1);

        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        List<Machine> m = new ArrayList<>();
        NameGenerator n = new NameGenerator(m);

        ArrayList<Product> products = new ArrayList<>();

        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();
        product1.name = "ASD";
        product2.name = "DSA";
        product3.name = "ADAsA";


        products.add(product1);
        products.add(product2);
        products.add(product3);

        Machine m1 = new Machine();
        Machine m2 = new Machine();
        Machine m3 = new Machine();

        m1.machineNumber = 1;
        m2.machineNumber = 2;
        m3.machineNumber = 3;

        m1.currentProduct = product1;
        m2.currentProduct = product2;
        m3.currentProduct = product3;

        List<Machine> list = new ArrayList<>();

        //adding the products in wrong order.
        list.add(m3);
        list.add(m1);
        list.add(m2);

        System.out.println(n.generateName(list));
    }
}
