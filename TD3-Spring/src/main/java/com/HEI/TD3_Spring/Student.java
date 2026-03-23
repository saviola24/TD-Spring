package com.HEI.TD3_Spring;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Student(
        @JsonProperty("Reference") String reference,
        @JsonProperty("FirstName") String firstName,
        @JsonProperty("LastName") String lastName,
        @JsonProperty("Age") int age
) {}
