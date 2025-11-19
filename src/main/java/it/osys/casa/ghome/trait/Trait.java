package it.osys.casa.ghome.trait;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import it.osys.casa.ghome.device.Device;
import it.osys.casa.ghome.enums.ErrorCodeEnum;
import it.osys.casa.ghome.enums.ExceptionEnum;

public abstract class Trait implements Serializable {

	private static final long serialVersionUID = 2554925294755779930L;

	protected Device device;

	protected String traitId;

	protected Set<String> commands;

	protected Optional<ErrorCodeEnum> currentErrorCode;

	protected Optional<ExceptionEnum> currentExceptionEnum;

	public Trait(Device device, String traitId, Set<String> commands) {
		this.device = device;
		this.traitId = traitId;
		this.commands = commands;
		this.currentErrorCode = Optional.empty();
		this.currentExceptionEnum = Optional.empty();
	}

	public abstract void addAttributes(ObjectNode objectNode);

	// /**
	// * Enrich the output of Query and Execute Calls.
	// */
	// public void addStatuses(ObjectNode objectNode) {
	// currentExceptionEnum.ifPresent(e -> objectNode.put("exceptionCode",
	// e.key()));
	// }

	/**
	 * Called to build the Report State call on change internal state
	 */
	public Map<String, Object> getStatus() {
		Map<String, Object> out = new HashMap<>();
		currentExceptionEnum.ifPresent(e -> out.put("exceptionCode", e.key()));
		return out;
	}

	public Map<String, Object> getNotification() {
		return Collections.emptyMap();
	}

	/**
	 * Set status from Google Home JSON parameter 
	 */
	public abstract CompletableFuture<Void> executeCommand(String requestId, String command, JsonNode params);

	public String getTraitId() {
		return traitId;
	}

	public boolean hasCommand(String command) {
		return commands.contains(command);
	}

	/**
	 * If currently there is an error
	 * @return the ErrorCode il Present
	 */
	public Optional<ErrorCodeEnum> getCurrentError() {
		return currentErrorCode;
	}

	public Optional<ExceptionEnum> getCurrentException() {
		return currentExceptionEnum;
	}

	public void setCurrentException(Optional<ExceptionEnum> currentExceptionEnum) {
		this.currentExceptionEnum = currentExceptionEnum;
	}

	public Device getDevice() {
		return device;
	}

	@Override
	public String toString() {
		return "Trait [device=" + device + ", traitId=" + traitId + ", commands=" + commands + ", currentErrorCode=" + currentErrorCode
				+ "]";
	}

}
