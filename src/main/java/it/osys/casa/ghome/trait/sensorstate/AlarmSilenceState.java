package it.osys.casa.ghome.trait.sensorstate;

/**
 * Indicates silence state for the sensor's alarm, including whether silencing an 
 * active alarm is currently allowed. This state may be provided when a pre-alarm 
 * or alarm state is active. For a Smoke Detector, the values ALLOWED and 
 * DISALLOWED are not used. The Google Home app has built-in logic to 
 * determine if an alarm can be silenced from the app.
 */
public enum AlarmSilenceState {

	// The alarm is not silenced and silencing the alarm is currently allowed.
	ALLOWED,

	// The alarm is not silenced and silencing the alarm is currently not
	// allowed.
	DISALLOWED,

	// The alarm has been silenced.
	SILENCED,

}
