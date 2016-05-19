package fo.looknorth.mqtt;

import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by jakup on 5/19/16.
 */
public class MqttActionListener implements IMqttActionListener {
    MqttAndroidClient client;
    String[] topics;
    int qos;

    public MqttActionListener(MqttAndroidClient client, String[] topics, int qos) {
        this.client = client;
        this.topics = topics;
        this.qos = qos;
    }

    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        System.out.println("Connection Success!");
        try {
            for (String s: topics) {
                client.subscribe(s, qos);
                System.out.println("Subscribed to: " + s);
            }
        } catch (MqttException ex) {
            Log.d("ActionListener", "Could not subscribe");
        }
    }

    @Override
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        System.out.println("Connection Failure!");
    }
}
