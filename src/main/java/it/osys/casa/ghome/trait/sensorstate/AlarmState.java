package it.osys.casa.ghome.trait.sensorstate;

/**
 * The alarm state corresponding to the current sensor reading. A pre-alarm or alarm may be triggered when sensor readings approach and reach hazardous levels.
 */
public enum AlarmState {

	// The alarm is idle; detected levels are not hazardous.
	IDLE,

	// Detected levels are elevated but have not yet reached emergency levels;
	// the levels may soon become hazardous.
	PRE_ALARM_1,

	// If the device supports multiple alerts before reaching emergency levels,
	// this indicates higher detection levels than PRE_ALARM_1; the levels are
	// continuing to increase and may soon approach emergency levels.
	PRE_ALARM_2,

	// The detected levels have reached emergency levels and an alarm has been
	// triggered.
	ALARM

}
