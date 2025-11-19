package it.osys.casa.ghome.trait.onoff;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import it.osys.casa.ghome.device.Device;
import it.osys.casa.ghome.exception.InvalidCommandException;
import it.osys.casa.ghome.listener.Listener;
import it.osys.casa.ghome.trait.Trait;

/**
 * https://developers.home.google.com/cloud-to-cloud/traits/onoff
 */
public class OnOffTrait extends Trait {

	private static final long serialVersionUID = 7080491514624954439L;

	// Attributes
	private boolean commandOnlyOnOff = false;

	private boolean queryOnlyOnOff = false;

	// State
	private boolean on;

	public OnOffTrait(Device device, boolean commandOnlyOnOff, boolean queryOnlyOnOff) {
		super(device, "action.devices.traits.OnOff", Set.of("action.devices.commands.OnOff"));
		this.commandOnlyOnOff = commandOnlyOnOff;
		this.queryOnlyOnOff = queryOnlyOnOff;
	}

	public boolean isCommandOnlyOnOff() {
		return commandOnlyOnOff;
	}

	public boolean isQueryOnlyOnOff() {
		return queryOnlyOnOff;
	}

	@Override
	public void addAttributes(ObjectMapper objectMapper, ObjectNode objectNode) {
		objectNode.put("commandOnlyOnOff", commandOnlyOnOff);
		objectNode.put("queryOnlyOnOff", queryOnlyOnOff);
	}

	public boolean isOn() {
		return on;
	}

	public void setOn(boolean on) {
		this.on = on;
	}

	@Override
	public Map<String, Object> getStatus(ObjectMapper objectMapper) {
		Map<String, Object> out = new HashMap<>();
		out.put("on", on);
		return out;
	}

	@Override
	public CompletableFuture<Void> executeCommand(Listener listener, String requestId, String command, JsonNode params) {
		if (!"action.devices.commands.OnOff".equals(command))
			throw new InvalidCommandException("Unknow command " + command + " in trait " + this.getTraitId());
		OnOffTraitWantListener onOffTraitWantListener = (OnOffTraitWantListener) listener;
		return onOffTraitWantListener.onWantOnOff(device, requestId, params.get("on").asBoolean());
	}

	@Override
	public String toString() {
		return "OnOffTrait [device=" + device.getId() + ", commandOnlyOnOff=" + commandOnlyOnOff + ", queryOnlyOnOff=" + queryOnlyOnOff
				+ ", on=" + on + "]";
	}

}
