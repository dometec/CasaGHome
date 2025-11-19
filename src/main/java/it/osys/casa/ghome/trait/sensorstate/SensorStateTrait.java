package it.osys.casa.ghome.trait.sensorstate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import it.osys.casa.ghome.device.Device;
import it.osys.casa.ghome.exception.InvalidCommandException;
import it.osys.casa.ghome.listener.Listener;
import it.osys.casa.ghome.trait.Trait;

/**
 * https://developers.home.google.com/cloud-to-cloud/traits/sensorstate
 */
public class SensorStateTrait extends Trait {

	private static final long serialVersionUID = -1621800819096378562L;

	public static final Logger logger = LoggerFactory.getLogger(SensorStateTrait.class);

	// Attributes and State are in Sensor
	private Set<SensorItem> sensors;

	public SensorStateTrait(Device device, Set<SensorItem> sensors) {
		super(device, "action.devices.traits.SensorState", Set.of());
		this.sensors = sensors;
	}

	public Set<SensorItem> getSensors() {
		return Set.copyOf(sensors);
	}

	@Override
	public void addAttributes(ObjectMapper objectMapper, ObjectNode objectNode) {
		ArrayNode putArray = objectNode.putArray("sensorStatesSupported");
		sensors.forEach(s -> {
			ObjectNode attr = objectMapper.createObjectNode();

			attr.put("name", s.name.getDisplayName());

			if (s.availableStates.size() > 0) {
				ArrayNode cds = objectMapper.createArrayNode();
				s.availableStates.forEach(as -> cds.add(as.getDisplayName()));
				attr.set("availableStates", cds);
			}

			s.rawValueUnit.ifPresent(r -> {
				ObjectNode nc = objectMapper.createObjectNode();
				nc.put("rawValueUnit", r.name());
				attr.set("numericCapabilities", nc);
			});

			putArray.add(attr);
		});
	}

	@Override
	public Map<String, Object> getStatus(ObjectMapper objectMapper) {
		Map<String, Object> out = new HashMap<>();

		List<Map<String, Object>> css = new ArrayList<>();

		sensors.forEach(sensor -> {

			Map<String, Object> smap = new HashMap<>();

			smap.put("name", sensor.name.getDisplayName());
			smap.put("currentSensorState", sensor.getCurrentSensorState().getDisplayName());
			smap.put("alarmState", sensor.getAlarmState().name());
			smap.put("alarmSilenceState", sensor.getAlarmSilenceState().name());

			if (sensor.getRawValue() != null)
				smap.put("rawValue", sensor.getRawValue());

			css.add(smap);
		});

		out.put("currentSensorStateData", css);

		return out;
	}

	@Override
	public CompletableFuture<Void> executeCommand(Listener listener, String requestId, String command, JsonNode params) {
		throw new InvalidCommandException("Unknow command " + command + " in trait " + this.getTraitId());
	}

}
