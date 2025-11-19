module it.osys.casa.ghome {
	requires transitive org.slf4j;
	requires transitive com.fasterxml.jackson.databind;
	requires transitive com.google.api.client;
	requires transitive google.api.client;
	requires transitive com.google.api.services.homegraph;

	exports it.osys.casa.ghome;
	exports it.osys.casa.ghome.listener;
	exports it.osys.casa.ghome.handler;
	exports it.osys.casa.ghome.enums;
	exports it.osys.casa.ghome.converter;
	exports it.osys.casa.ghome.dto;
	exports it.osys.casa.ghome.device;
	exports it.osys.casa.ghome.trait;
	exports it.osys.casa.ghome.trait.brightness;
	exports it.osys.casa.ghome.trait.colorsetting;
	exports it.osys.casa.ghome.trait.lockunlock;
	exports it.osys.casa.ghome.trait.modes;
	exports it.osys.casa.ghome.trait.onoff;
	exports it.osys.casa.ghome.trait.openclose;
	exports it.osys.casa.ghome.trait.sensorstate;
}