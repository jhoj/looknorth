package fo.looknorth.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jakup on 5/25/16.
 */
public class ProductionEntry {

    public List<Product> items;

    public ProductionEntry() {
        items = new ArrayList<>();
    }

    public void add(Product item) {

        if (items.contains(item)) {

            // increment the count of the item.
            int location = items.indexOf(item);

            Product foundItem = items.get(location);
            int itemCount = item.count;
            int totalCount = foundItem.count + itemCount;
            System.out.println("Before count: " + foundItem.count);
            System.out.println("changing count");
            foundItem.count = totalCount;
            System.out.println("After count: " + foundItem.count);
            System.out.println(foundItem.getTotal());
        } else {
            items.add(item);
        }
    }

    public int getItemsProducedByMachine() {
        int total = 0;

        for (Product p: items) {
            total += p.getTotal();
        }

        return total;
    }

    public void reset() {
        items.clear();
    }

    public static void main(String[] args) {
        ProductionEntry pe = new ProductionEntry();
        Product product = new Product(1, "P1", 5);

        pe.add(product);

        System.out.println(pe.items.get(0).count);
        System.out.println(pe.items.get(0).getTotal());
        for (int i = 0; i < 5; i++) {
            // add same kind of product
            pe.add(new Product(1, "P1", 5));
        }

        System.out.println(pe.items.get(0).count);
        System.out.println(pe.items.get(0).getTotal());


    }

}
