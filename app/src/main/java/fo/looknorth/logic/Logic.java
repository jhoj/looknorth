package fo.looknorth.logic;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

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
import fo.looknorth.model.OilConsumptionEntry;
import fo.looknorth.model.Product;
import fo.looknorth.model.ProductionCounter;
import fo.looknorth.model.ProductionEntry;
import fo.looknorth.mqtt.LooknorthMqttCallback;
import fo.looknorth.mqtt.MqttActionListener;

/**
 * Created by jakup on 4/27/16.
 */
public class Logic extends Application {

    public static Logic instance;
    public List<Runnable> observers;
    public Handler handler;
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
    public List<Machine> machines;
    public HashMap<Integer, List<OilConsumptionEntry>> oilUsageLinePoints;
    public String[] records;
    public List<Product> productList;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Logic", "onCreate called!");
        instance = this;
        api = Api.instance;
        observers = new ArrayList<>();
        handler = new Handler();

        //mqtt klar til init
        broker = "tcp://looknorthserver.cloudapp.net:1883";
        clientId = "androidSampleClient" + Calendar.getInstance().getTimeInMillis();
        oilTopic = "looknorth/production/oil-consumption/#";
        machineTopic = "looknorth/production/machines/#";
        topics = new String[2];
        topics[0] = oilTopic;
        topics[1] = machineTopic;
        qos = 2;
    }

    public void init() {
        initData();
        initMqtt(getApplicationContext());
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
            m.productionEntry = new ProductionEntry();
        }
        productList = api.getDbProducts();

        List<OilConsumptionEntry> oilTotalEntries = new ArrayList<>();
        List<OilConsumptionEntry> oilMachine1Entries = new ArrayList<>();
        List<OilConsumptionEntry> oilMachine2Entries = new ArrayList<>();
        List<OilConsumptionEntry> oilMachine3Entries = new ArrayList<>();
        List<OilConsumptionEntry> oilMachine4Enties = new ArrayList<>();
        List<OilConsumptionEntry> oilMachine5Entries = new ArrayList<>();

        oilUsageLinePoints = new HashMap<>();
        oilUsageLinePoints.put(0, oilTotalEntries);  // total
        oilUsageLinePoints.put(1, oilMachine1Entries); // machine 1
        oilUsageLinePoints.put(2, oilMachine2Entries); // machine 2
        oilUsageLinePoints.put(3, oilMachine3Entries);  // machine 3
        oilUsageLinePoints.put(4, oilMachine4Enties); // machine 4
        oilUsageLinePoints.put(5, oilMachine5Entries); // machine 5

        records = new String[2];
        records[0] = oilTotalEntries.get(0).toString();
        records[1] = oilTotalEntries.get(1).toString();

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
