package it.osys.casa.ghome.trait.colorsetting;

public class ColorTemperatureRange {

	private Integer temperatureMinK;

	private Integer temperatureMaxK;

	public ColorTemperatureRange(Integer temperatureMinK, Integer temperatureMaxK) {
		super();
		this.temperatureMinK = temperatureMinK;
		this.temperatureMaxK = temperatureMaxK;
	}

	public Integer getTemperatureMinK() {
		return temperatureMinK;
	}

	public void setTemperatureMinK(Integer temperatureMinK) {
		this.temperatureMinK = temperatureMinK;
	}

	public Integer getTemperatureMaxK() {
		return temperatureMaxK;
	}

	public void setTemperatureMaxK(Integer temperatureMaxK) {
		this.temperatureMaxK = temperatureMaxK;
	}

}
