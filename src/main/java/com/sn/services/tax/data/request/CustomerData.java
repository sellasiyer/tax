package com.sn.services.tax.data.request;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder(
        {"lineItemNumber", "productCode", "productClass", "quantity", "sellingPrice"})
public class CustomerData {
    private String name;
    private String code;

}
