package it.osys.casa.ghome.trait.sensorstate;

import java.util.HashMap;
import java.util.Map;

import it.osys.casa.ghome.listener.Notification;

public class SensorNotification extends Notification {

	private SensorItemName name;

	private DescriptiveCapability currentSensorState;

	public SensorNotification(SensorItemName name, DescriptiveCapability currentSensorState) {
		super();
		this.name = name;
		this.currentSensorState = currentSensorState;
	}

	@Override
	public Map<String, Object> getNotification() {

		Map<String, Object> notifAttribute = new HashMap<>();
		notifAttribute.put("priority", Notification.NOFITICATION_PRIORITY);
		notifAttribute.put("name", name.getDisplayName());
		notifAttribute.put("currentSensorState", currentSensorState.getDisplayName());

		Map<String, Object> notification = new HashMap<>();
		notification.put("SensorState", notifAttribute);

		return notification;
	}

}
