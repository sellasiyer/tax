package com.sn.services.tax.converter;

import com.sn.services.tax.data.response.TaxLineItemResponseData;
import com.sn.services.tax.data.response.TaxResponseData;
import com.sn.webservice.model.tax.QuotationResponse;
import org.springframework.core.convert.converter.Converter;

/**
 * Convert the Fusion web service response into Tax response data.
 *
 */
public class TaxQuotationResponseConverter implements Converter<QuotationResponse, TaxResponseData> {

    @Override
    public TaxResponseData convert(QuotationResponse quotationResponse) {

        TaxResponseData responseData = new TaxResponseData();
        responseData.setStatus(quotationResponse.getResults());
        responseData.setTotalTax(quotationResponse.getTotalTax().toString());

        quotationResponse.getLineItem().forEach(lineItemResponse -> {
            TaxLineItemResponseData taxLineItemResponseData = new TaxLineItemResponseData(lineItemResponse.getLineItemId(), lineItemResponse.getLineItemNumber().toString(),
                    lineItemResponse.getTotalTax().getValue().toPlainString());

            responseData.getLineItemDataList().add(taxLineItemResponseData);
        });


        return responseData;
    }


}


