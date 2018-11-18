package com.paymentservice.paymentservice.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class GenericMarshaller<T> {

    public String marshall(T t, Class clazz) {
        StringWriter stringWriter = new StringWriter();
        try {
            JAXBContext jContext = JAXBContext.newInstance(clazz);
            Marshaller marshallerObj = jContext.createMarshaller();
            marshallerObj.marshal(t, stringWriter);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return stringWriter.toString();
    }
}
