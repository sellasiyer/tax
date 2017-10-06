package com.sn.services.tax.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.UUID;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder(
        {"lineItemNumber", "productCode", "productClass", "quantity", "sellingPrice" })
public class TaxLineItemData {


    private String id;
    @JsonProperty("lineItemNumber")
    private String number;
    @JsonProperty("productCode")
    private String productCode;
    @JsonProperty("productClass")
    private String productClass;
    @JsonProperty("quantity")
    private String quantity;
    @JsonProperty("sellingPrice")
    private String sellingPrice;

    private String unitOfMeasure="EA";
    private String orderType = "SALE";
    private String lineType = "SALE";

    public TaxLineItemData(String number, String productCode, String quantity, String sellingPrice, String productClass) {
        this();
        this.setNumber(number);
        this.setProductCode(productCode);
        this.setQuantity(quantity);
        this.setSellingPrice(sellingPrice);
        this.setProductClass(productClass);
    }

    public TaxLineItemData() {
        this.setId(UUID.randomUUID().toString());
    }

    public String getProductClass() {
        return productClass;
    }

    public void setProductClass(String productClass) {
        this.productClass = productClass;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }


    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }
}
