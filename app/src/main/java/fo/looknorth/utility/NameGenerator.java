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
        product1.name = "ASD";
        product2.name = "DSA";

        products.add(product1);
        products.add(product2);

        n.translateNameToString(products);
    }
}
