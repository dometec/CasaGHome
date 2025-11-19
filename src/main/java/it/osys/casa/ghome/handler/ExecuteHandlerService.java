package it.osys.casa.ghome.handler;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import it.osys.casa.ghome.UserDevices;
import it.osys.casa.ghome.device.Device;
import it.osys.casa.ghome.enums.ErrorCodeEnum;
import it.osys.casa.ghome.enums.ExecuteStatus;
import it.osys.casa.ghome.handler.ExecuteHandlerService.CommandsResult.AuthPostTypesLikes;
import it.osys.casa.ghome.listener.Listener;
import it.osys.casa.ghome.trait.Trait;

/**
 * This class handle the execute requests from Google Home, the execution is sent to field
 */
public class ExecuteHandlerService {

	private static final int EXECUTE_TIMEOUT = 8;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private ObjectMapper objectMapper;

	public ExecuteHandlerService(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public ObjectNode executeCommand(String requestId, String uuid, JsonNode command, Optional<UserDevices> casa,
			Map<Class<? extends Trait>, Listener> listeners) {

		logger.debug("Command RequestId {} UUID({}): {}.", requestId, uuid, command);

		JsonNode devices = command.get("devices");
		JsonNode executions = command.get("execution");

		List<CommandsResult> cRes = devices.valueStream().map(d -> executeCommandsOn(requestId, d, executions, casa, listeners)).toList();

		// Wait all CompletableFuture (-> wait confirm commands from device(s))
		// for max 8 Seconds
		try {
			if (!cRes.isEmpty())
				CompletableFuture.allOf(cRes.stream().map(r -> r.future).toArray(CompletableFuture[]::new)).get(EXECUTE_TIMEOUT, TimeUnit.SECONDS);
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
			logger.warn("Interrupted while waiting command(s) {} from device {}.", executions, devices, ie);
		} catch (ExecutionException ee) {
			logger.warn("Execution exception while waiting command(s) {} from device {}.", executions, devices, ee);
		} catch (TimeoutException te) {
			logger.warn("Timeout waiting command(s) {} from device {}.", executions, devices, te);
		}

		return compact(requestId, cRes);

	}

	/**
	 * Execute command on each device
	 * https://developers.home.google.com/cloud-to-cloud/integration/query-execute
	 */
	private CommandsResult executeCommandsOn(String requestId, JsonNode inDevice, JsonNode executions, Optional<UserDevices> casa,
			Map<Class<? extends Trait>, Listener> listeners) {

		String id = inDevice.get("id").asText();
		if (casa.isEmpty())
			return new CommandsResult(id, ExecuteStatus.ERROR, ErrorCodeEnum.DEVICE_NOT_FOUND);

		Optional<Device> oDevice = casa.get().getDevices().stream().filter(d -> id.equals(d.getId())).findFirst();
		if (oDevice.isEmpty())
			return new CommandsResult(id, ExecuteStatus.ERROR, ErrorCodeEnum.DEVICE_NOT_FOUND);

		Device device = oDevice.get();

		CommandsResult commandsResult = new CommandsResult(id);

		Iterator<JsonNode> iterator = executions.iterator();
		while (iterator.hasNext()) {
			JsonNode exec = (JsonNode) iterator.next();

			String command = exec.get("command").asText();
			JsonNode params = exec.get("params");

			Optional<Trait> oTrait = device.getTraitByCommand(command);
			if (oTrait.isEmpty())
				return new CommandsResult(id, ExecuteStatus.ERROR, ErrorCodeEnum.DEVICE_NOT_FOUND);

			if (!device.isOnline())
				return new CommandsResult(id, ExecuteStatus.ERROR, ErrorCodeEnum.DEVICE_OFFLINE);

			Trait trait = oTrait.get();
			Listener listener = listeners.get(trait.getClass());
			CompletableFuture<Void> executeCommand = trait.executeCommand(listener, requestId, command, params);
			commandsResult.future = executeCommand.whenComplete((_, _) -> commandsResult.states = device.getStatusJson(objectMapper));

		}

		return commandsResult;

	}

	private ObjectNode compact(String requestId, List<CommandsResult> cRes) {

		// Group devices by status, states, errorCode, exceptionCode

		Map<AuthPostTypesLikes, List<CommandsResult>> collect = cRes.stream()
				.collect(Collectors.groupingBy(c -> new CommandsResult.AuthPostTypesLikes(c.status, c.states, c.errorCode)));

		ArrayNode commands = objectMapper.createArrayNode();

		collect.forEach((commonAttributes, values) -> {
			ObjectNode command = objectMapper.createObjectNode();

			ArrayNode ids = objectMapper.createArrayNode();
			values.forEach(v -> ids.add(v.id));
			command.set("ids", ids);

			command.put("status", commonAttributes.status.name());
			if (commonAttributes.errorCode != null)
				command.put("errorCode", commonAttributes.errorCode.name());

			command.set("states", commonAttributes.states);

			commands.add(command);
		});

		ObjectNode payload = objectMapper.createObjectNode();
		payload.set("commands", commands);

		ObjectNode root = objectMapper.createObjectNode();
		root.put("requestId", requestId);
		root.set("payload", payload);
		return root;
	}

	/**
	 * Result of commands executed on Device
	 */
	class CommandsResult {

		CompletableFuture<Void> future;

		String id;

		ExecuteStatus status;

		ObjectNode states;

		ErrorCodeEnum errorCode;

		record AuthPostTypesLikes(ExecuteStatus status, ObjectNode states, ErrorCodeEnum errorCode) {
		}

		public CommandsResult(String id) {
			this.id = id;
			this.status = ExecuteStatus.SUCCESS;
		}

		public CommandsResult(String id, ExecuteStatus status, ErrorCodeEnum errorCode) {
			this(id);
			this.status = status;
			this.errorCode = errorCode;
		}
	}

}
