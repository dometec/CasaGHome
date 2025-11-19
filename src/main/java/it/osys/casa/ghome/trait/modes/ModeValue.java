package it.osys.casa.ghome.trait.modes;

import java.util.List;
import java.util.Map;

public class ModeValue {

	public String name;

	// Lang (ISO 639-1) ->List of names
	public Map<String, List<String>> values;

	public ModeValue(String name, Map<String, List<String>> values) {
		this.name = name;
		this.values = values;
	}

}
