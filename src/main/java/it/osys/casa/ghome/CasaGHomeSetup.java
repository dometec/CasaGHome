package it.osys.casa.ghome;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.homegraph.v1.HomeGraphService;

/**
 * This class set some utility object
 */
public class CasaGHomeSetup {

	private static ObjectMapper OBJECTMAPPER;

	private static HomeGraphService homegraphService;

	public static void setHomegraphService(HomeGraphService homegraphService) {
		CasaGHomeSetup.homegraphService = homegraphService;
	}

	public static HomeGraphService getHomegraphService() {
		return homegraphService;
	}

	public static void setObjectMapper(ObjectMapper objectMapper) {
		OBJECTMAPPER = objectMapper;
	}

	public static ObjectMapper getObjectMapper() {
		return OBJECTMAPPER;
	}

}
