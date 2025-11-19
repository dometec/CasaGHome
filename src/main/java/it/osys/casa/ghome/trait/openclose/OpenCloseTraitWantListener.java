package it.osys.casa.ghome.trait.openclose;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import it.osys.casa.ghome.device.Device;
import it.osys.casa.ghome.exception.InvalidCommandException;
import it.osys.casa.ghome.listener.Listener;

/**
 * A command from Google Home to be executed on device
 */
public abstract class OpenCloseTraitWantListener extends Listener {

	/**
	 * Command to be execute
	 * @return a Future that return when the Device confirm the Command
	 */
	public CompletableFuture<Void> onWantOpenClose(Device device, OpenCloseTrait trait, String requestId, int openPercent,
			Optional<String> openDirection, Optional<String> followUpToken) {
		throw new InvalidCommandException("Not implemented");
	}

}
