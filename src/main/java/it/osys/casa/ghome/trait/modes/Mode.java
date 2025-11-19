package it.osys.casa.ghome.trait.modes;

import java.util.List;
import java.util.Map;

public class Mode {

	public String name;

	// Lang (ISO 639-1) ->List of names
	public Map<String, List<String>> nameSynonym;

	public List<ModeValue> settings;

	public boolean ordered;

}
