package fo.looknorth.utility;

import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by jakup on 5/19/16.
 */
public class MqttMessageToURLTranslator {
    MqttMessageReader mqttMessageReader;

    private String webapi = "http://10.0.0.10:4567/";
    private String last = "/last";
    public String subject;
    public String id;
    public String slash = "/";

    public MqttMessageToURLTranslator() {
        mqttMessageReader = new MqttMessageReader();
    }

    public String readMessage(String topic, MqttMessage message) {
        mqttMessageReader.fill(topic, message);
        subject = mqttMessageReader.getSUBJECT();
        id = mqttMessageReader.getSENSOR();

        System.out.println(subject);

        if (subject.contains("machines")){
            String url = webapi + subject + slash + id + last;
            System.out.println(url);
            return url;
        } else if (subject.contains("oil-consumption")) {
            String url = webapi + subject + last;
            System.out.println(url);
            return url;
        } else { return null; }
    }
}
