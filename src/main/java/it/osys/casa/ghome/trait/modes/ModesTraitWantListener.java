package it.osys.casa.ghome.trait.modes;

import java.util.concurrent.CompletableFuture;

import it.osys.casa.ghome.exception.InvalidCommandException;
import it.osys.casa.ghome.listener.Listener;

/**
 * A command from Google Home to be executed on device
 */
public abstract class ModesTraitWantListener  extends Listener {

	/**
	 * Command to be execute
	 * @return a Future that return when the Device confirm the Command
	 */
	public CompletableFuture<Void> onWantMode(String requertId, String name, String value) {
		throw new InvalidCommandException("Not implemented");
	}

}
