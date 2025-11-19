package it.osys.casa.ghome.trait.sensorstate;

import java.util.Optional;
import java.util.Set;

public class SensorItem {

	// Attributes
	SensorItemName name;

	Set<DescriptiveCapability> availableStates;

	Optional<NumericCapability> rawValueUnit;

	// State
	private DescriptiveCapability currentSensorState;

	private Double rawValue;

	private AlarmState alarmState;

	private AlarmSilenceState alarmSilenceState;

	/**
	 * Create a Sensor device that reports only numeric state value.
	 */
	public SensorItem(SensorItemName name, NumericCapability rawValueUnit) {
		this(name, Set.of(), Optional.of(rawValueUnit));
	}

	/**
	 * Create a Sensor device that reports descriptive state value.
	 */
	public SensorItem(SensorItemName name, Set<DescriptiveCapability> availableStates) {
		this(name, availableStates, Optional.empty());
	}

	/**
	 * Create a Sensor device that reports descriptive and numeric state value.
	 */
	public SensorItem(SensorItemName name, Set<DescriptiveCapability> availableStates, NumericCapability rawValueUnit) {
		this(name, availableStates, Optional.of(rawValueUnit));
	}

	/**
	 * Create a Sensor device that reports descriptive and numeric state value.
	 */
	public SensorItem(SensorItemName name, Set<DescriptiveCapability> availableStates, Optional<NumericCapability> rawValueUnit) {
		this.name = name;
		this.availableStates = availableStates;
		this.rawValueUnit = rawValueUnit;

		this.currentSensorState = DescriptiveCapability.UNKNOWN;
		this.alarmState = AlarmState.IDLE;
		this.alarmSilenceState = AlarmSilenceState.ALLOWED;
		this.rawValue = null;

	}

	public SensorItemName getName() {
		return name;
	}

	public Set<DescriptiveCapability> getAvailableStates() {
		return availableStates;
	}

	public Optional<NumericCapability> getRawValueUnit() {
		return rawValueUnit;
	}

	public DescriptiveCapability getCurrentSensorState() {
		return currentSensorState;
	}

	public void setCurrentSensorState(DescriptiveCapability currentSensorState) {
		this.currentSensorState = currentSensorState;
	}

	public Double getRawValue() {
		return rawValue;
	}

	public void setRawValue(Double rawValue) {
		this.rawValue = rawValue;
	}

	public AlarmState getAlarmState() {
		return alarmState;
	}

	public void setAlarmState(AlarmState alarmState) {
		this.alarmState = alarmState;
	}

	public AlarmSilenceState getAlarmSilenceState() {
		return alarmSilenceState;
	}

	public void setAlarmSilenceState(AlarmSilenceState alarmSilenceState) {
		this.alarmSilenceState = alarmSilenceState;
	}

}
