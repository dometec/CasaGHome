package it.osys.casa.ghome.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestDto(String requestId, List<RequestInputDto> inputs) {
}
