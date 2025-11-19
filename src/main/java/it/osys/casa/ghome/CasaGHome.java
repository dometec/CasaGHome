package it.osys.casa.ghome;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import it.osys.casa.ghome.converter.QueryConverter;
import it.osys.casa.ghome.converter.SyncConverter;
import it.osys.casa.ghome.dto.RequestDto;
import it.osys.casa.ghome.dto.RequestInputDto;
import it.osys.casa.ghome.enums.ErrorCodeEnum;
import it.osys.casa.ghome.enums.IntentEnum;
import it.osys.casa.ghome.handler.DisconnectHandlerService;
import it.osys.casa.ghome.handler.ExecuteHandlerService;
import it.osys.casa.ghome.listener.Listener;
import it.osys.casa.ghome.trait.Trait;
import it.osys.casa.ghome.trait.onoff.OnOffTrait;
import it.osys.casa.ghome.trait.onoff.OnOffTraitWantListener;

/**
 * This class set some utility object
 */
public class CasaGHome {

	public static final Logger logger = LoggerFactory.getLogger(CasaGHome.class);

	private SyncConverter syncConverter;
	private QueryConverter queryConverter;
	private ExecuteHandlerService executeHandlerService;
	private DisconnectHandlerService disconnectHandlerService;

	private ObjectMapper objectMapper;

	private Optional<ErrorCodeEnum> generalError = Optional.empty();

	private Map<Class<? extends Trait>, Listener> listeners = new HashMap<>();

	public CasaGHome(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
		queryConverter = new QueryConverter(objectMapper);
		syncConverter = new SyncConverter(objectMapper);
		executeHandlerService = new ExecuteHandlerService(objectMapper);
		disconnectHandlerService = new DisconnectHandlerService(objectMapper);
	}

	public void setGeneralError(Optional<ErrorCodeEnum> generalError) {
		this.generalError = generalError;
	}

	public ObjectNode handleRequest(RequestDto req, String uuid, Optional<UserDevices> casa) {
		String requestId = req.requestId();
		RequestInputDto firstInput = req.inputs().getFirst();
		IntentEnum intent = firstInput.intent();

		if (generalError.isPresent()) {
			// For general error we can return general response (Cloud Update,
			// ecc.)
			ObjectNode payload = objectMapper.createObjectNode();
			payload.put("errorCode", generalError.get().key());
			payload.put("status", "ERROR");
			ObjectNode root = objectMapper.createObjectNode();
			root.put("requestId", requestId);
			root.set("payload", payload);
			return root;
		}

		logger.info("Request requestId:{} UUID({}) intent:{}.", requestId, uuid, intent);

		ObjectNode res = switch (intent) {
		case DEVICES_SYNC -> syncConverter.toSyncResponse(requestId, uuid, casa);
		case DEVICES_QUERY -> queryConverter.toQueryResponse(requestId, uuid, firstInput.payload().withArray("devices"), casa);
		case DEVICES_EXECUTE -> executeHandlerService.executeCommand(requestId, uuid, firstInput.payload().get("commands").get(0), casa,
				listeners);
		case DEVICES_DISCONNECT -> disconnectHandlerService.executeDisconnect(requestId, uuid);
		};

		logger.debug("Response {}.", res);

		return res;

	}

	public void setOnOffTraitWantListener(OnOffTraitWantListener onOffTraitWantListener) {
		listeners.put(OnOffTrait.class, onOffTraitWantListener);
	}

}
