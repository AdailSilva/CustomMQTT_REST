package com.adailsilva.custommqtt.services;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MQTTService implements MqttCallback {

	private MqttClient client = null;
	private String mqttUserName = "noc";
	private String mqttPassword = "noc";
	private String mqttIpAddress = "localhost";
//	private boolean mqttHaveCredential = false;
	private boolean mqttHaveCredential = true;
	private String mqttPort = "1883";
	private String mqttTopic = "/prtg/notifications";
	Logger LOG = LoggerFactory.getLogger(getClass());

	@Override
	public void connectionLost(Throwable arg0) {
		LOG.info("connectionLost :" + arg0.getMessage() + " :" + arg0.toString());
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		try {
			LOG.info("deliveryCompleted ");
			LOG.info("deliveryComplete : " + arg0.getMessage().getPayload().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
		try {
			LOG.info("arg0 :" + arg0 + " arg1 :" + arg1.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void publish(String topicSuffix, String content) {
		MqttMessage message = new MqttMessage();
		message.setPayload(content.getBytes());
		message.setQos(2);
		try {
			String topic = mqttTopic + topicSuffix;
			if (client.isConnected()) {
				LOG.info("Connection Status :" + client.isConnected());
			}
			client.publish(topic, message);
		} catch (MqttPersistenceException e) {
			e.printStackTrace();

		} catch (MqttException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startMqtt() {

		MemoryPersistence memoryPersistence = new MemoryPersistence();
		try {
			client = new MqttClient("tcp://" + mqttIpAddress + ":" + mqttPort, MqttClient.generateClientId(),
					memoryPersistence);
		} catch (MqttException e1) {
			e1.printStackTrace();
		}

		MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
		mqttConnectOptions.setCleanSession(true);
		mqttConnectOptions.setMaxInflight(3000);
		mqttConnectOptions.setAutomaticReconnect(true);
		if (mqttHaveCredential) {
			mqttConnectOptions.setUserName(mqttUserName);
			mqttConnectOptions.setPassword(mqttPassword.toCharArray());
		}

		client.setCallback(this);

		// client.connect();
		// MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
		try {
			IMqttToken mqttConnectionToken = client.connectWithResult(mqttConnectOptions);

			LOG.info(" Connection Status :" + mqttConnectionToken.isComplete());
			client.subscribe("#");

		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
}
