package it.osys.casa.ghome.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;

public enum IntentEnum {
	/**
	 * Assistant sends an action.devices.SYNC intent to your fulfillment to request the list of devices associated with the given user and their capabilities. Your fulfillment must return a unique ID for each user in the agentUserId field of the SYNC response. This ID must be an immutable value to represent the user to your cloud service. It is not recommended to provide email addresses or other attributes based on settings the user can change.<br>
	 * The devices field of your SYNC response contains all of the devices the user has authorized Assistant to access, the types and traits they support, and the attributes required to configure the trait's behavior for that specific device.<br>
	 * The SYNC intent is triggered during account linking or when a user manually resyncs their devices. If the users' list of devices, supported traits, or attribute values change, use Request Sync to trigger a new SYNC intent and report the updates to Google.<br>
	 * <br>
	 * <a href="https://developers.home.google.com/cloud-to-cloud/integration/sync#sync-response">sync-response</a>
	 */
	DEVICES_SYNC("action.devices.SYNC"),
	/**
	 * When users interact with Google Assistant to query the current state of a device, your fulfillment receives an action.devices.QUERY intent containing a list of device IDs (as provided by your SYNC response).<br>
	 * <a href="https://developers.home.google.com/cloud-to-cloud/integration/query-execute#handle_query_intents">handle_query_intents</a><br>
	 * <a href="https://developers.home.google.com/cloud-to-cloud/intents/query">intents/query</a>
	 */
	DEVICES_QUERY("action.devices.QUERY"),
	/**
	 * Your fulfillment receives an action.devices.EXECUTE intent when users send commands to Assistant to control your device<br>
	 * Similar to QUERY, a single intent can target multiple device IDs. A single EXECUTE intent may also contain multiple distinct commands given to a group of devices. For example, a triggered intent may set both brightness and color on a group of lights, or set multiple lights each to a different color. Your EXECUTE response should return the new state of the device after the execution.<br>
	 * <a href="https://developers.home.google.com/cloud-to-cloud/integration/query-execute#handle_execute_intents">handle_execute_intents</a><br>
	 * <a href="https://developers.home.google.com/cloud-to-cloud/intents/execute">intents/execute</a>
	 */
	DEVICES_EXECUTE("action.devices.EXECUTE"),
	/**
	 * This intent is triggered to inform you when a user has unlinked their device account from Google Assistant.
	 * This intent indicates that Google Assistant will not send additional intents for this user. After receiving the DISCONNECT intent, your cloud service should cease publishing changes to Google with Request Sync and Report State.<br>
	 * <a href="https://developers.home.google.com/cloud-to-cloud/intents/disconnect">intents/disconnect</a>
	 */
	DEVICES_DISCONNECT("action.devices.DISCONNECT");

	private final String code;

	IntentEnum(String code) {
		this.code = code;
	}

	@JsonCreator
	public static IntentEnum forValue(String value) {
		return Arrays.stream(values()).filter(e -> e.code.equals(value)).findFirst().orElseThrow();
	}

	@Override
	public String toString() {
		return this.code;
	}
}
