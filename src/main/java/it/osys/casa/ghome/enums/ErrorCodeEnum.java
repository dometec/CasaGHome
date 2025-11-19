package it.osys.casa.ghome.enums;

/**
 * <a href="https://developers.home.google.com/cloud-to-cloud/intents/errors-exceptions">errors-exceptions</a>
 */
public enum ErrorCodeEnum {

	ABOVE_MAXIMUM_LIGHT_EFFECTS_DURATION("aboveMaximumLightEffectsDuration",
			"That's more than the maximum duration of 1 hour. Please try again."),

	ABOVE_MAXIMUM_TIMER_DURATION("aboveMaximumTimerDuration", "I can only set <device(s)> for up to <time period>"),

	ACTION_NOT_AVAILABLE("actionNotAvailable", "Sorry, I can't seem to do that right now."),

	ACTION_UNAVAILABLE_WHILE_RUNNING("actionUnavailableWhileRunning",
			"<device(s)> <is/are> currently running, so I can't make any changes."),

	ALREADY_ARMED("alreadyArmed", "<device(s)> <is/are> already armed."),

	ALREADY_AT_MAX("alreadyAtMax", "<device(s)> <is/are> already set to the maximum temperature."),

	ALREADY_AT_MIN("alreadyAtMin", "<device(s)> <is/are> already set to the minimum temperature."),

	ALREADY_CLOSED("alreadyClosed", "<device(s)> <is/are> already closed."),

	ALREADY_DISARMED("alreadyDisarmed", "<device(s)> <is/are> already disarmed."),

	ALREADY_DOCKED("alreadyDocked", "<device(s)> <is/are> already docked."),

	ALREADY_IN_STATE("alreadyInState", "<device(s)> <is/are> already in that state."),

	ALREADY_LOCKED("alreadyLocked", "<device(s)> <is/are> already locked."),

	ALREADY_OFF("alreadyOff", "<device(s)> <is/are> already off."),

	ALREADY_ON("alreadyOn", "<device(s)> <is/are> already on."),

	ALREADY_OPEN("alreadyOpen", "<device(s)> <is/are> already open."),

	ALREADY_PAUSED("alreadyPaused", "<device(s)> <is/are> already paused."),

	ALREADY_STARTED("alreadyStarted", "<device(s)> <is/are> already started."),

	ALREADY_STOPPED("alreadyStopped", "<device(s)> <is/are> already stopped."),

	ALREADY_UNLOCKED("alreadyUnlocked", "<device(s)> <is/are> already unlocked."),

	AMBIGUOUS_ZONE_NAME("ambiguousZoneName",
			"Sorry, <device(s)> can't identify which zone you mean. Please make sure your zones have unique names and try again."),

	AMOUNT_ABOVE_LIMIT("amountAboveLimit", "That's more than what <device(s)> can support."),

	APP_LAUNCH_FAILED("appLaunchFailed", "Sorry, failed to launch <app name> on <device(s)>."),

	ARM_FAILURE("armFailure", "<device(s)> couldn't be armed."),

	ARM_LEVEL_NEEDED("armLevelNeeded",
			"I'm not sure which level to set <device(s)> to. Try saying \"Set <device(s)> to <low security>\" or \"Set <device(s)> to <high security>\""),

	AUTH_FAILURE("authFailure",
			"I can't seem to reach <device(s)>. Try checking the app to make sure your <device/devices> <is/are> fully set up."),

	BAG_FULL("bagFull", "<device(s)> <has/have> <a full bag/full bags>. Please empty <it/them> and try again."),

	BELOW_MINIMUM_LIGHT_EFFECTS_DURATION("belowMinimumLightEffectsDuration",
			"That's less than the minimum duration of 5 minutes. Please try again."),

	BELOW_MINIMUM_TIMER_DURATION("belowMinimumTimerDuration", "I can't set <device(s)> for such a short time. Please try again."),

	BIN_FULL("binFull", "<device(s)> <has/have> <a full bin/full bins>."),

	CANCEL_ARMING_RESTRICTED("cancelArmingRestricted", "Sorry, I couldn't cancel arming <device(s)>."),

	CANCEL_TOO_LATE("cancelTooLate", "Sorry, it's too late to cancel. Use <device(s)> or the app instead."),

	CHANNEL_SWITCH_FAILED("channelSwitchFailed", "Sorry, failed to switch to channel <channel name>. Please try again later."),

	CHARGER_ISSUE("chargerIssue", "Sorry, it looks like <device(s)> <has/have> <a charger issue/charger issues>."),

