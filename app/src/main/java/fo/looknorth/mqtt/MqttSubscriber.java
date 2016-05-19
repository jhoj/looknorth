package fo.looknorth.mqtt;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

/**
 * Created by jakup on 5/18/16.
 */
public class MqttSubscriber implements MqttCallback{
    Context context;
    String clientId = MqttClient.generateClientId();
    MqttAndroidClient client;
    MqttConnectOptions options;
    String username = "android";
    String password = "1qaz2wsx";
    IMqttToken token;
    String brokerAddress = "tcp://10.0.0.15:1883";
    String topic = "looknorth/production/oil-consumption/#";
    int qos = 1;
    IMqttToken subToken;


    public MqttSubscriber(Context context) {

        this.context = context;
        client = new MqttAndroidClient(context, brokerAddress, clientId);

        try {
            options = new MqttConnectOptions();
            options.setUserName(username);
            options.setPassword(password.toCharArray());

            token = client.connect(options);

            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d("TAG", "onSuccess");
                    //subscribe();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d("MQTT", "onFailure");
                   // makeToast("could not connect");
                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    private void publish() {
        String payload = "test";
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(topic, message);
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
    }

    private void subscribe() {
        try {
            subToken = client.subscribe(topic, qos);
            subToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // The message was published
                    makeToast("Successfully subscribed to topic: " + topic);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    // The subscription could not be performed, maybe the user was not
                    // authorized to subscribe on the specified topic e.g. using wildcards
                    makeToast("Could not subscribe to: " + topic);
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }



    private void makeToast(String msg) { Toast.makeText(context, msg, Toast.LENGTH_SHORT).show(); }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        //makeToast("Topic: " + topic + " Message: " + message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
