package it.osys.casa.ghome.device;

import java.util.List;
import java.util.Optional;

import it.osys.casa.ghome.dto.DeviceName;
import it.osys.casa.ghome.trait.brightness.BrightnessTrait;
import it.osys.casa.ghome.trait.colorsetting.ColorModelEnum;
import it.osys.casa.ghome.trait.colorsetting.ColorSettingTrait;
import it.osys.casa.ghome.trait.colorsetting.ColorTemperatureRange;
import it.osys.casa.ghome.trait.modes.Mode;
import it.osys.casa.ghome.trait.modes.ModesTrait;
import it.osys.casa.ghome.trait.onoff.OnOffTrait;

/**
 * https://developers.home.google.com/cloud-to-cloud/guides/light
 */
public class Light extends Device {

	private static final long serialVersionUID = -3997191853694268093L;

	// Mandatory
	private OnOffTrait onOffTrait;

	// Optional
	private ColorSettingTrait colorSettingTrait;

	// Optional
	private BrightnessTrait brightessTrait;

	// Optional
	private ModesTrait modesTrait;

	public Light(String id, DeviceName name) {
		super(id, name, "action.devices.types.LIGHT");
	}

	public Light withOnOffTrait(boolean commandOnlyOnOff, boolean queryOnlyOnOff) {
		onOffTrait = new OnOffTrait(this, commandOnlyOnOff, queryOnlyOnOff);
		addTraits(onOffTrait);
		return this;
	}

	public Light withBrightnessTrait(boolean commandOnlyBrightness) {
		brightessTrait = new BrightnessTrait(this, commandOnlyBrightness);
		addTraits(brightessTrait);
		return this;
	}

	public Light withColorSettingTrait(boolean commandOnlyColorSetting, Optional<ColorModelEnum> colorModel,
			Optional<ColorTemperatureRange> colorTemperatureRange) {
		colorSettingTrait = new ColorSettingTrait(this, commandOnlyColorSetting, colorModel, colorTemperatureRange);
		addTraits(colorSettingTrait);
		return this;
	}

	public Light withModesTrait(boolean commandOnlyColorSetting, boolean queryOnlyOnOff, List<Mode> modes) {
		modesTrait = new ModesTrait(this, commandOnlyColorSetting, queryOnlyOnOff, modes);
		addTraits(modesTrait);
		return this;
	}

	public OnOffTrait getOnOffTrait() {
		return onOffTrait;
	}

	public ColorSettingTrait getColorSettingTrait() {
		return colorSettingTrait;
	}

	public BrightnessTrait getBrightessTrait() {
		return brightessTrait;
	}

	public ModesTrait getModesTrait() {
		return modesTrait;
	}

}
