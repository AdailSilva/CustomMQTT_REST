package com.adailsilva.custommqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.adailsilva.custommqtt.services.MQTTService;

@SpringBootApplication
public class CustomMqttApplication implements CommandLineRunner {

	@Autowired
	private MQTTService mqttService;

	public static void main(String[] args) {
		SpringApplication.run(CustomMqttApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mqttService.startMqtt();
	}

}