	COMMAND_INSERT_FAILED("commandInsertFailed", "Unable to process commands for <device(s)>."),

	DEAD_BATTERY("deadBattery", "<device(s)> <has/have> <a dead battery/dead batteries>."),

	DEGREES_OUT_OF_RANGE("degreesOutOfRange", "The requested degrees are out of range for <device(s)>."),

	DEVICE_ALERT_NEEDS_ASSISTANCE("deviceAlertNeedsAssistance", "<device(s)> <has/have> an active alert and <need(s)> your assistance."),

	DEVICE_AT_EXTREME_TEMPERATURE("deviceAtExtremeTemperature", "<device(s)> <is/are> at <an extreme temperature/extreme temperatures>."),

	DEVICE_BUSY("deviceBusy", "Sorry, it looks like <device(s)> is already doing something right now."),

	DEVICE_CHARGING("deviceCharging", "Sorry, it looks like <device(s)> can't do that because <it/they> are charging."),

	DEVICE_CLOGGED("deviceClogged", "Sorry, it looks like <device(s)> is clogged."),

	DEVICE_CURRENTLY_DISPENSING("deviceCurrentlyDispensing", "<device(s)> is already dispensing something right now."),

	DEVICE_DOOR_OPEN("deviceDoorOpen", "The door is open on <device(s)>. Please close it and try again."),

	DEVICE_HANDLE_CLOSED("deviceHandleClosed", "The handle is closed on <device(s)>. Please open it and try again."),

	DEVICE_JAMMING_DETECTED("deviceJammingDetected", "<device(s)> <is/are> jammed."),

	DEVICE_LID_OPEN("deviceLidOpen", "The lid is open on <device(s)>. Please close it and try again."),

	DEVICE_NEEDS_REPAIR("deviceNeedsRepair", "<device(s)> <need(s)> to be repaired. Please contact your local service dealer."),

	DEVICE_NOT_DOCKED("deviceNotDocked", "Sorry, it looks like <device(s)> <isn't/aren't> docked. Please dock <it/them> and try again."),

	DEVICE_NOT_FOUND("deviceNotFound", "<device(s)> <is/are>n't available. You might want to try setting <it/them> up again."),

	DEVICE_NOT_MOUNTED("deviceNotMounted", "Sorry, it looks like <device(s)> can't do that because <it/they> <is/are>n't mounted."),

	DEVICE_NOT_READY("deviceNotReady", "<device(s)> <is/are>n't ready."),

	DEVICE_STUCK("deviceStuck", "<device(s)> <is/are> stuck and needs your help."),

	DEVICE_TAMPERED("deviceTampered", "<device(s)> <has/have> been tampered with."),

	DEVICE_THERMAL_SHUTDOWN("deviceThermalShutdown", "Sorry, it looks like <device(s)> shut down due to extreme temperatures."),

	DIRECT_RESPONSE_ONLY_UNREACHABLE("directResponseOnlyUnreachable", "<device(s)> <doesn't/don't> support remote control."),

	DISARM_FAILURE("disarmFailure", "<device(s)> couldn't be disarmed."),

	DISCRETE_ONLY_OPEN_CLOSE("discreteOnlyOpenClose", "Sorry, <device(s)> can only be opened or closed all the way."),

	DISPENSE_AMOUNT_ABOVE_LIMIT("dispenseAmountAboveLimit", "<device(s)> can't dispense such a large amount."),

	DISPENSE_AMOUNT_BELOW_LIMIT("dispenseAmountBelowLimit", "<device(s)> can't dispense such a small amount."),

	DISPENSE_AMOUNT_REMAINING_EXCEEDED("dispenseAmountRemainingExceeded", "<device(s)> doesn't have enough <dispense item> to do that."),

	DISPENSE_FRACTIONAL_AMOUNT_NOT_SUPPORTED("dispenseFractionalAmountNotSupported",
			"<device(s)> can't dispense fractions of <dispense item>."),

	DISPENSE_FRACTIONAL_UNIT_NOT_SUPPORTED("dispenseFractionalUnitNotSupported",
			"<device(s)> doesn't support fractions of that unit for <dispense item>."),

	DISPENSE_UNIT_NOT_SUPPORTED("dispenseUnitNotSupported", "<device(s)> doesn't support that unit for <dispense item>."),

	DOOR_CLOSED_TOO_LONG("doorClosedTooLong",
			"It's been a while since the door on <device(s)> has been opened. Please open the door, make sure there's something inside, and try again."),

	EMERGENCY_HEAT_ON("emergencyHeatOn", "<device(s)> <is/are> in Emergency Heat Mode, so <it/they>'ll have to be adjusted by hand."),

