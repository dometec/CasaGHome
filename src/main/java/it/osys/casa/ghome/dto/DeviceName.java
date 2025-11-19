package it.osys.casa.ghome.dto;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record DeviceName(Set<String> defaultNames, String name, Set<String> nicknames) {

	public DeviceName {
		Objects.requireNonNull(name);
	}

	public DeviceName(String name) {
		this(Collections.emptySet(), name, Collections.emptySet());
	}

	public DeviceName(Set<String> defaultNames, String name) {
		this(defaultNames, name, Collections.emptySet());
	}

}
