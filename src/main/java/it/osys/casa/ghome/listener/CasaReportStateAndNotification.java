package it.osys.casa.ghome.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpResponse;
import com.google.api.services.homegraph.v1.HomeGraphService;
import com.google.api.services.homegraph.v1.HomeGraphService.Devices.ReportStateAndNotification;
import com.google.api.services.homegraph.v1.HomeGraphService.Devices.RequestSync;
import com.google.api.services.homegraph.v1.model.ReportStateAndNotificationDevice;
import com.google.api.services.homegraph.v1.model.ReportStateAndNotificationRequest;
import com.google.api.services.homegraph.v1.model.RequestSyncDevicesRequest;
import com.google.api.services.homegraph.v1.model.StateAndNotificationPayload;

import it.osys.casa.ghome.device.Device;
import it.osys.casa.ghome.trait.Trait;

/**
 * Sens status report to Google Home, with debounce.
 * Max 1 call/sec for the same Device and Trait(s).
 */
public class CasaReportStateAndNotification {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final ConcurrentHashMap<String, Future<?>> requestsQueue = new ConcurrentHashMap<>();

	private HomeGraphService homegraphService;

	private ScheduledExecutorService scheduledExecutor;

	private int msDebounce;

	private ObjectMapper objectMapper;

	/**
	 * Create a default Report State And Notification client with a debounce algorithm
	 * @param scheduledExecutor 
	 * @param msDebounce time to wait for other call, in milliseconds
	 */
	public CasaReportStateAndNotification(HomeGraphService homegraphService, ObjectMapper objectMapper,
			ScheduledExecutorService scheduledExecutor, int msDebounce) {
		this.homegraphService = homegraphService;
		this.objectMapper = objectMapper;
		this.scheduledExecutor = scheduledExecutor;
		this.msDebounce = msDebounce;
	}

	/**
	 * Send the online/offline device status.
	 * @param device The Device
	 */
	public void sendReportState(Device device) {
		sendReportStateAndNotification(device, Optional.empty(), Optional.empty());
	}

	/**
	 * Send the online/offline device status.
	 * It send also the status of this trait
	 * @param device The Device
	 * @param trait The Trait
	 */
	public void sendReportState(Device device, Trait trait) {
		sendReportStateAndNotification(device, Optional.of(trait), Optional.empty());
	}

	/**
	 * Send the online/offline device status.
	 * It send also the status of this trait and an Optional Notificatio.
	 * @param device The Device
	 * @param trait The Trait
	 */
	public void sendReportStateAndNotification(Device device, Trait trait, Notification notification) {
		sendReportStateAndNotification(device, Optional.of(trait), Optional.of(notification));
	}

	/**
	 * Send the online/offline device status.
	 * If there is a trait, send also the status of this trait
	 * @param device The Device
	 * @param trait The optional Trait
	 */
	public void sendReportStateAndNotification(Device device, Optional<Trait> trait, Optional<Notification> notification) {

		String key = device.getId() + "_" + (trait.isPresent() ? trait.get().getTraitId() : "");

		requestsQueue.compute(key, (_, existingFuture) -> {
			if (existingFuture != null)
				existingFuture.cancel(false);
			return scheduledExecutor.schedule(() -> _sendReportStateAndNotification(device, trait, notification), msDebounce,
					TimeUnit.MILLISECONDS);
		});

	}

