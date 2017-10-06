package com.sn.services.tax.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 *  Address data object holds the necessary information for tax pru
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder(
        {"city", "state", "postalCode", "country"})
public class AddressData {

    @JsonProperty("city")
    private String city;
    @JsonProperty("state")
    private String state;
    @JsonProperty("postalCode")
    private String postalCode;
    @JsonProperty("country")
    private String country;


    public AddressData(String city, String state, String postalCode, String country) {
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
    }

    public AddressData(String city, String state, String postalCode) {
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }
}