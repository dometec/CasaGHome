package it.osys.casa.ghome.converter;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import it.osys.casa.ghome.UserDevices;
import it.osys.casa.ghome.dto.BooleanValue;
import it.osys.casa.ghome.dto.IntegerValue;
import it.osys.casa.ghome.dto.StringValue;

public class SyncConverter {

	private ObjectMapper objectMapper;

	public SyncConverter(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public ObjectNode toSyncResponse(String requertId, String agentUserId, Optional<UserDevices> casa) {
		ObjectNode root = objectMapper.createObjectNode();
		root.put("requestId", requertId);

		ObjectNode payload = root.putObject("payload");
		payload.put("agentUserId", agentUserId);
		ArrayNode devices = payload.putArray("devices");

		if (casa.isEmpty())
			return root;

		casa.get().getDevices().forEach(device -> {
			ObjectNode object = devices.addObject();
			object.put("id", device.getId());
			object.put("type", device.getType().toString());

			ArrayNode traits = object.putArray("traits");
			ObjectNode attributes = object.putObject("attributes");

			device.getTraits().forEach(t -> {
				traits.add(t.getTraitId());
				t.addAttributes(objectMapper, attributes);
			});

			ObjectNode name = object.putObject("name");

			if (device.getName().defaultNames() != null) {
				ArrayNode defaultNames = name.putArray("defaultNames");
				device.getName().defaultNames().forEach(dn -> defaultNames.add(dn));
			}

			name.put("name", device.getName().name());

			if (device.getName().defaultNames() != null) {
				ArrayNode nicknames = name.putArray("nicknames");
				device.getName().nicknames().forEach(dn -> nicknames.add(dn));
			}

			object.put("willReportState", device.isWillReportState());

			if (device.getRoomHint() != null)
				object.put("roomHint", device.getRoomHint());

			if (device.getNotificationSupportedByAgent().isPresent())
				object.put("notificationSupportedByAgent", device.getNotificationSupportedByAgent().get());

			if (device.getDeviceInfo() != null) {
				ObjectNode deviceInfo = object.putObject("deviceInfo");
				if (device.getDeviceInfo().manufacturer() != null)
					deviceInfo.put("manufacturer", device.getDeviceInfo().manufacturer());
				if (device.getDeviceInfo().model() != null)
					deviceInfo.put("model", device.getDeviceInfo().model());
				if (device.getDeviceInfo().hwVersion() != null)
					deviceInfo.put("hwVersion", device.getDeviceInfo().hwVersion());
				if (device.getDeviceInfo().swVersion() != null)
					deviceInfo.put("swVersion", device.getDeviceInfo().swVersion());
			}

			// TODO otherDeviceIds

			ObjectNode customData = object.putObject("customData");
			device.getCustomData().forEach((k, v) -> {
				switch (v) {
				case StringValue s -> customData.put(k, s.value());
				case IntegerValue n -> customData.put(k, n.value());
				case BooleanValue b -> customData.put(k, b.value());
				}
			});

		});

		return root;

	}
}