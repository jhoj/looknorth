package fo.looknorth.model;
import java.util.ArrayList;

/**
 * Created by jakup on 4/13/16.
 */
public class Machine implements Comparable<Machine> {
    public int machineNumber;
    public Product currentProduct;
    public ProductionEntry productionEntry;

    public Machine() {}

    public Machine(int machineNumber, Product product)
    {
        this.machineNumber = machineNumber;
        this.currentProduct = product;
        this.productionEntry = new ProductionEntry();
    }

    public Product getCurrentProduct() {
        return currentProduct;
    }

    public void setCurrentProduct(Product currentProduct) {
        this.currentProduct = currentProduct;
    }

    @Override
    public String toString() {
        return "Machine{" +
                "machineNumber=" + machineNumber +
                ", currentProduct=" + currentProduct +
                '}';
    }

    @Override
    public int compareTo(Machine another) {
        Integer thisOne = this.machineNumber;
        Integer anotherOne = another.machineNumber;

        return thisOne.compareTo(anotherOne);
    }
}
