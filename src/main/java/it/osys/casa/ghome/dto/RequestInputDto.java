package it.osys.casa.ghome.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.node.ObjectNode;

import it.osys.casa.ghome.enums.IntentEnum;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestInputDto(IntentEnum intent, ObjectNode payload) {
}
