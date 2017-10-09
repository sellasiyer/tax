package com.sn.services.tax.converter;

import com.sn.services.tax.data.request.AddressData;
import com.sn.services.tax.data.request.TaxData;
import com.sn.services.tax.data.request.TaxLineItemData;
import com.sn.webservice.model.tax.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 * Created by sellasiyer on 10/6/17.
 */
public class TaxQuotationRequestConverter implements Converter<TaxData, QuotationRequest> {


    private static final Logger log = LoggerFactory.getLogger(TaxQuotationRequestConverter.class);

    @Override
    public QuotationRequest convert(TaxData taxData) {

        ObjectFactory objectFactory = new ObjectFactory();
        QuotationRequest quotationRequest = new QuotationRequest();
        quotationRequest.setDocumentDate(createXMLGregorianCalendar());
        quotationRequest.setDocumentNumber(UUID.randomUUID().toString());
        quotationRequest.setTransactionType("SALE");

        BaseSeller baseSeller = objectFactory.createBaseSeller();
        baseSeller.setDivision("200");
        baseSeller.setDepartment("10");

        quotationRequest.setSeller(baseSeller);


        Customer customer = objectFactory.createCustomer();
        CustomerCode customerCode = new CustomerCode();
        customerCode.setValue(taxData.getCustomerName());
        customerCode.setClassCode(taxData.getCustomerCode());
        customer.setCustomerCode(customerCode);

        ReqAddress reqAddress = objectFactory.createReqAddress();
        AddressData shippingAddress = taxData.getShippingAddressData();

        reqAddress.setCity(shippingAddress.getCity());
        reqAddress.setMainDivision(shippingAddress.getState());
        reqAddress.setPostalCode(shippingAddress.getPostalCode());

        customer.setDestination(reqAddress);

        for (TaxLineItemData taxLineItemData : taxData.getTaxLineItemDataList()
                ) {


            LineItem lineItem = objectFactory.createLineItem();
            lineItem.setLineItemNumber(BigInteger.valueOf(Long.valueOf(taxLineItemData.getNumber())));
            lineItem.setLineItemId(taxLineItemData.getId());
            lineItem.setExtendedPrice(BigDecimal.valueOf(Long.valueOf(taxLineItemData.getSellingPrice())));
            lineItem.setCustomer(customer);


            Quantity quantity = objectFactory.createQuantity();
            quantity.setUnitOfMeasure("EA");
            quantity.setValue(BigDecimal.valueOf(Long.valueOf(taxLineItemData.getQuantity())));

            lineItem.setQuantity(quantity);

            LineItem.Product product = objectFactory.createLineItemProduct();
            product.setValue(taxLineItemData.getProductCode());
            product.setProductClass(taxLineItemData.getProductClass());
            lineItem.setProduct(product);

            FlexibleFields flexibleFields = objectFactory.createFlexibleFields();
            flexibleFields.setShipToCustomerCode(customer.getCustomerCode().getValue());
            flexibleFields.setShipToCustomerName(customer.getCustomerCode().getValue());
            flexibleFields.setOriginalSellingPrice(taxLineItemData.getOriginalPrice());
            flexibleFields.setDiscountAmount(taxLineItemData.getDiscountPrice());
            flexibleFields.setSourceSalesOrderNumber(lineItem.getLineItemNumber().toString());
            flexibleFields.setOrderType("SALE");
            flexibleFields.setLineType("SALE");
            flexibleFields.setSalesOrderDate(quotationRequest.getDocumentDate());
            lineItem.setFlexibleFields(flexibleFields);
            quotationRequest.getLineItem().add(lineItem);
        }


        return quotationRequest;
    }


    private XMLGregorianCalendar createXMLGregorianCalendar() {
        try {
            final GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(new Date());
            final XMLGregorianCalendar xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
            return xmlDate;

        } catch (final Exception ex) {
            log.error("Failed to createXMLGregorianCalendar():", ex);
        }
        return null;
    }
}
