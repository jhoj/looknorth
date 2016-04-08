package model;

/**
 * Created by jakup on 4/6/16.
 */
public class Production {

    public Product product;
    public int productionCycles;
    public int quantityProduced;

    public Production(Product product) {
        this.product = product;
        this.productionCycles = 0;
        this.quantityProduced = 0;
    }

    public void updateQuantityProduced() {
        quantityProduced = productionCycles * product.quantity;
    }
}
