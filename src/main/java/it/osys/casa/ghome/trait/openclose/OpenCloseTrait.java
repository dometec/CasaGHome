package it.osys.casa.ghome.trait.openclose;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import it.osys.casa.ghome.device.Device;
import it.osys.casa.ghome.exception.InvalidCommandException;
import it.osys.casa.ghome.listener.Listener;
import it.osys.casa.ghome.trait.Trait;

/**
 * https://developers.home.google.com/cloud-to-cloud/traits/onoff
 */
public class OpenCloseTrait extends Trait {

	private static final long serialVersionUID = -6112815100322625897L;

	public static final Logger logger = LoggerFactory.getLogger(OpenCloseTrait.class);

	// Attributes
	private boolean discreteOnlyOpenClose = false;

	private boolean commandOnlyOpenClose = false;

	private boolean queryOnlyOpenClose = false;

	private Set<OpenCloseDirection> openDirections;

	// State
	private int openPercent;

	private Optional<Integer> targetOpenPercent = Optional.empty();

	private Optional<OpenCloseDirection> direction;

	public OpenCloseTrait(Device device, boolean discreteOnlyOpenClose, boolean commandOnlyOpenClose, boolean queryOnlyOpenClose,
			Set<OpenCloseDirection> openDirections) {
		super(device, "action.devices.traits.OpenClose", Set.of("action.devices.commands.OpenClose"));
		this.discreteOnlyOpenClose = discreteOnlyOpenClose;
		this.commandOnlyOpenClose = commandOnlyOpenClose;
		this.queryOnlyOpenClose = queryOnlyOpenClose;
		this.openDirections = openDirections;
	}

	public boolean isCommandOnlyOpenClose() {
		return commandOnlyOpenClose;
	}

	public boolean isDiscreteOnlyOpenClose() {
		return discreteOnlyOpenClose;
	}

	public boolean isQueryOnlyOpenClose() {
		return queryOnlyOpenClose;
	}

	public int getOpenPercent() {
		return openPercent;
	}

	/**
	 * Target position during the movement, empty otherwise. 
	 */
	public Optional<Integer> getTargetOpenPercent() {
		return targetOpenPercent;
	}

	public Optional<OpenCloseDirection> getDirection() {
		return direction;
	}

	@Override
	public void addAttributes(ObjectMapper objectMapper, ObjectNode objectNode) {
		objectNode.put("discreteOnlyOpenClose", discreteOnlyOpenClose);
		objectNode.put("commandOnlyOpenClose", commandOnlyOpenClose);
		objectNode.put("queryOnlyOpenClose", queryOnlyOpenClose);
		ArrayNode putArray = objectNode.putArray("openDirection");
		openDirections.forEach(o -> putArray.add(o.name()));
	}

	@Override
	public Map<String, Object> getStatus(ObjectMapper objectMapper) {
		Map<String, Object> out = new HashMap<>();
		out.put("openPercent", openPercent);
		targetOpenPercent.ifPresent(t -> out.put("targetOpenPercent", t));
		return out;
	}

	public void setOpenPercent(int openPercent) {
		this.openPercent = openPercent;
	}

	public void setTargetOpenPercent(Optional<Integer> targetOpenPercent) {
		this.targetOpenPercent = targetOpenPercent;
	}

	public void setTargetOpenPercent(Optional<Integer> targetOpenPercent, OpenCloseDirection direction) {
		this.direction = Optional.of(direction);
		this.targetOpenPercent = targetOpenPercent;
	}

	public void movementComplete() {
		this.direction = Optional.empty();
		this.targetOpenPercent = Optional.empty();
	}

	@Override
	public CompletableFuture<Void> executeCommand(Listener listener, String requestId, String command, JsonNode params) {
		if (!"action.devices.commands.OpenClose".equals(command))
			throw new InvalidCommandException("Unknow command " + command + " in trait " + this.getTraitId());

		Optional<String> followUpToken = params.has("followUpToken") ? Optional.of(params.get("followUpToken").asText()) : Optional.empty();
		Optional<String> openDirection = params.has("openDirection") ? Optional.of(params.get("openDirection").asText()) : Optional.empty();
		OpenCloseTraitWantListener openCloseTraitWantListener = (OpenCloseTraitWantListener) listener;
		return openCloseTraitWantListener.onWantOpenClose(getDevice(), this, requestId, params.get("openPercent").asInt(), openDirection,
				followUpToken);
	}

	@Override
	public String toString() {
		return "OpenCloseTrait [discreteOnlyOpenClose=" + discreteOnlyOpenClose + ", commandOnlyOpenClose=" + commandOnlyOpenClose
				+ ", queryOnlyOpenClose=" + queryOnlyOpenClose + ", openDirections=" + openDirections + ", openPercent=" + openPercent
				+ ", targetOpenPercent=" + targetOpenPercent + "]";
	}

}
