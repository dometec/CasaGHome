package it.osys.casa.ghome.enums;

public enum ExceptionEnum {

	BAG_FULL("bagFull", "<device(s)> <has/have> <a full bag/full bags>. Please empty <it/them> and try again."),

	BIN_FULL("binFull", "<device(s)> <has/have> <a full bin/full bins>."),

	CARBON_MONOXIDE_DETECTED("carbonMonoxideDetected", "Carbon monoxide has been detected in <house name>."),

	DEVICE_AT_EXTREME_TEMPERATURE("deviceAtExtremeTemperature", "<device(s)> <is/are> at <an extreme temperature/extreme temperatures>."),

	DEVICE_JAMMING_DETECTED("deviceJammingDetected", "<device(s)> <is/are> jammed."),

	DEVICE_MOVED("deviceMoved", "<device(s)> <was/were> moved."),

	DEVICE_OPEN("deviceOpen", "<device(s)> <is/are> open."),

	DEVICE_TAMPERED("deviceTampered", "<device(s)> <has/have> been tampered with."),

	DEVICE_UNPLUGGED("deviceUnplugged", "<device(s)> <is/are> unplugged."),

	FLOOR_UNREACHABLE("floorUnreachable", "<device(s)> can't reach that room. Please move <it/them> to the right floor and try again."),

	HARDWARE_FAILURE("hardwareFailure", "<device(s)> <has/have> a hardware problem."),

	IN_SOFTWARE_UPDATE("inSoftwareUpdate", "<device(s)> <is/are> currently in a software update."),

	IS_BYPASSED("isBypassed", "<device(s)> <is/are> currently bypassed."),

	LOW_BATTERY("lowBattery", "<device(s)> <has/have> low battery."),

	MOTION_DETECTED("motionDetected", "<device(s)> <detect(s)> motion."),

	NEEDS_PADS("needsPads", "<device(s)> <need(s)> new pads."),

	NEEDS_SOFTWARE_UPDATE("needsSoftwareUpdate", "<device(s)> <need(s)> a software update."),

	NEEDS_WATER("needsWater", "<device(s)> <need(s)> water."),

	NETWORK_JAMMING_DETECTED("networkJammingDetected", "The home network connection to <device(s)> isn't working properly."),

	NO_ISSUES_REPORTED("noIssuesReported", "<device(s)> reported no issues."),

	ROOMS_ON_DIFFERENT_FLOORS("roomsOnDifferentFloors", "<device(s)> can't reach those rooms because they're on different floors."),

	RUN_CYCLE_FINISHED("runCycleFinished", "<device(s)> <has/have> finished running."),

	SECURITY_RESTRICTION("securityRestriction", "<device(s)> <has/have> a security restriction."),

	SMOKE_DETECTED("smokeDetected", "Smoke has been detected in <house name>."),

	TANK_EMPTY("tankEmpty", "<device(s)> <has/have> <an empty tank/empty tanks>. Please fill <it/them> and try again."),

	USING_CELLULAR_BACKUP("usingCellularBackup", "<device(s)> <is/are> using cellular backup."),

	WATER_LEAK_DETECTED("waterLeakDetected", "<device(s)> <detect(s)> a water leak.");

	private final String key;
	private final String message;

	ExceptionEnum(String key, String message) {
		this.key = key;
		this.message = message;
	}

	public String key() {
		return key;
	}

	public String message() {
		return message;
	}

	public static ExceptionEnum fromKey(String key) {
		for (ExceptionEnum dm : values()) {
			if (dm.key.equals(key)) {
				return dm;
			}
		}
		return null;
	}

}
