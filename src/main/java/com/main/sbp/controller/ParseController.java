package com.main.sbp.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.main.sbp.data.DataItem;
import com.main.sbp.data.DataObject;

@RestController
public class ParseController {

	// From given json print the ids using lambda expression

	@GetMapping("/gson")
	public void jsonParserWithGson() {

		String jsonString = "{ \"data\": [ { \"id\": 11 }, { \"id\": 21 }, { \"id\": 22 }, { \"id\": 34 } ], \"pagination\": { \"totalItems\": 4 } }";

		Gson gson = new Gson();
		DataObject dataObject = gson.fromJson(jsonString, DataObject.class);

		List<Integer> ids = Arrays.asList(dataObject.getData())
				.stream()
				.map(DataItem::getId)
				.collect(Collectors.toList());

		System.out.println(ids);

	}

	@GetMapping("/jackson")
	public void jsonParserWithJackson() {

		// Assuming the JSON object is represented as a string
		String jsonString = "{ \"data\": [ { \"id\": 11 }, { \"id\": 21 }, { \"id\": 22 }, { \"id\": 34 } ], \"pagination\": { \"totalItems\": 4 } }";

		// Parse the string into a JSON object
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = null;
		try {
			jsonNode = objectMapper.readTree(jsonString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		// Extract the ids using a lambda expression
		jsonNode.get("data")
			.elements()
			.forEachRemaining(element -> System.out.println(element.get("id").asInt()));

	}

}
