package it.osys.casa.ghome.trait.brightness;

import java.util.concurrent.CompletableFuture;

import it.osys.casa.ghome.exception.InvalidCommandException;

public abstract class BrightnessTraitWantListener {

	public CompletableFuture<Void> onWantBrightness(String requertId, int brightness) {
		throw new InvalidCommandException("Not implemented");
	}

}
