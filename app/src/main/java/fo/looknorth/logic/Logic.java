package fo.looknorth.logic;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import fo.looknorth.api.Api;
import fo.looknorth.model.Machine;
import fo.looknorth.model.OilConsumption;
import fo.looknorth.model.OilConsumptionEntry;
import fo.looknorth.model.Product;
import fo.looknorth.model.ProductionCounter;
import fo.looknorth.mqtt.LooknorthMqttCallback;
import fo.looknorth.mqtt.MqttActionListener;

/**
 * Created by jakup on 4/27/16.
 */
public class Logic extends Application {

    public static Logic instance;
    public List<Runnable> observers;
    public Handler hander;
    public Api api;

    // MQTT
    public MqttAndroidClient mqttClient;
    public String broker;
    public String clientId;
    public String oilTopic;
    public String machineTopic;
    public String[] topics;
    public int qos;

    // Model
    public OilConsumption oilConsumption;
    public List<Machine> machines;
    public HashMap<Integer, List<OilConsumptionEntry>> oilUsageLinePoints;
    public String[] records;
    public List<Product> productList;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        api = Api.instance;
        observers = new ArrayList<>();
        hander = new Handler();

        //mqtt klar til init
        broker = "tcp://looknorthserver.cloudapp.net:1883";
        clientId = "androidSampleClient";
        oilTopic = "looknorth/production/oil-consumption/#";
        machineTopic = "looknorth/production/machines/#";
        topics = new String[2];
        topics[0] = oilTopic;
        topics[1] = machineTopic;
        qos = 2;
        oilConsumption = new OilConsumption();
    }

    public void initMqtt(Context context) {
        mqttClient = new MqttAndroidClient(context, broker, clientId);
        mqttClient.setCallback(new LooknorthMqttCallback());
        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName("android");
            options.setPassword("1qaz2wsx".toCharArray());
            mqttClient.connect(null, new MqttActionListener(mqttClient, topics, qos));
            System.out.println("Connected to mqtt broker");
            //mqttClient.connect(options, null, new MqttActionListener(mqttClient, topics, qos));
        } catch (MqttException ex) {

        }
    }

    public void initData() {
        machines = api.getMachines();
        for (Machine m: machines) {
            System.out.println("Machine " + m.machineNumber + " CurrentProduct: " + m.currentProduct.toString());
        }
        productList = api.getDbProducts();


        List<OilConsumptionEntry> totalEntries = new ArrayList<>();
        totalEntries.add(new OilConsumptionEntry("Total", 0.9f, 0.8f));
        totalEntries.add(new OilConsumptionEntry("Total", 0.9f, 0.7f));

        List<OilConsumptionEntry> m1Entries = new ArrayList<>();
        m1Entries.add(new OilConsumptionEntry("M1", 0.9f, 0.3f));
        m1Entries.add(new OilConsumptionEntry("M1", 0.9f, 0.3f));

        List<OilConsumptionEntry> m2Entries = new ArrayList<>();
        m2Entries.add(new OilConsumptionEntry("M2", 0.2f, 0.8f));
        m2Entries.add(new OilConsumptionEntry("M2", 0.2f, 0.7f));

        List<OilConsumptionEntry> m3Entries = new ArrayList<>();
        m3Entries.add(new OilConsumptionEntry("M3", 0.2f, 0.8f));
        m3Entries.add(new OilConsumptionEntry("M3", 0.2f, 0.7f));

        List<OilConsumptionEntry> m4Entries = new ArrayList<>();
        m4Entries.add(new OilConsumptionEntry("M4", 0.2f, 0.8f));
        m4Entries.add(new OilConsumptionEntry("M4", 0.2f, 0.7f));

        List<OilConsumptionEntry> m5Entries = new ArrayList<>();
        m5Entries.add(new OilConsumptionEntry("M5", 0.2f, 0.8f));
        m5Entries.add(new OilConsumptionEntry("M5", 0.2f, 0.7f));

        oilUsageLinePoints = new HashMap<>();
        oilUsageLinePoints.put(0, totalEntries);  // total
        oilUsageLinePoints.put(1, m1Entries); // machine 1
        oilUsageLinePoints.put(2, m2Entries); // machine 2
        oilUsageLinePoints.put(3, m3Entries);  // machine 3
        oilUsageLinePoints.put(4, m4Entries); // machine 4
        oilUsageLinePoints.put(5, m5Entries); // machine 5

        records = new String[2];
        records[0] = totalEntries.get(0).toString();
        records[1] = totalEntries.get(1).toString();

        int quantity = 2;
        int quantity1 = 1;

        Product p1 = new Product(1, "Test Product 1", quantity);
        Product p2 = new Product(2, "Test Product 2", quantity1);

        ProductionCounter p1m1 = new ProductionCounter(p1);
        ProductionCounter p2m1 = new ProductionCounter(p2);

        p1m1.productionCycles = 1;
        p2m1.productionCycles = 1;

        //m1 p1 total = 2 * 1
        p1m1.updateQuantityProduced();
        //m1 p2 total = 1 * 1
        p2m1.updateQuantityProduced();

        //production for total
        machines.get(0).productionCounterList = new ArrayList<>();
        machines.get(0).productionCounterList.add(p1m1);
        machines.get(0).productionCounterList.add(p2m1);

        machines.get(1).productionCounterList = new ArrayList<>();
        machines.get(1).productionCounterList.add(p1m1);
        machines.get(1).productionCounterList.add(p2m1);

        machines.get(2).productionCounterList = new ArrayList<>();
        machines.get(2).productionCounterList.add(p1m1);
        machines.get(2).productionCounterList.add(p2m1);

        machines.get(3).productionCounterList = new ArrayList<>();
        machines.get(3).productionCounterList.add(p1m1);
        machines.get(3).productionCounterList.add(p2m1);

        machines.get(4).productionCounterList = new ArrayList<>();
        machines.get(4).productionCounterList.add(p1m1);
        machines.get(4).productionCounterList.add(p2m1);

    }

    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        return sdf.format(Calendar.getInstance().getTime());
    }

    public void getMachines() {
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    machines = api.getMachines();
                    return "Data er hentet!";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Data blev ikke hentet.";
                }
            }

            @Override
            protected void onPostExecute(Object besked) {
                System.out.println(besked);
            }
        }.execute();
    }
    public void getProducts() {
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    productList = api.getDbProducts();
                    return "Data er hentet!";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Data blev ikke hentet.";
                }
            }

            @Override
            protected void onPostExecute(Object besked) {
                System.out.println(besked);
            }
        }.execute();
    }
    public void putActiveProduct(final int id) {
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    Machine m = machines.get(id);
                    api.putCurrentProduct(m.machineNumber, m.currentProduct);
                    return "Data er sendt!";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Data blev ikke sendt.";
                }
            }

            @Override
            protected void onPostExecute(Object besked) {
                System.out.println(besked);
            }
        }.execute();
    }

    public String getTime(int tabIndex) {
        List<OilConsumptionEntry> entries = Logic.instance.oilUsageLinePoints.get(tabIndex);
        int last = entries.size() - 1;
        return entries.get(last).time;
    }

    public float getRecommendedUsage(int tabIndex) {
        List<OilConsumptionEntry> entries = Logic.instance.oilUsageLinePoints.get(tabIndex);
        int last = entries.size() - 1;
        return entries.get(last).recommendedUsage;
    }

    public float getActualUsage(int tabIndex) {
        List<OilConsumptionEntry> entries = Logic.instance.oilUsageLinePoints.get(tabIndex);
        int last = entries.size() - 1;
        return entries.get(last).actualUsage;
    }

    public void updateViews() {
        for (Runnable r: observers) {
            r.run();
        }
    }

}
