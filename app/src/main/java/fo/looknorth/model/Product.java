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
public class Product {

    public int id;
    public String name;
    public int quantity;

    public Product(){}

    public Product(int id, String name, int quantity)
    {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name;
    }

}