	private void _sendReportStateAndNotification(Device device, Optional<Trait> trait, Optional<Notification> notification) {

		logger.info("Send Status Report {}: {}.", device, trait);

		String agentUserId = device.getCasa().getAgentUserId();

		try {

			ReportStateAndNotificationDevice report = new ReportStateAndNotificationDevice();

			// Create status data
			Map<String, Object> status = new HashMap<>();
			trait.ifPresent(t -> status.putAll(t.getStatus(objectMapper)));
			status.putAll(device.getStatus());
			report.setStates(Map.of(device.getId(), status));

			// Optional Notification Data
			notification.ifPresent(n -> {
				report.setNotifications(Map.of(device.getId(), n.getNotification()));
			});

			ReportStateAndNotificationRequest request = new ReportStateAndNotificationRequest().setRequestId(UUID.randomUUID().toString())
					.setAgentUserId(agentUserId).setPayload(new StateAndNotificationPayload().setDevices(report));

			if (notification.isPresent())
				request.setEventId(UUID.randomUUID().toString());

			ReportStateAndNotification reportStateAndNotification = homegraphService.devices().reportStateAndNotification(request);

			var httpRequest = reportStateAndNotification.buildHttpRequest();
			HttpResponse httpResponse = httpRequest.execute();

			logger.debug("Report State Response: {}.", httpResponse.getStatusCode());

		} catch (GoogleJsonResponseException e) {

			if (e.getStatusCode() == 404) {
				logger.info("Device not exist on google home graph, require SYNC.");

				try {

					RequestSyncDevicesRequest requestSDR = new RequestSyncDevicesRequest().setAgentUserId(agentUserId).setAsync(false);
					RequestSync requestSync = homegraphService.devices().requestSync(requestSDR);
					HttpResponse response = requestSync.executeUnparsed();
					logger.info("RequestSyncDevices status code: {}.", response.getStatusCode());

				} catch (Exception e1) {
					logger.error("Error recall SYNC.", e1);
				}

			} else {
				logger.error("Error sending ReportState.", e);
			}

		} catch (Exception e) {
			logger.error("Error sending ReportState.", e);
		}

	}

	// private int notificationPriority;

