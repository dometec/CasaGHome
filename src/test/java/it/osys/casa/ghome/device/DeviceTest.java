package it.osys.casa.ghome.device;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import it.osys.casa.ghome.UserDevices;
import it.osys.casa.ghome.dto.DeviceName;

public class DeviceTest {

	@Test
	public void getStatusJson_onlineSuccess() {
		ObjectMapper mapper = new ObjectMapper();

		UserDevices casa = new UserDevices("user-1");

		Device d = new Device("dev-1", new DeviceName("MyDevice"), "action.devices.types.LIGHT") {
		};

		casa.addDevice(d);

		ObjectNode status = d.getStatusJson(mapper);

		assertNotNull(status);
		assertTrue(status.get("online").asBoolean());
		assertEquals("SUCCESS", status.get("status").asText());
	}

}
