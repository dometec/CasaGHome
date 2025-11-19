package it.osys.casa.ghome.converter;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonUtil {

	/**
	 * Converte una {@code Map<String, Object>} in un {@link com.fasterxml.jackson.databind.node.ObjectNode} di Jackson
	 */
	public static ObjectNode mapToObjectNode(Map<String, Object> map, ObjectMapper mapper) {
		if (map == null)
			return null;
		// Use ObjectMapper conversion to safely convert POJOs / Maps to Jackson
		// ObjectNode
		return mapper.convertValue(map, ObjectNode.class);
	}

	public static ObjectNode appendMapToObjectNode(Map<String, Object> map, ObjectMapper mapper, ObjectNode objectNode) {
		if (map == null) {
			return objectNode;
		}
		// Convert the map to an ObjectNode and merge its fields into the
		// provided objectNode
		ObjectNode tmp = mapper.convertValue(map, ObjectNode.class);
		objectNode.setAll(tmp);
		return objectNode;
	}

}
