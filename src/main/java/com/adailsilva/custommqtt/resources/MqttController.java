package com.adailsilva.custommqtt.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.adailsilva.custommqtt.dto.MessageDTO;
import com.adailsilva.custommqtt.services.MQTTService;

@RestController
public class MqttController {

	@Autowired
	private MQTTService mqttService;

	@PutMapping("/start")
	public String startMqtt() {
		return null;
	}

	@PostMapping("/publish")
	public String publish(@RequestBody MessageDTO messageDTO) {
		mqttService.publish(messageDTO.getTopic(), messageDTO.getMessage());
		return "Success";
	}

}
