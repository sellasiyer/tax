package com.sn.services.tax;


import org.apache.log4j.Logger;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

/**
 * Interceptor to log the request and response of the SOAP webservices.
 */
public class SNWebServiceClientLoggingInterceptor implements ClientInterceptor {

    private static final Logger log = Logger.getLogger(SNWebServiceClientLoggingInterceptor.class);
    private static final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

    static {
        dbf.setValidating(false);
    }

    public static void printoutLog(final WebServiceMessage message, final boolean isRequest) {
        if (message != null) {
            try {
                String type = isRequest ? "Request" : "Response";
                log.info("============================" + type + " BEGIN===========================================");
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                message.writeTo(out);
                byte[] charData = out.toByteArray();
                String body = new String(charData, "ISO-8859-1");
                log.info("\n" + formatXml(body));
                log.info("============================" + type + " END=============================================");
            } catch (Exception e) {
                //Catching Exception, we do not want to stop the request for this logger. It is a debug tool.
                log.error("Could not log message: ", e);
            }
        }
    }

    /**
     * Attempt to format a string response as pretty-printed xml.
     * Returns the original string body if formatting fails.
     */
    public static String formatXml(String body) {

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(body)));

            Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            Writer out = new StringWriter();
            tf.transform(new DOMSource(doc), new StreamResult(out));

            return out.toString();

        } catch (ParserConfigurationException | SAXException | TransformerException | IOException e) {
            /* Fail gracefully by returning the original serialized body. */
            return body;
        }
    }

    @Override
    public boolean handleFault(final MessageContext messageContext) throws WebServiceClientException {
        return false;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception e) throws WebServiceClientException {

    }

    @Override
    public boolean handleRequest(final MessageContext messageContext) throws WebServiceClientException {
        printoutLog(messageContext.getRequest(), true);
        return true;
    }

    @Override
    public boolean handleResponse(final MessageContext messageContext) throws WebServiceClientException {
        // Must check hasResponse, because GetResponse will create a new one if NULL and throw off the library.
        if (messageContext.hasResponse()) printoutLog(messageContext.getResponse(), false);
        return true;
    }
}
