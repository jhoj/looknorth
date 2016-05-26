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
    import fo.looknorth.model.Machine;
    import fo.looknorth.model.OilConsumptionEntry;
    import fo.looknorth.model.Product;
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

                // recommended consumption
                float recommended = 1.3f;

                // actual consumption
                float actual = Float.parseFloat(message.toString());

                // new entry
                OilConsumptionEntry entry = new OilConsumptionEntry(time, recommended, actual);

                // the sensor the message came from, i.e. where to put the data
                int sensorId = Integer.parseInt(reader.getSENSOR());

                // the index of total consumption
                int total = 0;

                // retrieve list with entries
                List<OilConsumptionEntry> entryList = Logic.instance.oilUsageLinePoints.get(sensorId);

                // retrieve list with all oil entries
                List<OilConsumptionEntry> totalEntryList = Logic.instance.oilUsageLinePoints.get(total);

                // add to specific list
                entryList.add(entry);

                // add to the total list
                totalEntryList.add(entry);

                // put the specific list back into hashmap
                Logic.instance.oilUsageLinePoints.put(sensorId, entryList);

                //put the total list back into hashmap
                Logic.instance.oilUsageLinePoints.put(total, entryList);

            }
            // if the topic was sent to the machine topic
            else if (topic.contains("machines")) {
                // find which sensor sent the message.
                int machineId = Integer.parseInt(reader.getSENSOR());

                // the index that the the specific machine has in the list.
                int actualIndex = machineId - 1;

                //find current product for this machine
                Product item = Logic.instance.machines.get(actualIndex).currentProduct;

                //setting the count of the product to 1.
                item.count = 1;

                System.out.println(item.content());

                // create new production entry
                // if it is of type not active, then dont add it to the list.
                if (!item.name.contains("Ikki aktivit")) {
                    Logic.instance.machines.get(actualIndex).productionEntry.add(item);
                }
                System.out.println("Total:");
                System.out.println(Logic.instance.machines.get(actualIndex).productionEntry.getItemsProducedByMachine());
            }



            // all other topics will be ignored

            // update all views.
            Logic.instance.updateViews();
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
