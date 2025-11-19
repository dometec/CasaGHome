package it.osys.casa.ghome.trait.onoff;

import java.util.concurrent.CompletableFuture;

import it.osys.casa.ghome.exception.InvalidCommandException;

/**
 * A command from Google Home to be executed on device
 */
public abstract class OnOffTraitWantListener {

	/**
	 * Command to be execute
	 * @return a Future that return when the Device confirm the Command
	 */
	public CompletableFuture<Void> onWantOnOff(String requestId, boolean on) {
		throw new InvalidCommandException("Not implemented");
	}

}
