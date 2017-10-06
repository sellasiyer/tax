package com.sn.services.tax;
import com.sn.webservice.model.tax.*;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import javax.xml.bind.JAXBElement;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;


public class TaxClient extends WebServiceGatewaySupport{
    private static final Logger log = LoggerFactory.getLogger(TaxClient.class);

    public QuotationResponse getTaxQuotation()
    {
        final JAXBElement<QuotationRequest> requestJAXBElement = getTaxQuotationRequest();

        log.info("Sending request");
        Object response=  getWebServiceTemplate().marshalSendAndReceive(requestJAXBElement);
        QuotationResponse quotationResponse = null;

        if(response instanceof  JAXBElement<?>) {
            final Object jaxbObj = ((JAXBElement) response).getValue();
            if (jaxbObj instanceof QuotationResponse) {
                 quotationResponse = (QuotationResponse) jaxbObj;

            }

        }
        return quotationResponse;

    }

    private JAXBElement<QuotationRequest> getTaxQuotationRequest() {

        ObjectFactory objectFactory = new ObjectFactory();
        QuotationRequest quotationRequest = new QuotationRequest();
        quotationRequest.setDocumentDate(XMLGregorianCalendarImpl.createDate(2017,10,4,0));
        quotationRequest.setDocumentNumber(UUID.randomUUID().toString());
        quotationRequest.setTransactionType("SALE");

        BaseSeller baseSeller = objectFactory.createBaseSeller();
        baseSeller.setDivision("200");
        baseSeller.setDepartment("10");

        quotationRequest.setSeller(baseSeller);

        Quantity quantity = objectFactory.createQuantity();
        quantity.setUnitOfMeasure("EA");
        quantity.setValue(BigDecimal.valueOf(Long.valueOf("1")));

        Customer customer = objectFactory.createCustomer();
        customer.setCustomerCode(objectFactory.createCustomerCode());
        customer.getCustomerCode().setValue("GUEST");

        ReqAddress reqAddress = objectFactory.createReqAddress();
        reqAddress.setCity("GUEST");
        reqAddress.setMainDivision("GUEST");
        reqAddress.setPostalCode("55123");

        customer.setDestination(reqAddress);



        for(int i =1; i<4 ;i++) {
            LineItem lineItem = objectFactory.createLineItem();
            lineItem.setLineItemNumber(BigInteger.valueOf(Long.valueOf(i)));
            lineItem.setLineItemId(UUID.randomUUID().toString());
            lineItem.setExtendedPrice(BigDecimal.valueOf(Long.valueOf("199")));
            lineItem.setCustomer(customer);
            lineItem.setQuantity(quantity);

            LineItem.Product product = objectFactory.createLineItemProduct();
            product.setValue("CSI841AN");
            product.setProductClass("TPGG");
            lineItem.setProduct(product);

            FlexibleFields flexibleFields = objectFactory.createFlexibleFields();
            flexibleFields.setShipToCustomerCode("GUEST");
            flexibleFields.setShipToCustomerName("GUEST");
            flexibleFields.setOriginalSellingPrice("249.99");
            flexibleFields.setDiscountAmount("50");
            flexibleFields.setSourceSalesOrderNumber(lineItem.getLineItemNumber().toString());
            flexibleFields.setOrderType("SALE");
            flexibleFields.setLineType("SALE");
            flexibleFields.setSalesOrderDate(quotationRequest.getDocumentDate());
            lineItem.setFlexibleFields(flexibleFields);
            quotationRequest.getLineItem().add(lineItem);
        }




        return new ObjectFactory().createQuotationRequest(quotationRequest);
    }


}
