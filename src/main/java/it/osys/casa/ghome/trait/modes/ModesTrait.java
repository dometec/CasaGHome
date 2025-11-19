package it.osys.casa.ghome.trait.modes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import it.osys.casa.ghome.device.Device;
import it.osys.casa.ghome.exception.InvalidCommandException;
import it.osys.casa.ghome.listener.Listener;
import it.osys.casa.ghome.trait.Trait;

/**
 * https://developers.home.google.com/cloud-to-cloud/traits/onoff
 */
public class ModesTrait extends Trait {

	private static final long serialVersionUID = -6259387948985581634L;

	// Attributes
	private boolean commandOnlyModes = false;

	private boolean queryOnlyModes = false;

	private List<Mode> modes;

	// State
	private Map<String, String> currentModeSettings = new HashMap<>();

	public ModesTrait(Device device, boolean commandOnlyModes, boolean queryOnlyModes, List<Mode> modes) {
		super(device, "action.devices.traits.Modes", Set.of("action.devices.commands.SetModes"));
		this.commandOnlyModes = commandOnlyModes;
		this.queryOnlyModes = queryOnlyModes;
		this.modes = modes;
	}

	@Override
	public void addAttributes(ObjectMapper objectMapper, ObjectNode objectNode) {
		objectNode.put("commandOnlyModes", commandOnlyModes);
		objectNode.put("queryOnlyModes", queryOnlyModes);

		ArrayNode attrs = objectMapper.createArrayNode();

		modes.forEach(mode -> {
			ObjectNode attr = objectMapper.createObjectNode();
			attr.put("name", mode.name);
			attr.put("ordered", mode.ordered);

			ArrayNode nameValues = objectMapper.createArrayNode();
			mode.nameSynonym.forEach((k, v) -> {
				ObjectNode name = objectMapper.createObjectNode();
				name.put("lang", k);
				ArrayNode nameSynonym = objectMapper.createArrayNode();
				v.forEach(n -> nameSynonym.add(n));
				name.set("name_synonym", nameSynonym);
				nameValues.add(name);
			});
			attr.set("name_values", nameValues);

			ArrayNode settings = objectMapper.createArrayNode();
			mode.settings.forEach(setting -> {
				ObjectNode sett = objectMapper.createObjectNode();
				sett.put("setting_name", setting.name);

				ArrayNode settingValues = objectMapper.createArrayNode();
				setting.values.forEach((k, v) -> {
					ObjectNode name = objectMapper.createObjectNode();
					name.put("lang", k);
					ArrayNode nameSynonym = objectMapper.createArrayNode();
					v.forEach(n -> nameSynonym.add(n));
					name.set("setting_synonym", nameSynonym);
					settingValues.add(name);
				});
				sett.set("setting_values", settingValues);

				settings.add(sett);
			});

			attr.set("settings", settings);

			attrs.add(attr);
		});

		objectNode.set("availableModes", attrs);
	}

	@Override
	public Map<String, Object> getStatus(ObjectMapper objectMapper) {
		Map<String, Object> out = new HashMap<>();
		Map<String, Object> cms = new HashMap<>();
		currentModeSettings.forEach((k, v) -> {
			cms.put(k, v);
		});
		out.put("currentModeSettings", cms);
		return out;
	}

	public void setCurrentModeSetting(String name, String value) {
		currentModeSettings.put(name, value);
	}

	@Override
	public CompletableFuture<Void> executeCommand(Listener listener, String requestId, String command, JsonNode params) {
		if (!"action.devices.commands.SetModes".equals(command))
			throw new InvalidCommandException("Unknow command " + command + " in trait " + this.getTraitId());
		JsonNode jsonNode = params.get("updateModeSettings");
		ModesTraitWantListener modesTraitWantListener = (ModesTraitWantListener) listener;
		return modesTraitWantListener.onWantMode(requestId, jsonNode.fieldNames().next(),
				jsonNode.get(jsonNode.fieldNames().next()).asText());
	}

	@Override
	public String toString() {
		return "ModesTrait [commandOnlyModes=" + commandOnlyModes + ", queryOnlyModes=" + queryOnlyModes + ", modes=" + modes
				+ ", currentModeSettings=" + currentModeSettings + "]";
	}

}
