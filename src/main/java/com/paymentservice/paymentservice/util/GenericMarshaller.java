package com.paymentservice.paymentservice.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class GenericMarshaller<T> {

    public String marshall(T t, Class clazz) throws JAXBException {
        StringWriter stringWriter = new StringWriter();
        JAXBContext jContext = JAXBContext.newInstance(clazz);
        Marshaller marshallerObj = jContext.createMarshaller();
        marshallerObj.marshal(t, stringWriter);
        return stringWriter.toString();
    }
}
