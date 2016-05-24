package fo.looknorth.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Collection;
import java.util.List;

/**
 * Created by jakup on 4/13/16.
 */
public class ProductRepository implements IProductRepository {

    public int id;
    public String name;
    public int quantity;

    public ProductRepository(){}

    public ProductRepository(int id, String name, int quantity)
    {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name;
    }

    public List<ProductRepository> getDbProducts(){
        List<ProductRepository> products = null;

        try {
            InputStream is = new URL("http://localhost:4567/product").openStream();

            byte b[] = new byte[is.available()]; // kun små filer
            is.read(b);
            String str = new String(b, "UTF-8");
            Gson gson = new Gson();
            Type productType = new TypeToken<Collection<Product>>(){}.getType();
            products =  gson.fromJson(str, productType);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return products;
    }

    public List<ProductRepository> getProducts() {
        return new ProductRepository().getDbProducts();
    }

}
