package it.osys.casa.ghome.device;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import it.osys.casa.ghome.UserDevices;
import it.osys.casa.ghome.converter.JsonUtil;
import it.osys.casa.ghome.dto.CustomDataValue;
import it.osys.casa.ghome.dto.DeviceInfoDto;
import it.osys.casa.ghome.dto.DeviceName;
import it.osys.casa.ghome.dto.OtherDeviceId;
import it.osys.casa.ghome.enums.ErrorCodeEnum;
import it.osys.casa.ghome.enums.ErrorCodeReasonEnum;
import it.osys.casa.ghome.enums.ExceptionEnum;
import it.osys.casa.ghome.enums.QueryStatus;
import it.osys.casa.ghome.trait.Trait;

public abstract class Device implements Serializable {

	private static final long serialVersionUID = -2282185279273658402L;

	/**
	 * The Casa this device belong to
	 */
	protected UserDevices casa;

	protected String id;

	protected DeviceName name;

	protected String type;

	protected boolean willReportState;

	protected String roomHint;

	protected DeviceInfoDto deviceInfo;

	protected Set<OtherDeviceId> otherDeviceIds;

	protected Map<String, CustomDataValue> customData;

	protected Set<Trait> traits;

	protected boolean online;

	protected Optional<Boolean> notificationSupportedByAgent;

	protected Optional<ErrorCodeEnum> errorCode;

	protected Optional<ErrorCodeEnum> currentErrorCode;

	protected Optional<ErrorCodeReasonEnum> currentErrorCoreReason;

	protected Optional<ExceptionEnum> currentException;

	public Device(String id, DeviceName name, String type) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.online = true;
		this.willReportState = true;

		this.customData = new HashMap<>();
		this.traits = new HashSet<>();

		this.otherDeviceIds = Collections.emptySet();
		this.notificationSupportedByAgent = Optional.empty();
		this.currentErrorCode = Optional.empty();
		this.currentErrorCoreReason = Optional.empty();
		this.currentException = Optional.empty();
	}

	/**
	 * Create the output for Query Requests 
	 */
	public ObjectNode getStatusJson(ObjectMapper objectMapper) {

		ObjectNode objectNode = objectMapper.createObjectNode();

		if (online) {
			objectNode.put("online", online);

			if (currentErrorCode.isPresent()) {
				objectNode.put("errorCode", currentException.get().key());
				objectNode.put("status", QueryStatus.ERROR.name());
			} else if (currentException.isPresent()) {
				objectNode.put("exceptionCode", currentException.get().key());
				objectNode.put("status", QueryStatus.EXCEPTIONS.name());
			} else {
				objectNode.put("status", QueryStatus.SUCCESS.name());
			}

			traits.forEach(t -> JsonUtil.appendMapToObjectNode(t.getStatus(objectMapper), objectMapper, objectNode));

		} else {
			objectNode.put("errorCode", ErrorCodeEnum.DEVICE_OFFLINE.key());
			objectNode.put("status", QueryStatus.OFFLINE.name());
		}

		return objectNode;
	}

	public UserDevices getCasa() {
		return casa;
	}

	public void setCasa(UserDevices casa) {
		this.casa = casa;
	}

	public String getId() {
		return id;
	}

	public DeviceName getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	/**
	 * Add a Trait to device.
	 * Generally you can use methos withXXX on Device Object, but for many Device you can attach any trait.
	 * @param trait to add to the device
	 */
	public void addTraits(Trait trait) {
		this.traits.add(trait);
	}

	public Set<Trait> getTraits() {
		return Set.copyOf(traits);
	}

	public void setWillReportState(boolean willReportState) {
		this.willReportState = willReportState;
	}

	public boolean isWillReportState() {
		return willReportState;
	}

	public void setRoomHint(String roomHint) {
		this.roomHint = roomHint;
	}

	public String getRoomHint() {
		return roomHint;
	}

	public DeviceInfoDto getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(DeviceInfoDto deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public Set<OtherDeviceId> getOtherDeviceIds() {
		return Set.copyOf(otherDeviceIds);
	}

	public Map<String, CustomDataValue> getCustomData() {
		return Map.copyOf(customData);
	}

	public void addCustomData(String key, CustomDataValue value) {
		this.customData.put(key, value);
	}

	public void remCustomData(String key) {
		this.customData.remove(key);
	}

	public Optional<Trait> getTraitByName(String traitId) {
		return traits.stream().filter(t -> t.getTraitId().equals(traitId)).findFirst();
	}

	public Optional<Trait> getTraitByCommand(String command) {
		return traits.stream().filter(t -> t.hasCommand(command)).findFirst();
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public boolean isOnline() {
		return online;
	}

	public Map<String, Object> getStatus() {
		return Map.of("online", online);
	}

	public Optional<Boolean> getNotificationSupportedByAgent() {
		return notificationSupportedByAgent;
	}

	public void setNotificationSupportedByAgent(Optional<Boolean> notificationSupportedByAgent) {
		this.notificationSupportedByAgent = notificationSupportedByAgent;
	}

	public void setCurrentErrorCode(Optional<ErrorCodeEnum> currentErrorCode) {
		this.currentErrorCode = currentErrorCode;
	}

	public void setCurrentErrorCoreReason(Optional<ErrorCodeReasonEnum> currentErrorCoreReason) {
		this.currentErrorCoreReason = currentErrorCoreReason;
	}

	public void setCurrentException(Optional<ExceptionEnum> currentException) {
		this.currentException = currentException;
	}

	@Override
	public String toString() {
		return "Device [id=" + id + ", name=" + name + ", type=" + type + "]";
	}

}
