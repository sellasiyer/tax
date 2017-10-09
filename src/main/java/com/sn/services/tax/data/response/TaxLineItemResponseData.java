package com.sn.services.tax.data.response;

/**
 *
 */
public class TaxLineItemResponseData {
    private String id;
    private String number;
    private String lineItemTax;

    /**
     *
     * @param id
     * @param number
     * @param lineItemTax
     */
    public TaxLineItemResponseData(String id, String number, String lineItemTax) {
        this.id = id;
        this.number = number;
        this.lineItemTax = lineItemTax;
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

    public String getLineItemTax() {
        return lineItemTax;
    }

    public void setLineItemTax(String lineItemTax) {
        this.lineItemTax = lineItemTax;
    }
}
