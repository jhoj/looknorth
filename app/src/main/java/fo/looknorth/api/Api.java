package fo.looknorth.api;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import fo.looknorth.model.Machine;
import fo.looknorth.model.Product;

/**
 * Created by jakup on 5/24/16.
 */
public class Api {

    //localhost does not work, it will refer to the emulator itself :)
    private final String protocol = "http://";
    private final String host = "10.0.0.15";
    private final String port = ":4567/";
    private final String urlMachine = "machine";
    private final String urlProduct = "product";
    private final String urlOil = "oil-consumption";
    private final String urlCurrentProduct = "active-product";
    private final String urlRecommendedUsage = "recommended-consumption";

    public static final Api instance = new Api();

    private Api() {
    }

    public List<Machine> getMachines() {
        List<Machine> machines = null;

        try {
           String str = hentUrl("http://" +host+ ":4567/machine");
            Gson gson = new Gson();
            Type machineType = new TypeToken<Collection<Machine>>(){}.getType();
            machines =  gson.fromJson(str, machineType);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return machines;
    }
    public List<Product> getDbProducts(){
        List<Product> products = null;

        try {
            String str = hentUrl("http://" +host+ ":4567/product");
            Gson gson = new Gson();
            Type productType = new TypeToken<Collection<Product>>(){}.getType();
            products =  gson.fromJson(str, productType);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return products;
    }

    private List<Product> getCurrentProducts() {
        List<Product> products = null;

        try {
            String str = hentUrl("http://" +host+ ":4567/active-product");
            Gson gson = new Gson();
            Type productType = new TypeToken<Collection<Product>>(){}.getType();
            products =  gson.fromJson(str, productType);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return products;
    }


    public static String hentUrl(String url) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        StringBuilder sb = new StringBuilder();
        String linje = br.readLine();
        while (linje != null) {
            sb.append(linje + "\n");
            linje = br.readLine();
        }
        return sb.toString();
    }

    public void putCurrentProduct(int machineId, Product product) {
        try {
            Gson gson = new Gson();
            String str = gson.toJson(product);

            URL url = new URL("http://" + host + ":4567/active-product/machineId/" + machineId);

            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("PUT");
            OutputStreamWriter out = new OutputStreamWriter(
                    httpCon.getOutputStream());
            out.write(str);
            out.close();
            httpCon.getInputStream();
        } catch (MalformedURLException ex) {
            Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Api.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        Api api = Api.instance;

        List<Product> productList = api.getDbProducts();
        List<Machine> machineList = api.getMachines();

        // PRODUCTS
        Iterator i = productList.iterator();

        while (i.hasNext()) {
            System.out.println(i.next());
        }

        // MACHINES
        i = machineList.iterator();

        while (i.hasNext()) {
            System.out.println(i.next().toString());
        }

        //ACTIVE PRODUCTS
        List<Product> activeProductList = api.getCurrentProducts();
        i = activeProductList.iterator();
        System.out.println("****BEFORE******");

        while (i.hasNext()) {
            System.out.println(i.next());
        }

        // CHANGING ACTIVE PRODUCTS
        Product p = productList.get(5);
        for (int j = 1; j < 6; j++) {
            api.putCurrentProduct(j, p);
        }
        // ACTIVE PRODUCTS
        activeProductList = api.getCurrentProducts();
        i = activeProductList.iterator();
        System.out.println("****AFTER******");

        while (i.hasNext()) {
            System.out.println(i.next());
        }

    }
}
