package it.osys.casa.ghome.trait.lockunlock;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import it.osys.casa.ghome.device.Device;
import it.osys.casa.ghome.exception.InvalidCommandException;
import it.osys.casa.ghome.trait.Trait;

/**
 * https://developers.home.google.com/cloud-to-cloud/traits/onoff
 */
public class LockUnlockTrait extends Trait {

	private static final long serialVersionUID = -4738737990493246055L;

	// State
	private boolean isLocked;

	private boolean isJammed;

	private LockUnlockTraitWantListener lockUnlockTraitWantListener;

	public LockUnlockTrait(Device device, LockUnlockTraitWantListener lockUnlockTraitWantListener) {
		super(device, "action.devices.traits.OnOff", Set.of("action.devices.commands.OnOff"));
		this.lockUnlockTraitWantListener = lockUnlockTraitWantListener;
	}

	@Override
	public void addAttributes(ObjectNode objectNode) {
	}

	@Override
	public Map<String, Object> getStatus() {
		Map<String, Object> out = super.getStatus();
		out.put("isLocked", isLocked);
		out.put("isJammed", isJammed);
		return out;
	}

	@Override
	public CompletableFuture<Void> executeCommand(String requestId, String command, JsonNode params) {
		if (!"action.devices.commands.LockUnlock".equals(command))
			throw new InvalidCommandException("Unknow command " + command + " in trait " + this.getTraitId());

		// TODO to implement

		return null;
	}

	@Override
	public String toString() {
		return "LockUnlockTrait [isLocked=" + isLocked + ", isJammed=" + isJammed + "]";
	}

}
