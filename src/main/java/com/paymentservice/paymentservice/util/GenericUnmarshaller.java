package com.paymentservice.paymentservice.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

public class GenericUnmarshaller<T> {

    public T unmarshall(String xmlString, Class clazz) throws JAXBException {
        T t = null;
        StringReader reader = new StringReader(xmlString);
        JAXBContext jContext = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshallerObj = jContext.createUnmarshaller();
        t = (T) unmarshallerObj.unmarshal(reader);
        return t;
    }
}
