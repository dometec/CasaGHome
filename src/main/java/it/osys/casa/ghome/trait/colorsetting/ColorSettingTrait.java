package it.osys.casa.ghome.trait.colorsetting;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import it.osys.casa.ghome.device.Device;
import it.osys.casa.ghome.exception.InvalidCommandException;
import it.osys.casa.ghome.listener.Listener;
import it.osys.casa.ghome.trait.Trait;

/**
 * https://developers.home.google.com/cloud-to-cloud/traits/colorsetting
 */
public class ColorSettingTrait extends Trait {

	private static final long serialVersionUID = 1844505948186242273L;

	// Attributes
	private boolean commandOnlyColorSetting;

	// States
	private Integer temperatureK;

	private Integer spectrumRgb;

	private Integer hue;
	private Float saturation;
	private Float value;

	private Optional<ColorTemperatureRange> colorTemperatureRange;
	private Optional<ColorModelEnum> colorModel;

	/**
	 * Create the ColorSettingTrait
	 * 
	 * https://developers.home.google.com/cloud-to-cloud/traits/colorsetting
	 * 
	 * @param device The Device
	 * @param commandOnlyColorSetting default false 
	 * @param colorModel optional
	 * @param colorTemperatureRange optional
	 * @param colorSettingTraitWantListener callback listener
	 */
	public ColorSettingTrait(Device device, boolean commandOnlyColorSetting, Optional<ColorModelEnum> colorModel,
			Optional<ColorTemperatureRange> colorTemperatureRange) {
		super(device, "action.devices.traits.ColorSetting", Set.of("action.devices.commands.ColorAbsolute"));
		this.commandOnlyColorSetting = commandOnlyColorSetting;
		this.colorModel = colorModel;
		this.colorTemperatureRange = colorTemperatureRange;
	}

	public boolean isCommandOnlyColorSetting() {
		return commandOnlyColorSetting;
	}

	public void setCommandOnlyColorSetting(boolean commandOnlyColorSetting) {
		this.commandOnlyColorSetting = commandOnlyColorSetting;
	}

	public Optional<ColorModelEnum> getColorModel() {
		return colorModel;
	}

	public Optional<ColorTemperatureRange> getColorTemperatureRange() {
		return colorTemperatureRange;
	}

	@Override
	public void addAttributes(ObjectMapper objectMapper, ObjectNode objectNode) {

		objectNode.put("commandOnlyColorSetting", commandOnlyColorSetting);

		colorModel.ifPresent(cm -> objectNode.put("colorModel", cm.name()));

		colorTemperatureRange.ifPresent(ctr -> {
			ObjectNode octr = objectMapper.createObjectNode();
			octr.put("temperatureMinK", ctr.getTemperatureMinK());
			octr.put("temperatureMaxK", ctr.getTemperatureMaxK());
			objectNode.set("colorTemperatureRange", octr);
		});
	}

	@Override
	public Map<String, Object> getStatus(ObjectMapper objectMapper) {

		Map<String, Object> out = new HashMap<>();

		colorModel.ifPresent(cm -> {
			switch (cm) {
			case hsv -> {
				ObjectNode spectrumHsv = objectMapper.createObjectNode();
				spectrumHsv.put("hue", hue);
				spectrumHsv.put("saturation", saturation);
				spectrumHsv.put("value", value);
				out.put("spectrumHsv", spectrumHsv);
			}
			case rgb -> {
				out.put("spectrumRgb", spectrumRgb);
			}
			}
		});

		colorTemperatureRange.ifPresent(_ -> {
			if (temperatureK != null)
				out.put("temperatureK", temperatureK);
		});

		return Map.of("color", out);
	}

