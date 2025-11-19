package it.osys.casa.ghome.trait.sensorstate;

/**
 * Indicates the type of sensor
 */
public enum SensorItemName {

	AIR_QUALITY("AirQuality"),

	CARBON_MONOXIDE_LEVEL("CarbonMonoxideLevel"),

	SMOKE_LEVEL("SmokeLevel"),

	FILTER_CLEANLINESS("FilterCleanliness"),

	WATER_LEAK("WaterLeak"),

	RAIN_DETECTION("RainDetection"),

	FILTER_LIFE_TIME("FilterLifeTime"),

	PRE_FILTER_LIFE_TIME("PreFilterLifeTime"),

	HEPA_FILTER_LIFE_TIME("HEPAFilterLifeTime"),

	MAX2_FILTER_LIFE_TIME("Max2FilterLifeTime"),

	CARBON_DIOXIDE_LEVEL("CarbonDioxideLevel"),

	PM2_5("PM2.5"),

	PM10("PM10"),

	VOLATILE_ORGANIC_COMPOUNDS("VolatileOrganicCompounds");

	private final String displayName;

	SensorItemName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Trova un SensorType dal suo displayName
	 * @param displayName il nome da cercare
	 * @return il SensorType corrispondente
	 * @throws IllegalArgumentException se non viene trovato
	 */
	public static SensorItemName fromDisplayName(String displayName) {
		for (SensorItemName type : values()) {
			if (type.displayName.equals(displayName)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Nessun SensorName trovato per: " + displayName);
	}
}
