package it.osys.casa.ghome.trait.lockunlock;

import java.util.concurrent.CompletableFuture;

import it.osys.casa.ghome.exception.InvalidCommandException;
import it.osys.casa.ghome.listener.Listener;

/**
 * A command from Google Home to be executed on device
 */
public abstract class LockUnlockTraitWantListener extends Listener {

	/**
	 * Command to be execute
	 * @return a Future that return when the Device confirm the Command
	 */
	public CompletableFuture<Void> onWantOnOff(String requestId, boolean on) {
		throw new InvalidCommandException("Not implemented");
	}

}
