package fo.looknorth.mqtt;

    import android.util.Log;

    import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
    import org.eclipse.paho.client.mqttv3.MqttCallback;
    import org.eclipse.paho.client.mqttv3.MqttMessage;

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

        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
            Log.d("ActionListener", "Delivery Complete!");
        }
    }