	//
	// /**
	// * Send a notification about openPercent trend
	// */
	// public void sendSuccessNotification() {
	// this.currentErrorCode = Optional.empty();
	// sendNotification(this.currentErrorCode);
	// }
	//
	// /**
	// * Send a Failure notification so user know the operation got an error
	// *
	// https://developers.home.google.com/cloud-to-cloud/intents/errors-exceptions#error_list
	// * @param errorCode
	// */
	// public void sendFailureNotification(ErrorCodeEnum errorCode) {
	// this.currentErrorCode = Optional.of(errorCode);
	// sendNotification(this.currentErrorCode);
	// }
	//
	// private void sendNotification(Optional<ErrorCodeEnum> errorCode) {
	//
	// if (followUpToken.isEmpty()) {
	// logger.warn("Can't Send Notification, empty follow up token: {} / {}.",
	// device, this);
	// return;
	// }
	//
	// logger.info("Send Notification {} / {} / {}.", device, followUpToken,
	// errorCode);
	//
	// Map<String, Object> notification = new HashMap<>();
	// notification.put("priority", notificationPriority);
	// notification.put("followUpToken", followUpToken.get());
	//
	// if (errorCode.isEmpty()) {
	// notification.put("openPercent", openPercent);
	// notification.put("status", "SUCCESS");
	// } else {
	// notification.put("errorCode", errorCode.get().key());
	// notification.put("status", "FAILURE");
	// }
	//
	// Map<String, Object> openCloseNotification = new HashMap<>();
	// openCloseNotification.put("OpenClose", notification);
	// ReportStateAndNotificationDevice report = new
	// ReportStateAndNotificationDevice();
	// report.setNotifications(Map.of(device.getId(), openCloseNotification));
	//
	// String agentUserId = device.getCasa().getAgentUserId();
	// HomeGraphService homegraphService = CasaSetup.getHomegraphService();
	//
	// ReportStateAndNotificationRequest request = new
	// ReportStateAndNotificationRequest().setRequestId(UUID.randomUUID().toString())
	// .setAgentUserId(agentUserId).setPayload(new
	// StateAndNotificationPayload().setDevices(report));
	//
	// try {
	//
	// ReportStateAndNotification reportStateAndNotification =
	// homegraphService.devices().reportStateAndNotification(request);
	//
	// var httpRequest = reportStateAndNotification.buildHttpRequest();
	// HttpResponse httpResponse = httpRequest.execute();
	//
	// logger.debug("Notification Response: {}.", httpResponse.getStatusCode());
	//
	// } catch (Exception e) {
	// logger.error("Error sending Notification.", e);
	// }
	//
	// }
	//
	// public void sendEvent(int priority, ErrorCodeEnum errorCode) {
	//
	// logger.info("Send Event {} / {}.", priority, errorCode);
	//
	// Map<String, Object> notification = new HashMap<>();
	// notification.put("priority", notificationPriority);
	// notification.put("errorCode", errorCode.name());
	// notification.put("status", "FAILURE");
	//
	// Map<String, Object> openCloseEvent = new HashMap<>();
	// openCloseEvent.put("OpenClose", notification);
	// ReportStateAndNotificationDevice report = new
	// ReportStateAndNotificationDevice();
	//
	// report.setNotifications(Map.of(device.getId(), openCloseEvent));
	//
	// String agentUserId = device.getCasa().getAgentUserId();
	// HomeGraphService homegraphService = CasaSetup.getHomegraphService();
	//
	// ReportStateAndNotificationRequest request = new
	// ReportStateAndNotificationRequest().setRequestId(UUID.randomUUID().toString())
	// .setAgentUserId(agentUserId).setEventId(UUID.randomUUID().toString())
	// .setPayload(new StateAndNotificationPayload().setDevices(report));
	//
	// try {
	// ReportStateAndNotification reportStateAndNotification =
	// homegraphService.devices().reportStateAndNotification(request);
	//
	// var httpRequest = reportStateAndNotification.buildHttpRequest();
	// HttpResponse httpResponse = httpRequest.execute();
	//
	// logger.debug("Event Response: {}.", httpResponse.getStatusCode());
	//
	// } catch (Exception e) {
	// logger.error("Error sending Event.", e);
	// }
	//
	// }
	//
	// public void sendReportStateAndNotification(SensorItem sensorItem) {
	//
	// logger.info("Send Notification {} / {} / {}.", device, sensorItem);
	//
	// Map<String, Object> notifAttribute = new HashMap<>();
	// notifAttribute.put("priority", notificationPriority);
	// notifAttribute.put("name", sensorItem.name.getDisplayName());
	// notifAttribute.put("currentSensorState",
	// sensorItem.getCurrentSensorState().getDisplayName());
	// // notifAttribute.put("alarmState", sensorItem.getAlarmState().name());
	// // notifAttribute.put("alarmSilenceState",
	// // sensorItem.getAlarmSilenceState().name());
	//
	// // if (sensorItem.getRawValue() != null)
	// // notifAttribute.put("rawValue", sensorItem.getRawValue());
	//
	// Map<String, Object> notification = new HashMap<>();
	// notification.put("SensorState", notifAttribute);
	//
	// Map<String, Object> status = new HashMap<>();
	// status.putAll(this.getStatus());
	// status.putAll(device.getStatus());
	//
	// ReportStateAndNotificationDevice report = new
	// ReportStateAndNotificationDevice();
	// report.setStates(Map.of(device.getId(), status));
	// report.setNotifications(Map.of(device.getId(), notification));
	//
	// String agentUserId = device.getCasa().getAgentUserId();
	// HomeGraphService homegraphService = CasaSetup.getHomegraphService();
	//
	// String requestId = UUID.randomUUID().toString();
	// String eventId = UUID.randomUUID().toString();
	// StateAndNotificationPayload setDevices = new
	// StateAndNotificationPayload().setDevices(report);
	//
	// ReportStateAndNotificationRequest request = new
	// ReportStateAndNotificationRequest().setRequestId(requestId).setEventId(eventId)
	// .setAgentUserId(agentUserId).setPayload(setDevices);
	//
	// try {
	//
	// ReportStateAndNotification reportStateAndNotification =
	// homegraphService.devices().reportStateAndNotification(request);
	//
	// var httpRequest = reportStateAndNotification.buildHttpRequest();
	// HttpResponse httpResponse = httpRequest.execute();
	//
	// logger.debug("Notification Response: {}.", httpResponse.getStatusCode());
	//
	// } catch (Exception e) {
	// logger.error("Error sending Notification.", e);
	// }
	//
	// }

}
