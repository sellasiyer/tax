package com.sn.services.tax.data.response;

import java.util.ArrayList;
import java.util.List;

public class TaxResponseData {
    private String status;
    private String totalTax;
    private List<TaxLineItemResponseData> lineItemDataList;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(String totalTax) {
        this.totalTax = totalTax;
    }

    public List<TaxLineItemResponseData> getLineItemDataList() {
        if(lineItemDataList != null)
            lineItemDataList = new ArrayList<>();

        return lineItemDataList;
    }


}
