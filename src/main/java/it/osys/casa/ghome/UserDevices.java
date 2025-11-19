package it.osys.casa.ghome;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import it.osys.casa.ghome.device.Device;

/**
 * Container of all User device's
 */
public class UserDevices implements Serializable {

	private static final long serialVersionUID = -5432176342393331919L;

	private String agentUserId;
	private Set<Device> devices = new HashSet<>();

	public UserDevices(String agentUserId) {
		this.agentUserId = agentUserId;
	}

	public void addDevice(Device device) {
		device.setCasa(this);
		this.devices.add(device);
	}

	public void remDevice(Device device) {
		this.devices.remove(device);
	}

	public Set<Device> getDevices() {
		return Set.copyOf(devices);
	}

	public String getAgentUserId() {
		return agentUserId;
	}

	public Optional<Device> getDeviceById(String id) {
		return devices.stream().filter(d -> d.getId().equals(id)).findAny();
	}

}
