package it.osys.casa.ghome.trait.sensorstate;

/**
 * https://developers.home.google.com/cloud-to-cloud/traits/sensorstate#supported-sensors
 */
public enum DescriptiveCapability {

	NEW("new"),

	GOOD("good"),

	REPLACE_SOON("replace soon"),

	REPLACE_NOW("replace now"),

	UNKNOWN("unknown"),

	RAIN_DETECTED("rain detected"), NO_RAIN_DETECTED("no rain detected"),

	LEAK("leak"), NO_LEAK("no leak"),

	CLEAN("clean"), DIRTY("dirty"),

	NEEDS_REPLACEMENT("needs replacement"),

	SMOKE_DETECTED("smoke detected"), NO_SMOKE_DETECTED("no smoke detected"),

	HIGH("high"),

	CARBON_MONOXIDE_DETECTED("carbon monoxide detected"), NO_CARBON_MONOXIDE_DETECTED("no carbon monoxide detected"),

	HEALTHY("healthy"), UNHEALTHY("unhealthy"),

	MODERATE("moderate"),

	UNHEALTHY_FOR_SENSITIVE_GROUPS("unhealthy for sensitive groups"),

	VERY_UNHEALTHY("very unhealthy"),

	HAZARDOUS("hazardous"),

	FAIR("fair"),

	POOR("poor"),

	VERY_POOR("very poor"),

	SEVERE("severe");

	private final String displayName;

	DescriptiveCapability(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Trova un SensorStatus dal suo displayName
	 * @param displayName il nome da cercare
	 * @return il SensorStatus corrispondente
	 * @throws IllegalArgumentException se non viene trovato
	 */
	public static DescriptiveCapability fromDisplayName(String displayName) {
		for (DescriptiveCapability status : values()) {
			if (status.displayName.equalsIgnoreCase(displayName)) {
				return status;
			}
		}
		throw new IllegalArgumentException("Nessun SensorStatus trovato per: " + displayName);
	}

}
