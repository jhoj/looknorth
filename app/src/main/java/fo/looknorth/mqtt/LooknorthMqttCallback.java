package fo.looknorth.mqtt;

    import android.os.AsyncTask;
    import android.util.Log;

    import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
    import org.eclipse.paho.client.mqttv3.MqttCallback;
    import org.eclipse.paho.client.mqttv3.MqttMessage;

    import fo.looknorth.logik.Logik;
    import fo.looknorth.model.Machine;
    import fo.looknorth.model.OilConsumption;
    import fo.looknorth.model.Production;
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
            MqttMessageToURLTranslator mmtt = new MqttMessageToURLTranslator();
            final String url = mmtt.readMessage(topic, message);

            if (mmtt.subject.equals("oil-consumption")) {
                //update


                new AsyncTask() {
                    @Override
                    protected OilConsumption doInBackground(Object... arg) {
                        try {
                            OilConsumption o = Logik.instance.oilConsumption.getLastEntry(url);
                            return o;
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("here");
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        System.out.println((OilConsumption)o);
                        }
                }.execute();

            }
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
            Log.d("ActionListener", "Delivery Complete!");
        }

    }
