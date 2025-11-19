package it.osys.casa.ghome.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * this class handle the requests from GoogleHomeRest
 */
public class DisconnectHandlerService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private ObjectMapper objectMapper;

	public DisconnectHandlerService(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public ObjectNode executeDisconnect(String requestId, String uuid) {
		logger.info("Disconnect from UUID: {}.", uuid);
		ObjectNode root = objectMapper.createObjectNode();
		root.put("requestId", requestId);
		return root;
	}

}
