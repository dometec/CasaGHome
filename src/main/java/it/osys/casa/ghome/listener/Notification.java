package it.osys.casa.ghome.listener;

import java.util.Map;

public abstract class Notification {

	/**
	 * Only 0 is currently supported by Google Home, which indicates that the notification should be spoken aloud.
	 */
	public static final int NOFITICATION_PRIORITY = 0;

	public abstract Map<String, Object> getNotification();

}
