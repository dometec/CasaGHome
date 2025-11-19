package it.osys.casa.ghome.enums;

/**
 * <a href="https://developers.home.google.com/cloud-to-cloud/intents/errors-exceptions">errors-exceptions</a>
 */
public enum ErrorCodeReasonEnum {

	CURRENTLY_ARMED("currentlyArmed",
			"Sorry, since security is already armed, you need to use <device(s)> or the app to make any changes."),

	REMOTE_UNLOCK_NOT_ALLOWED("remoteUnlockNotAllowed", "Sorry, I can't unlock <device(s)> remotely."),

	REMOTE_CONTROL_OFF("remoteControlOff", "That action is currently disabled. Please enable remote control on <device(s)> and try again."),

	CHILD_SAFETY_MODE_ACTIVE("childSafetyModeActive", "That action is disabled for <device(s)> while child safety mode is active.");

	private final String key;
	private final String message;

	ErrorCodeReasonEnum(String key, String message) {
		this.key = key;
		this.message = message;
	}

	public String key() {
		return key;
	}

	public String message() {
		return message;
	}

	public static ErrorCodeReasonEnum fromKey(String key) {
		for (ErrorCodeReasonEnum dm : values()) {
			if (dm.key.equals(key)) {
				return dm;
			}
		}
		return null;
	}

}
