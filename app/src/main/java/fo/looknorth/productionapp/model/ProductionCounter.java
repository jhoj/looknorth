package fo.looknorth.productionapp.model;

/**
 * Created by jakup on 4/6/16.
 */
public class ProductionCounter {

    public Product product;
    public int productionCycles;
    public int quantityProduced;

    public ProductionCounter(Product product) {
        this.product = product;
        this.productionCycles = 0;
        this.quantityProduced = 0;
    }

    public void updateQuantityProduced() {
        quantityProduced = productionCycles * product.quantity;
    }
}
