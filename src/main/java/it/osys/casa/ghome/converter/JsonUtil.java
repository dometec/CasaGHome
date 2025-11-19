package it.osys.casa.ghome.converter;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonUtil {

	/**
	 * Converte una {@code Map<String, Object>} in un {@link com.fasterxml.jackson.databind.node.ObjectNode} di Jackson
	 */
	public static ObjectNode mapToObjectNode(Map<String, Object> map, com.fasterxml.jackson.databind.ObjectMapper mapper) {
		if (map == null)
			return null;
		// Use ObjectMapper conversion to safely convert POJOs / Maps to Jackson ObjectNode
		return mapper.convertValue(map, ObjectNode.class);
	}

	public static ObjectNode appendMapToObjectNode(Map<String, Object> map, com.fasterxml.jackson.databind.ObjectMapper mapper,
			ObjectNode objectNode) {
		if (map == null) {
			return objectNode;
		}
		// Convert the map to an ObjectNode and merge its fields into the provided objectNode
		ObjectNode tmp = mapper.convertValue(map, ObjectNode.class);
		objectNode.setAll(tmp);
		return objectNode;
	}

	/**
	 * Aggiunge un valore all'ObjectNode convertendolo nel tipo JsonNode appropriato
	 */
	private static void addToObjectNode(ObjectNode objectNode, String key, Object value,
			com.fasterxml.jackson.databind.ObjectMapper mapper) {
		if (value == null) {
			objectNode.putNull(key);
			return;
		}
		// Delegate conversion to ObjectMapper which handles nested Maps/Lists and POJOs
		objectNode.set(key, mapper.valueToTree(value));
	}

	/**
	 * Aggiunge un elemento all'ArrayNode
	 */
	private static void addToArrayNode(ArrayNode arrayNode, Object value, com.fasterxml.jackson.databind.ObjectMapper mapper) {
		if (value == null) {
			arrayNode.addNull();
			return;
		}
		// Let ObjectMapper convert the value to a JsonNode and add it to the array
		arrayNode.add(mapper.valueToTree(value));
	}

}
