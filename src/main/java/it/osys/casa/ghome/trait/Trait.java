package it.osys.casa.ghome.trait;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import it.osys.casa.ghome.device.Device;
import it.osys.casa.ghome.listener.Listener;

public abstract class Trait implements Serializable {

	private static final long serialVersionUID = 2554925294755779930L;

	protected Device device;

	protected String traitId;

	protected Set<String> commands;

	public Trait(Device device, String traitId, Set<String> commands) {
		this.device = device;
		this.traitId = traitId;
		this.commands = commands;
	}

	/**
	 * To build the Sync Response
	 */
	public abstract void addAttributes(ObjectMapper objectMapper, ObjectNode objectNode);

	/**
	 * To build the Report State
	 */
	public abstract Map<String, Object> getStatus(ObjectMapper objectMapper);

	/**
	 * Set status from Google Home JSON parameter 
	 */
	public abstract CompletableFuture<Void> executeCommand(Listener Listener, String requestId, String command, JsonNode params);

	public String getTraitId() {
		return traitId;
	}

	public boolean hasCommand(String command) {
		return commands.contains(command);
	}

	public Device getDevice() {
		return device;
	}

	@Override
	public String toString() {
		return "Trait [device=" + device + ", traitId=" + traitId + ", commands=" + commands + "]";
	}

}