	FAULTY_BATTERY("faultyBattery", "<device(s)> <has/have> <a faulty battery/faulty batteries>."),

	FLOOR_UNREACHABLE("floorUnreachable", "<device(s)> can't reach that room. Please move <it/them> to the right floor and try again."),

	FUNCTION_NOT_SUPPORTED("functionNotSupported", "Actually, <device(s)> <doesn't/don't> support that functionality."),

	GENERIC_DISPENSE_NOT_SUPPORTED("genericDispenseNotSupported",
			"I need to know what you would like to dispense. Please try again with the name of the item."),

	HARD_ERROR("hardError", "Sorry, something went wrong and I'm unable to control your home device."),

	IN_AUTO_MODE("inAutoMode",
			"<device(s)> <is/are> currently set to auto mode. To change the temperature, you'll need to switch <it/them> to a different mode."),

	IN_AWAY_MODE("inAwayMode",
			"<device(s)> <is/are> currently set to away mode. To control your thermostat, switch it to home mode using the Nest app."),

	IN_DRY_MODE("inDryMode", "<device(s)> <is/are> currently set to dry mode. To change the temperature, switch it to a different mode."),

	IN_ECO_MODE("inEcoMode", "<device(s)> <is/are> currently set to eco mode."),

	IN_FAN_ONLY_MODE("inFanOnlyMode", "<device(s)> <is/are> currently set to fan-only mode."),

	IN_HEAT_OR_COOL("inHeatOrCool", "<device(s)> <is/are>n't in heat/cool mode."),

	IN_HUMIDIFIER_MODE("inHumidifierMode", "<device(s)> <is/are> currently set to humidifier mode."),

	IN_OFF_MODE("inOffMode", "<device(s)> <is/are> currently off."),

	IN_PURIFIER_MODE("inPurifierMode", "<device(s)> <is/are> currently set to purifier mode."),

	IN_SLEEP_MODE("inSleepMode", "<device(s)> <is/are> in sleep mode. Please try again later."),

	IN_SOFTWARE_UPDATE("inSoftwareUpdate", "<device(s)> <is/are> currently in a software update."),

	LOCK_FAILURE("lockFailure", "<device(s)> couldn't be locked."),

	LOCKED_STATE("lockedState", "<device(s)> <is/are> currently locked."),

	LOCKED_TO_RANGE("lockedToRange", "That temperature is outside the locked range on <device(s)>."),

	LOW_BATTERY("lowBattery", "<device(s)> <has/have> low battery."),

	MAX_SETTING_REACHED("maxSettingReached", "<device(s)> <is/are> already set to the highest setting."),

	MAX_SPEED_REACHED("maxSpeedReached", "<device(s)> <is/are> already set to the maximum speed."),

	MIN_SETTING_REACHED("minSettingReached", "<device(s)> <is/are> already set to the lowest setting."),

	MIN_SPEED_REACHED("minSpeedReached", "<device(s)> <is/are> already set to the minimum speed."),

	MONITORING_SERVICE_CONNECTION_LOST("monitoringServiceConnectionLost",
			"<device(s)> <has/have> lost <its/their> connection to the monitoring service."),

	NEEDS_ATTACHMENT("needsAttachment",
			"Sorry, it looks like <device(s)> <is/are> missing a required attachment. Please replace it and try again."),

	NEEDS_BIN("needsBin", "Sorry, it looks like <device(s)> <is/are> missing a bin. Please replace it and try again."),

	NEEDS_PADS("needsPads", "<device(s)> <need(s)> new pads."),

	NEEDS_SOFTWARE_UPDATE("needsSoftwareUpdate", "<device(s)> <need(s)> a software update."),

	NEEDS_WATER("needsWater", "<device(s)> <need(s)> water."),

	NETWORK_PROFILE_NOT_RECOGNIZED("networkProfileNotRecognized", "Sorry, I don't recognize \"<network profile>\" on <device(s)>."),

	NETWORK_SPEED_TEST_IN_PROGRESS("networkSpeedTestInProgress", "I'm already testing the <network> <speed/speeds>."),

	NO_AVAILABLE_APP("noAvailableApp", "Sorry, it looks like <app name> isn't available."),

	NO_AVAILABLE_CHANNEL("noAvailableChannel", "Sorry, it looks like channel <channel name> isn't available."),

	NO_CHANNEL_SUBSCRIPTION("noChannelSubscription", "Sorry, you aren't subscribed to channel <channel name> at the moment."),

