package com.main.sbp.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.main.sbp.data.DataItem;
import com.main.sbp.data.DataObject;
import com.main.sbp.data.Person;

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
		
		// Demo Example
		String jsonString1 = "{\"name\":\"John\",\"age\":30}";

		Person person = gson.fromJson(jsonString1, Person.class);

		String name = person.getName();
		int age = person.getAge();

		System.out.println("Name: " + name);
		System.out.println("Age: " + age);

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
		
		// Demo Example
		String jsonString1 = "{\"name\":\"John\",\"age\":30}";

		try {
			jsonNode = objectMapper.readTree(jsonString1);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		String name = jsonNode.get("name").asText();
		int age = jsonNode.get("age").asInt();

		System.out.println("Name: " + name);
		System.out.println("Age: " + age);

	}
	
	@GetMapping("/jsonparser")
	public void jsonParseWithJsonParser () {
		
		String jsonString = "{\"name\":\"John\",\"age\":30}";

		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) parser.parse(jsonString);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String name = (String) jsonObject.get("name");
		Long age = (Long) jsonObject.get("age");

		System.out.println("Name: " + name);
		System.out.println("Age: " + age);
	}

}
