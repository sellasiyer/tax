package com.sn.services.tax;

import com.sn.services.tax.data.request.TaxData;
import com.sn.services.tax.data.response.TaxResponseData;
import com.sn.webservice.model.tax.ObjectFactory;
import com.sn.webservice.model.tax.QuotationRequest;
import com.sn.webservice.model.tax.QuotationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.GenericConversionService;

import javax.xml.bind.JAXBElement;

/**
 * Created by sellasiyer on 10/6/17.
 */
public class TaxService {

    @Autowired
    private GenericConversionService conversionService;

    @Autowired
    private TaxClient taxClient;


    public TaxResponseData getTax(TaxData taxData) {

        QuotationResponse quotationResponse = taxClient.getTaxQuotation(getTaxQuotationRequest(taxData));

        return conversionService.convert(quotationResponse, TaxResponseData.class);

    }


    private JAXBElement<QuotationRequest> getTaxQuotationRequest(TaxData taxData) {

        ObjectFactory objectFactory = new ObjectFactory();

        QuotationRequest quotationRequest = conversionService.convert(taxData, QuotationRequest.class);

        return objectFactory.createQuotationRequest(quotationRequest);
    }


}
