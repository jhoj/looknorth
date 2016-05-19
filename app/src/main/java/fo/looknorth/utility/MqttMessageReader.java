package fo.looknorth.utility;

import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by jakup on 5/19/16.
 */
public class MqttMessageReader {
    /*
This class makes a more organized
message from a retrieved MQTT message.
This is done so that the correct information
from the mqtt message will be reflected in the app.
*/
        private String company;
        private String location;
        private String subject;
        private String sensor;
        private String message;

        public MqttMessageReader() {}

        public MqttMessageReader(String topic, MqttMessage message) {
            String[] parts = topic.split("/");

            company = parts[0];
            location = parts[1];
            subject = parts[2];
            sensor = parts[3];
            this.message = new String(message.getPayload());
        }

        public void fill(String topic, MqttMessage message) {
            String[] parts = topic.split("/");

            setCOMPANY(parts[0]);
            setLOCATION(parts[1]);
            setSUBJECT(parts[2]);
            setSENSOR(parts[3]);
            setMESSAGE(new String(message.getPayload()));
        }

        public String getCOMPANY() {
            return company;
        }

        public String getLOCATION() {
            return location;
        }

        public String getSUBJECT() {
            return subject;
        }

        public String getSENSOR() {
            return sensor;
        }

        public String getMESSAGE() {
            return message;
        }

        public void setCOMPANY(String COMPANY) {
            this.company = COMPANY;
        }

        public void setLOCATION(String LOCATION) {
            this.location = LOCATION;
        }

        public void setSUBJECT(String SUBJECT) {
            this.subject = SUBJECT;
        }

        public void setSENSOR(String SENSOR) {
            this.sensor = SENSOR;
        }

        public void setMESSAGE(String MESSAGE) {
            this.message = MESSAGE;
        }

        @Override
        public String toString() {
            return "Company: " + company + "\n" + "Location: " + location + "\n" + "SUBJECT: " + subject + "\n" + "SENSOR: " + sensor + "\n" + "MESSAGE: " + message + "\n";
        }

        public static void main(String[] args) {
            String payload = "Hello";
            MqttMessage m = new MqttMessage();
            m.setPayload(payload.getBytes());
            String topic = "looknorth/production/machines/1";
            MqttMessageReader filter = new MqttMessageReader(topic, m);
            System.out.println(filter.toString());
        }


}
