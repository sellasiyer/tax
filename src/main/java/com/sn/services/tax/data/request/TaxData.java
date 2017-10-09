package com.sn.services.tax.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

/**
 *
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonRootName("taxRequest")
public class TaxData {


    @JsonProperty("billingAddress")
    private AddressData billingAddressData;
    @JsonProperty("shippingAddress")
    private AddressData shippingAddressData;
    @JsonProperty("lineItems")
    private List<TaxLineItemData> taxLineItemDataList;
    @JsonProperty("customerName")
    private String customerName;
    @JsonProperty("customerCode")
    private String customerCode;

    /**
     *
     * @param customerCode
     * @param customerName
     * @param shippingAddressData
     * @param isbillingAddress
     * @param taxLineItemDataList
     */
    public TaxData(String customerCode, String customerName, AddressData shippingAddressData, boolean isbillingAddress, List<TaxLineItemData> taxLineItemDataList) {
        this.customerCode = customerCode;
        this.customerName = customerName;
        if (isbillingAddress) {
            this.billingAddressData = shippingAddressData;
        }
        this.shippingAddressData = shippingAddressData;
        this.taxLineItemDataList = taxLineItemDataList;
    }

    /**
     *
     */
    public TaxData() {
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public AddressData getBillingAddressData() {
        return billingAddressData;
    }

    public void setBillingAddressData(AddressData billingAddressData) {
        this.billingAddressData = billingAddressData;
    }

    public AddressData getShippingAddressData() {
        return shippingAddressData;
    }

    public void setShippingAddressData(AddressData shippingAddressData) {
        this.shippingAddressData = shippingAddressData;
    }

    public List<TaxLineItemData> getTaxLineItemDataList() {
        return taxLineItemDataList;
    }

    public void setTaxLineItemDataList(List<TaxLineItemData> taxLineItemDataList) {
        this.taxLineItemDataList = taxLineItemDataList;
    }
}
