package it.osys.casa.ghome.trait.colorsetting;

import java.util.concurrent.CompletableFuture;

import it.osys.casa.ghome.exception.InvalidCommandException;
import it.osys.casa.ghome.listener.Listener;

public abstract class ColorSettingTraitWantListener extends Listener {

	/**
	 * Command to be execute
	 * @return a Future that return when the Device confirm the Command
	 */
	public CompletableFuture<Void> onWantTemperature(String requertId, int temperature) {
		throw new InvalidCommandException("Not implemented");
	}

	public CompletableFuture<Void> onWantSpectrumRGB(String requertId, int rgb) {
		throw new InvalidCommandException("Not implemented");
	}

	public CompletableFuture<Void> onWantSpectrumHSV(String requertId, int hue, double saturation, double value) {
		throw new InvalidCommandException("Not implemented");
	}

}
