package it.osys.casa.ghome.trait.brightness;

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
 * https://developers.home.google.com/cloud-to-cloud/traits/brightness
 */
public class BrightnessTrait extends Trait {

	private static final long serialVersionUID = 8286360179406961221L;

	// Attributes
	private boolean commandOnlyBrightness = false;

	// States
	private int brightness;

	public BrightnessTrait(Device device, boolean commandOnlyBrightness) {
		super(device, "action.devices.traits.Brightness",
				Set.of("action.devices.commands.BrightnessAbsolute", "action.devices.commands.BrightnessRelative"));
		this.commandOnlyBrightness = commandOnlyBrightness;
	}

	@Override
	public void addAttributes(ObjectMapper objectMapper, ObjectNode objectNode) {
		objectNode.put("commandOnlyBrightness", commandOnlyBrightness);
	}

	@Override
	public Map<String, Object> getStatus(ObjectMapper objectMapper) {
		Map<String, Object> out = new HashMap<>();
		out.put("brightness", brightness);
		return out;
	}

	public void setBrightness(Integer brightness) {
		this.brightness = brightness;
	}

	public CompletableFuture<Void> wantBrightness(Listener listener, String requestId, int brightness) {
		BrightnessTraitWantListener brightnessTraitWantListener = (BrightnessTraitWantListener) listener;
		return brightnessTraitWantListener.onWantBrightness(requestId, brightness);
	}

	public int getBrightness() {
		return this.brightness;
	}

	@Override
	public CompletableFuture<Void> executeCommand(Listener listener, String requestId, String command, JsonNode params) {
		if ("action.devices.commands.BrightnessAbsolute".equals(command))
			return wantBrightness(listener, requestId, params.get("brightness").asInt());

		if ("action.devices.commands.BrightnessRelative".equals(command))
			return wantBrightness(listener, requestId, getBrightness() + params.get("brightnessRelativePercent").asInt());

		throw new InvalidCommandException("Unknow command " + command + " in trait " + this.getTraitId());
	}

	@Override
	public String toString() {
		return "BrightnessTrait [commandOnlyBrightness=" + commandOnlyBrightness + ", brightness=" + brightness + "]";
	}

}
