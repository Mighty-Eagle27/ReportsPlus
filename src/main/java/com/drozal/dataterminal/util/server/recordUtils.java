package com.drozal.dataterminal.util.server;

import com.drozal.dataterminal.util.Misc.LogUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static com.drozal.dataterminal.util.Misc.LogUtils.log;

/**
 * The type Record utils.
 */
public class recordUtils {
	
	/**
	 * Parse world ped data.
	 *
	 * @param data the data
	 */
	public static void parseWorldPedData(String data) {
		String[] pedestrians = data.split(",");
		for (String pedestrian : pedestrians) {
			String[] attributes = pedestrian.split("&");
			for (String attribute : attributes) {
				String[] keyValue = attribute.split("=");
				if (keyValue.length > 1) {
					System.out.println(keyValue[0] + ": " + keyValue[1].trim());
				} else {
					System.out.println(keyValue[0] + ": " + "No value provided");
				}
			}
			System.out.println();
		}
	}
	
	/**
	 * Search for ped attribute string.
	 *
	 * @param data              the data
	 * @param pedName           the ped name
	 * @param requiredAttribute the required attribute
	 * @return the string
	 */
	public static String searchForPedAttribute(String data, String pedName, String requiredAttribute) {
		String[] pedestrians = data.split(",");
		for (String pedestrian : pedestrians) {
			Map<String, String> attributesMap = new HashMap<>();
			String[] attributes = pedestrian.split("&");
			for (String attribute : attributes) {
				String[] keyValue = attribute.split("=");
				if (keyValue.length > 1) {
					attributesMap.put(keyValue[0].toLowerCase(), keyValue[1].trim());
				} else {
					attributesMap.put(keyValue[0].toLowerCase(), "No value provided");
				}
			}
			
			if (attributesMap.containsKey("name") && attributesMap.get("name")
					.equalsIgnoreCase(pedName)) {
				return attributesMap.getOrDefault(requiredAttribute.toLowerCase(), "Attribute not found");
			}
		}
		return "Pedestrian not found";
	}
	
	/**
	 * Parse world vehicle data.
	 *
	 * @param filePath the file path
	 * @throws IOException the io exception
	 */
	public static void parseWorldVehicleData(String filePath) throws IOException {
		byte[] encodedBytes = Files.readAllBytes(Paths.get(filePath));
		String data = new String(encodedBytes);
		
		String[] vehicles = data.split(",");
		for (String vehicle : vehicles) {
			String[] attributes = vehicle.split("&");
			for (String attribute : attributes) {
				String[] keyValue = attribute.split("=");
				if (keyValue.length > 1) {
					System.out.println(keyValue[0] + ": " + keyValue[1].trim());
				} else {
					System.out.println(keyValue[0] + ": " + "No value provided");
				}
			}
			System.out.println();
		}
	}
	
	/**
	 * Grab ped data map.
	 *
	 * @param filePath the file path
	 * @param pedName  the ped name
	 * @return the map
	 * @throws IOException the io exception
	 */
	public static Map<String, String> grabPedData(String filePath, String pedName) throws IOException {
		if (!Files.exists(Paths.get(filePath))) {
			log("File does not exist: " + filePath, LogUtils.Severity.ERROR);
			return new HashMap<>();
		}
		
		byte[] encodedBytes = Files.readAllBytes(Paths.get(filePath));
		String data = new String(encodedBytes);
		
		String[] pedestrians = data.split(",");
		for (String pedestrian : pedestrians) {
			Map<String, String> attributesMap = new HashMap<>();
			String[] attributes = pedestrian.split("&");
			for (String attribute : attributes) {
				String[] keyValue = attribute.split("=");
				if (keyValue.length > 1) {
					attributesMap.put(keyValue[0].toLowerCase(), keyValue[1].trim());
				} else {
					attributesMap.put(keyValue[0].toLowerCase(), "No value provided");
				}
			}
			
			if (attributesMap.containsKey("name") && attributesMap.get("name")
					.equalsIgnoreCase(pedName)) {
				return attributesMap;
			}
		}
		
		Map<String, String> notFoundMap = new HashMap<>();
		notFoundMap.put("Error", "Pedestrian not found");
		return notFoundMap;
	}
	
	/**
	 * Grab vehicle data map.
	 *
	 * @param filePath     the file path
	 * @param licensePlate the license plate
	 * @return the map
	 * @throws IOException the io exception
	 */
	public static Map<String, String> grabVehicleData(String filePath, String licensePlate) throws IOException {
		if (!Files.exists(Paths.get(filePath))) {
			log("File does not exist: " + filePath, LogUtils.Severity.ERROR);
			return new HashMap<>();
		}
		
		String normalizedLicensePlate = licensePlate.toLowerCase();
		byte[] encodedBytes = Files.readAllBytes(Paths.get(filePath));
		String data = new String(encodedBytes);
		
		String[] vehicles = data.split(",");
		for (String vehicle : vehicles) {
			Map<String, String> attributesMap = new HashMap<>();
			String[] attributes = vehicle.split("&");
			for (String attribute : attributes) {
				String[] keyValue = attribute.split("=");
				if (keyValue.length > 1) {
					attributesMap.put(keyValue[0], keyValue[1].trim());
				} else {
					attributesMap.put(keyValue[0], "no value provided");
				}
			}
			
			if (attributesMap.getOrDefault("licensePlate", "")
					.toLowerCase()
					.equals(normalizedLicensePlate)) {
				return attributesMap;
			}
		}
		
		Map<String, String> notFoundMap = new HashMap<>();
		notFoundMap.put("error", "vehicle not found");
		return notFoundMap;
	}
	
	/**
	 * Search for vehicle attribute string.
	 *
	 * @param data              the data
	 * @param licensePlate      the license plate
	 * @param requiredAttribute the required attribute
	 * @return the string
	 */
	public static String searchForVehicleAttribute(String data, String licensePlate, String requiredAttribute) {
		String[] vehicles = data.split(",");
		for (String vehicle : vehicles) {
			Map<String, String> attributesMap = new HashMap<>();
			String[] attributes = vehicle.split("&");
			for (String attribute : attributes) {
				String[] keyValue = attribute.split("=");
				if (keyValue.length > 1) {
					attributesMap.put(keyValue[0].toLowerCase(), keyValue[1].trim());
				} else {
					attributesMap.put(keyValue[0].toLowerCase(), "No value provided");
				}
			}
			if (attributesMap.getOrDefault("licenseplate", "")
					.equalsIgnoreCase(licensePlate)) {
				return attributesMap.getOrDefault(requiredAttribute.toLowerCase(), "Attribute not found");
			}
		}
		return "Vehicle not found";
	}
	
}
