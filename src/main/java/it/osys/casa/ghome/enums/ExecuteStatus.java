package it.osys.casa.ghome.enums;

/**
 * https://developers.home.google.com/cloud-to-cloud/intents/query
 */
public enum ExecuteStatus {

	/**
	 * Confirm that the command succeeded.
	 */
	SUCCESS,
	/**
	 * Command is enqueued but expected to succeed.
	 */
	PENDING,
	/**
	 * Target device is in offline state or unreachable.
	 */
	OFFLINE,
	/**
	 * There is an issue or alert associated with a command. The command could succeed or fail. This status type is typically set when you want to send additional information about another connected device.
	 */
	EXCEPTIONS,
	/**
	 * Target device is unable to perform the command.
	 */
	ERROR;

}
