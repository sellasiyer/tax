package com.sn.services.tax;

import com.sn.webservice.model.tax.QuotationRequest;
import com.sn.webservice.model.tax.QuotationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.bind.JAXBElement;


public class TaxClient extends WebServiceGatewaySupport {
    private static final Logger log = LoggerFactory.getLogger(TaxClient.class);


    public QuotationResponse getTaxQuotation(final JAXBElement<QuotationRequest> requestJAXBElement) {

        log.info("Sending request");
        Object response = getWebServiceTemplate().marshalSendAndReceive(requestJAXBElement);
        QuotationResponse quotationResponse = null;

        if (response instanceof JAXBElement<?>) {
            final Object jaxbObj = ((JAXBElement) response).getValue();
            if (jaxbObj instanceof QuotationResponse) {
                quotationResponse = (QuotationResponse) jaxbObj;

            }

        }
        return quotationResponse;

    }


}
