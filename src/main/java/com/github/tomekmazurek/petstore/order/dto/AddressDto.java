package com.github.tomekmazurek.petstore.order.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDto {

    private Long id;
    private String name;
    private String surname;
    private String city;
    private String postalCode;
    private String street;
    private String streetNumber;
    private String phoneNumber;

    @JsonCreator
    public AddressDto(
            @JsonProperty("id") Long id,
            @JsonProperty("name") String name,
            @JsonProperty("surname") String surname,
            @JsonProperty("city") String city,
            @JsonProperty("postalCode") String postalCode,
            @JsonProperty("street") String street,
            @JsonProperty("streetNumber") String streetNumber,
            @JsonProperty("phoneNumber") String phoneNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
        this.streetNumber = streetNumber;
        this.phoneNumber = phoneNumber;
    }
}
