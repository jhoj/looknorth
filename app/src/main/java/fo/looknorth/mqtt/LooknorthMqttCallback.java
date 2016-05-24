package fo.looknorth.mqtt;

    import android.util.Log;

    import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
    import org.eclipse.paho.client.mqttv3.MqttCallback;
    import org.eclipse.paho.client.mqttv3.MqttMessage;

    import fo.looknorth.logic.LooknorthLogic;
    import fo.looknorth.utility.MqttMessageToURLTranslator;

/**
     * Created by jakup on 5/19/16.
     */
    public class LooknorthMqttCallback implements MqttCallback {

        @Override
        public void connectionLost(Throwable cause) {
            Log.d("ActionListener", "Connection was lost!");
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            Log.d("ActionListener", "Message Arrived!: " + topic + ": " + new String(message.getPayload()));

            if (topic.contains("oil-consumption")) {
                // create new oil consumption point

                //add it to the dataset

                //call update
            } else if (topic.contains("machines")) {
                // create new oil consumption point

                //add it to dataset
            }

            //LooknorthLogic.instance.


        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
            Log.d("ActionListener", "Delivery Complete!");
        }

    }
