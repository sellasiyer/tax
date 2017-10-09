package com.sn.services.tax;

import com.sn.services.tax.data.request.AddressData;
import com.sn.services.tax.data.request.TaxData;
import com.sn.services.tax.data.request.TaxLineItemData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class TaxApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(TaxApplication.class, args);
        TaxService taxService = ctx.getBean(TaxService.class);

        taxService.getTax(getTaxData());

    }


    private static TaxData getTaxData() {

        TaxData taxData = new TaxData();
        AddressData addressData = new AddressData("GUEST", "GUEST", "55124");

        List<TaxLineItemData> lineItemDataList = new ArrayList<>();

        for (int i = 1; i < 5; i++) {
            TaxLineItemData lineItemData = new TaxLineItemData();
            lineItemData.setDiscountPrice("50");
            lineItemData.setNumber(Integer.toString(i));
            lineItemData.setOriginalPrice("150");
            lineItemData.setProductClass("TPGG");
            lineItemData.setProductCode("QMCT");
            lineItemData.setQuantity("2");
            lineItemData.setSellingPrice("100");
            lineItemDataList.add(lineItemData);
        }

        taxData.setTaxLineItemDataList(lineItemDataList);
        taxData.setShippingAddressData(addressData);
        taxData.setBillingAddressData(addressData);
        //taxData.setCustomerCode();
        taxData.setCustomerName("GUEST");

        return taxData;


    }
}
