package it.osys.casa.ghome.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.node.ObjectNode;

import it.osys.casa.ghome.CasaGHomeSetup;

/**
 * this class handle the requests from GoogleHomeRest
 */
public class DisconnectHandlerService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public ObjectNode executeDisconnect(String requestId, String uuid) {
		logger.info("Disconnect from UUID: {}.", uuid);
		ObjectNode root = CasaGHomeSetup.getObjectMapper().createObjectNode();
		root.put("requestId", requestId);
		return root;
	}

}
