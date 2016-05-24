package fo.looknorth.model;
import java.util.ArrayList;

/**
 * Created by jakup on 4/13/16.
 */
public class Machine implements Comparable<Machine> {
    public int machineNumber;
    public Product currentProduct;
    public ArrayList<ProductionCounter> productionCounterList;

    public Machine() {}

    public Machine(int machineNumber, Product product)
    {
        this.machineNumber = machineNumber;
        this.currentProduct = product;
    }

    public Product getCurrentProduct() {
        return currentProduct;
    }

    public void setCurrentProduct(Product currentProduct) {
        this.currentProduct = currentProduct;
    }

    public int getTotalProducedItems()
    {
        int total = 0;
        for (ProductionCounter p: productionCounterList)
        {
            total += p.quantityProduced;
        }
        return total;
    }

    @Override
    public String toString() {
        return "Machine{" +
                "machineNumber=" + machineNumber +
                ", currentProduct=" + currentProduct +
                ", productionCounterList=" + productionCounterList +
                '}';
    }

    @Override
    public int compareTo(Machine another) {
        Integer thisOne = this.machineNumber;
        Integer anotherOne = another.machineNumber;

        return thisOne.compareTo(anotherOne);
    }
}
