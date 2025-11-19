package it.osys.casa.ghome.device;

import java.util.Set;

import it.osys.casa.ghome.dto.DeviceName;
import it.osys.casa.ghome.trait.openclose.OpenCloseDirection;
import it.osys.casa.ghome.trait.openclose.OpenCloseTrait;

/**
 * https://developers.home.google.com/cloud-to-cloud/guides/garage
 */
public class Garage extends Device {

	private static final long serialVersionUID = -1101986102225802156L;

	// Mandatory
	private OpenCloseTrait openCloseTrait;

	// Optional TODO
	// action.devices.traits.LockUnlock

	public Garage(String id, DeviceName name) {
		super(id, name, "action.devices.types.GARAGE");
	}

	// Mandatory
	public Garage withOpenCloseTrait(boolean discreteOnlyOpenClose, boolean commandOnlyOpenClose, boolean queryOnlyOpenClose,
			Set<OpenCloseDirection> openDirections) {
		openCloseTrait = new OpenCloseTrait(this, discreteOnlyOpenClose, commandOnlyOpenClose, queryOnlyOpenClose, openDirections);
		addTraits(openCloseTrait);
		return this;
	}

	public OpenCloseTrait getOpenCloseTrait() {
		return openCloseTrait;
	}

}
