package com.sn.services.tax;

import com.sn.services.tax.converter.TaxQuotationRequestConverter;
import com.sn.services.tax.converter.TaxQuotationResponseConverter;
import com.sn.webservice.model.tax.ObjectFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.security.support.KeyStoreFactoryBean;
import org.springframework.ws.soap.security.support.TrustManagersFactoryBean;
import org.springframework.ws.transport.http.HttpsUrlConnectionMessageSender;

import javax.net.ssl.HostnameVerifier;

@Configuration
public class TaxClientConfiguration {

    @Value("${client.ssl.trust-store}")
    private Resource trustStore;

    @Value("${client.ssl.trust-store-password}")
    private String trustStorePassword;


    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setCheckForXmlRootElement(false);
        marshaller.setContextPath("com.sn.webservice.model.tax");

        return marshaller;
    }

    @Bean
    public TaxClient quoteClient(Jaxb2Marshaller marshaller) throws Exception {
        TaxClient client = new TaxClient();
        client.setDefaultUri("https://eaposbstg.comfort.com/SCCalculateQuoteSalesTaxService");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        client.setMessageSender(httpsUrlConnectionMessageSender());
        ClientInterceptor[] clientInterceptorList = new ClientInterceptor[1];
        clientInterceptorList[0] = loggingInterceptor();
        client.setInterceptors(clientInterceptorList);
        return client;
    }

    @Bean
    public HttpsUrlConnectionMessageSender httpsUrlConnectionMessageSender() throws Exception {
        HttpsUrlConnectionMessageSender httpsUrlConnectionMessageSender =
                new HttpsUrlConnectionMessageSender();
        httpsUrlConnectionMessageSender.setTrustManagers(trustManagersFactoryBean().getObject());
        // allows the client to skip host name verification as otherwise following error is thrown:
        // java.security.cert.CertificateException: No name matching localhost found
        httpsUrlConnectionMessageSender.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) {
                return true;
            }
        });

        return httpsUrlConnectionMessageSender;
    }

    @Bean
    public KeyStoreFactoryBean trustStore() {
        KeyStoreFactoryBean keyStoreFactoryBean = new KeyStoreFactoryBean();
        keyStoreFactoryBean.setLocation(trustStore);
        keyStoreFactoryBean.setPassword(trustStorePassword);

        return keyStoreFactoryBean;
    }

    @Bean
    public TrustManagersFactoryBean trustManagersFactoryBean() {
        TrustManagersFactoryBean trustManagersFactoryBean = new TrustManagersFactoryBean();
        trustManagersFactoryBean.setKeyStore(trustStore().getObject());

        return trustManagersFactoryBean;
    }


    @Bean
    public SNWebServiceClientLoggingInterceptor loggingInterceptor() {
        SNWebServiceClientLoggingInterceptor snWebServiceClientLoggingInterceptor = new SNWebServiceClientLoggingInterceptor();


        return snWebServiceClientLoggingInterceptor;
    }


    @Bean
    public ObjectFactory objectFactory() {
        return new ObjectFactory();
    }

    @Bean
    public GenericConversionService conversionService() {
        GenericConversionService conversionService = new GenericConversionService();
        conversionService.addConverter(new TaxQuotationRequestConverter());
        conversionService.addConverter(new TaxQuotationResponseConverter());
        return conversionService;
    }


    @Bean
    public TaxService taxService() {
        return new TaxService();
    }

}
