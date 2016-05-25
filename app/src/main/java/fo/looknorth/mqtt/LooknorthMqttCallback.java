package fo.looknorth.mqtt;

    import android.util.Log;

    import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
    import org.eclipse.paho.client.mqttv3.MqttCallback;
    import org.eclipse.paho.client.mqttv3.MqttMessage;

    import java.text.SimpleDateFormat;
    import java.util.Calendar;
    import java.util.HashMap;
    import java.util.List;

    import fo.looknorth.logic.Logic;
    import fo.looknorth.model.OilConsumptionEntry;
    import fo.looknorth.utility.MqttMessageReader;

/**
     * Created by jakup on 5/19/16.
     */
    public class LooknorthMqttCallback implements MqttCallback {

        @Override
        public void connectionLost(Throwable cause) {
            Log.d("ActionListener", "Connection was lost!");
            Logic.instance.initMqtt(Logic.instance.getApplicationContext());
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            Log.d("ActionListener", "Message Arrived!: " + topic + ": " + new String(message.getPayload()));

            MqttMessageReader reader = new MqttMessageReader(topic, message);

            if (topic.contains("oil-consumption")) {
                // create new oil consumption point
                String time = getClock();

                float recommended = 1.3f;
                float actual = Float.parseFloat(message.toString());
                OilConsumptionEntry entry = new OilConsumptionEntry(time, recommended, actual);

                int sensorId = Integer.parseInt(reader.getSENSOR());

                List<OilConsumptionEntry> entryList = Logic.instance.oilUsageLinePoints.get(sensorId);
                List<OilConsumptionEntry> totalEntryList = Logic.instance.oilUsageLinePoints.get(0);
                entryList.add(entry);
                totalEntryList.add(entry);
                Logic.instance.oilUsageLinePoints.put(sensorId, entryList);
                Logic.instance.oilUsageLinePoints.put(0, entryList);

            } else if (topic.contains("machines")) {
                // create new oil consumption point

                //add it to dataset
            }

            for (Runnable r: Logic.instance.observers) { r.run();}

        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
            Log.d("ActionListener", "Delivery Complete!");
        }

    private String getClock() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:MM");
        return sdf.format(Calendar.getInstance().getTime());
    }

    }
