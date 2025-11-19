package it.osys.casa.ghome.trait.brightness;

import java.util.concurrent.CompletableFuture;

import it.osys.casa.ghome.exception.InvalidCommandException;
import it.osys.casa.ghome.listener.Listener;

public abstract class BrightnessTraitWantListener extends Listener {

	public CompletableFuture<Void> onWantBrightness(String requertId, int brightness) {
		throw new InvalidCommandException("Not implemented");
	}

}