	@Override
	public CompletableFuture<Void> executeCommand(Listener listener, String requestId, String command, JsonNode params) {

		if (!"action.devices.commands.ColorAbsolute".equals(command))
			throw new InvalidCommandException("Unknow command " + command + " in trait " + this.getTraitId());

		ColorSettingTraitWantListener colorSettingTraitWantListener = (ColorSettingTraitWantListener) listener;

		JsonNode color = params.get("color");

		if (color.has("temperature"))
			return colorSettingTraitWantListener.onWantTemperature(requestId, color.get("temperature").asInt());

		if (color.has("spectrumRGB"))
			return colorSettingTraitWantListener.onWantSpectrumRGB(requestId, color.get("spectrumRGB").asInt());

		if (color.has("spectrumHSV")) {
			JsonNode specHSV = color.get("spectrumHSV");
			return colorSettingTraitWantListener.onWantSpectrumHSV(requestId, specHSV.get("hue").asInt(),
					specHSV.get("saturation").asDouble(), specHSV.get("value").asDouble());
		}

		throw new InvalidCommandException("Unknow command parameter in trait " + this.getTraitId());

	}

	@Override
	public String toString() {
		return "ColorSettingTrait [commandOnlyColorSetting=" + commandOnlyColorSetting + ", colorModel=" + colorModel
				+ ", colorTemperatureRange=" + colorTemperatureRange + ", temperatureK=" + temperatureK + ", spectrumRgb=" + spectrumRgb
				+ ", hue=" + hue + ", saturation=" + saturation + ", value=" + value + "]";
	}

	public void setColor(int spectrumRgb) {
		this.spectrumRgb = spectrumRgb;
		if (temperatureK != null)
			temperatureK = rgbToColorTemperature(spectrumRgb);
	}

	public void setColor(int hue, float saturation, float value) {
		// TODO to implemt
		throw new InvalidCommandException("Not implemented");
	}

	public static int rgbToColorTemperature(int spectrumRgb) {

		int r = (spectrumRgb >> 16) & 0xFF;
		int g = (spectrumRgb >> 8) & 0xFF;
		int b = spectrumRgb & 0xFF;

		// Normalizza i valori RGB tra 0 e 1
		double red = r / 255.0;
		double green = g / 255.0;
		double blue = b / 255.0;

		// Converti RGB in spazio cromatico XYZ (standard illuminant D65)
		double X = red * 0.4124 + green * 0.3576 + blue * 0.1805;
		double Y = red * 0.2126 + green * 0.7152 + blue * 0.0722;
		double Z = red * 0.0193 + green * 0.1192 + blue * 0.9505;

		// Converti XYZ in coordinate cromatiche xy
		double sum = X + Y + Z;
		if (sum == 0)
			return 0;
		double x = X / sum;
		double y = Y / sum;

		// Conversione xy → temperatura approssimata (formula di McCamy, 1992)
		double n = (x - 0.3320) / (0.1858 - y);
		double CCT = 449 * Math.pow(n, 3) + 3525 * Math.pow(n, 2) + 6823.3 * n + 5520.33;

		return (int) CCT; // Kelvin
	}

	public static int colorTemperatureToRGB(double kelvin) {
		double temp = kelvin / 100.0;

		double red, green, blue;

		// Calcolo del rosso
		if (temp <= 66) {
			red = 255;
		} else {
			red = 329.698727446 * Math.pow(temp - 60, -0.1332047592);
			red = clamp(red, 0, 255);
		}

		// Calcolo del verde
		if (temp <= 66) {
			green = 99.4708025861 * Math.log(temp) - 161.1195681661;
		} else {
			green = 288.1221695283 * Math.pow(temp - 60, -0.0755148492);
		}
		green = clamp(green, 0, 255);

		// Calcolo del blu
		if (temp >= 66) {
			blue = 255;
		} else if (temp <= 19) {
			blue = 0;
		} else {
			blue = 138.5177312231 * Math.log(temp - 10) - 305.0447927307;
			blue = clamp(blue, 0, 255);
		}

		// Converte nei canali RGB
		int r = (int) Math.round(red);
		int g = (int) Math.round(green);
		int b = (int) Math.round(blue);

		return (r << 16) | (g << 8) | b;
	}

	private static double clamp(double value, double min, double max) {
		return Math.max(min, Math.min(max, value));
	}

}
