package it.osys.casa.ghome.converter;

import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import it.osys.casa.ghome.UserDevices;
import it.osys.casa.ghome.device.Device;
import it.osys.casa.ghome.enums.ErrorCodeEnum;
import it.osys.casa.ghome.enums.QueryStatus;

/**
 * Prepare the query response for the device quiery-ed from Google
 */
public class QueryConverter {

	private ObjectMapper objectMapper;

	public QueryConverter(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	/**
	 * Input: DeviceId with custom data 
	 * Output: DeviceProperty
	 * https://developers.home.google.com/cloud-to-cloud/integration/query-execute?authuser=0
	 * 
	 */
	public ObjectNode toQueryResponse(String requestId, String agentUserId, ArrayNode devicesToQuery, Optional<UserDevices> casa) {
		ObjectNode root = objectMapper.createObjectNode();
		root.put("requestId", requestId);
		ObjectNode payload = root.putObject("payload");
		ObjectNode outDevices = payload.putObject("devices");

		devicesToQuery.forEach(inDevice -> {
			outDevices.set(inDevice.get("id").asText(),
					getDevice(casa, inDevice).map(d -> d.getStatusJson(objectMapper)).orElseGet(() -> createErrorResponse()));
		});

		return root;

	}

	private ObjectNode createErrorResponse() {
		ObjectNode objectNode = objectMapper.createObjectNode();
		objectNode.put("online", false);
		objectNode.put("status", QueryStatus.ERROR.name());
		objectNode.put("errorCode", ErrorCodeEnum.DEVICE_NOT_FOUND.key());
		return objectNode;
	}

	private Optional<Device> getDevice(Optional<UserDevices> casa, JsonNode d) {
		if (casa.isEmpty())
			return Optional.empty();
		String id = d.get("id").asText();
		return casa.get().getDevices().stream().filter(device -> id.equals(device.getId())).findAny();
	}

}