	NO_TIMER_EXISTS("noTimerExists", "Sorry, it looks like there aren't any timers set on <device(s)>."),

	NOT_SUPPORTED("notSupported", "Sorry, that mode isn't available for <device(s)>."),

	OBSTRUCTION_DETECTED("obstructionDetected", "<device(s)> detected an obstruction."),

	OFFLINE("offline", "Sorry, it looks like <device(s)> <is/are>n't available right now."),

	DEVICE_OFFLINE("deviceOffline", "Sorry, it looks like <device(s)> <is/are>n't available right now."),

	ON_REQUIRES_MODE("onRequiresMode", "Please specify which mode you want to turn on."),

	PASSPHRASE_INCORRECT("passphraseIncorrect", "Sorry, it looks like that PIN is incorrect."),

	PERCENT_OUT_OF_RANGE("percentOutOfRange", "Sorry, I can't set <device(s)> to <percent>."),

	PIN_INCORRECT("pinIncorrect", "(passphraseIncorrect)"),

	RAIN_DETECTED("rainDetected", "I didn't open <device(s)> because rain was detected."),

	RANGE_TOO_CLOSE("rangeTooClose", "Those are too close for a Heat-Cool range for <device(s)>. Choose temperatures farther apart."),

	RELINK_REQUIRED("relinkRequired", "Please use the Google Home or Assistant App to re-link <device(s)>."),

	REMOTE_SET_DISABLED("remoteSetDisabled", "Remote Set Disabled"),

	ROOMS_ON_DIFFERENT_FLOORS("roomsOnDifferentFloors", "<device(s)> can't reach those rooms because they're on different floors."),

	SAFETY_SHUT_OFF("safetyShutOff", "<device(s)> <is/are> in Safety Shut-Off Mode."),

	SCENE_CANNOT_BE_APPLIED("sceneCannotBeApplied", "Sorry, <device(s)> can't be applied."),

	SECURITY_RESTRICTION("securityRestriction", "<device(s)> <has/have> a security restriction."),

	SOFTWARE_UPDATE_NOT_AVAILABLE("softwareUpdateNotAvailable", "Sorry, there's no software update available on <device(s)>."),

	START_REQUIRES_TIME("startRequiresTime", "Tell me how long you'd like to run <device(s)>."),

	STILL_COOLING_DOWN("stillCoolingDown", "<device(s)> <is/are> still cooling down."),

	STILL_WARMING_UP("stillWarmingUp", "<device(s)> <is/are> still warming up."),

	STREAM_UNAVAILABLE("streamUnavailable", "Sorry, the stream is unavailable from <device(s)>."),

	STREAM_UNPLAYABLE("streamUnplayable", "Sorry, I can't play the stream from <device(s)> at the moment."),

	TANK_EMPTY("tankEmpty", "<device(s)> <has/have> <an empty tank/empty tanks>. Please fill <it/them> and try again."),

	TARGET_ALREADY_REACHED("targetAlreadyReached", "That's already the current temperature."),

	TIMER_VALUE_OUT_OF_RANGE("timerValueOutOfRange", "<device(s)> can't be set for that amount of time."),

	TOO_MANY_FAILED_ATTEMPTS("tooManyFailedAttempts", "Too many failed attempts. Use the app instead."),

	TRANSIENT_ERROR("transientError", "Something went wrong controlling <device(s)>. Try again."),

	TURNED_OFF("turnedOff", "<device(s)> <is/are> off right now."),

	DEVICE_TURNED_OFF("deviceTurnedOff", "<device(s)> <is/are> off right now."),

	UNABLE_TO_LOCATE_DEVICE("unableToLocateDevice", "I wasn't able to locate <device(s)>."),

	UNKNOWN_FOOD_PRESET("unknownFoodPreset", "<device(s)> doesn't support that food preset."),

	UNLOCK_FAILURE("unlockFailure", "<device(s)> couldn't be unlocked."),

	UNPAUSABLE_STATE("unpausableState", "<device(s)> can't be paused right now."),

	USER_CANCELLED("userCancelled", "ok"),

	VALUE_OUT_OF_RANGE("valueOutOfRange", "<device(s)> can't be set to that temperature.");

	private final String key;
	private final String message;

	ErrorCodeEnum(String key, String message) {
		this.key = key;
		this.message = message;
	}

	public String key() {
		return key;
	}

	public String message() {
		return message;
	}

	public static ErrorCodeEnum fromKey(String key) {
		for (ErrorCodeEnum dm : values()) {
			if (dm.key.equals(key))
				return dm;
		}
		return null;
	}

}
