package it.osys.casa.ghome.device;

import java.util.Optional;
import java.util.Set;

import it.osys.casa.ghome.dto.DeviceName;
import it.osys.casa.ghome.trait.sensorstate.SensorItem;
import it.osys.casa.ghome.trait.sensorstate.SensorStateTrait;

/**
 * https://developers.home.google.com/cloud-to-cloud/guides/garage
 */
public class Sensor extends Device {

	private static final long serialVersionUID = 7553914945148692213L;

	// Optional
	private SensorStateTrait sensorStateTrait;

	// Optional TODO
	// action.devices.traits.EnergyStorage

	public Sensor(String id, DeviceName name, boolean notificationSupportedByAgent) {
		super(id, name, "action.devices.types.SENSOR");
		this.setNotificationSupportedByAgent(Optional.of(notificationSupportedByAgent));
	}

	// Mandatory
	public Sensor withSensorStateTrait(Set<SensorItem> sensors) {
		sensorStateTrait = new SensorStateTrait(this, sensors);
		addTraits(sensorStateTrait);
		return this;
	}

	public SensorStateTrait getSensorStateTrait() {
		return sensorStateTrait;
	}

}
