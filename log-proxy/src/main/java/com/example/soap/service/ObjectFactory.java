
package com.example.soap.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.example.soap.service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ClearLogResponse_QNAME = new QName("http://service.soap.example.com/", "clearLogResponse");
    private final static QName _ListLog_QNAME = new QName("http://service.soap.example.com/", "listLog");
    private final static QName _ClearLog_QNAME = new QName("http://service.soap.example.com/", "clearLog");
    private final static QName _ListLogResponse_QNAME = new QName("http://service.soap.example.com/", "listLogResponse");
    private final static QName _LogFault_QNAME = new QName("http://service.soap.example.com/", "LogFault");
    private final static QName _Log_QNAME = new QName("http://service.soap.example.com/", "log");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.example.soap.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Log }
     * 
     */
    public Log createLog() {
        return new Log();
    }

    /**
     * Create an instance of {@link LogFault }
     * 
     */
    public LogFault createLogFault() {
        return new LogFault();
    }

    /**
     * Create an instance of {@link ClearLogResponse }
     * 
     */
    public ClearLogResponse createClearLogResponse() {
        return new ClearLogResponse();
    }

    /**
     * Create an instance of {@link ClearLog }
     * 
     */
    public ClearLog createClearLog() {
        return new ClearLog();
    }

    /**
     * Create an instance of {@link ListLogResponse }
     * 
     */
    public ListLogResponse createListLogResponse() {
        return new ListLogResponse();
    }

    /**
     * Create an instance of {@link ListLog }
     * 
     */
    public ListLog createListLog() {
        return new ListLog();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClearLogResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.soap.example.com/", name = "clearLogResponse")
    public JAXBElement<ClearLogResponse> createClearLogResponse(ClearLogResponse value) {
        return new JAXBElement<ClearLogResponse>(_ClearLogResponse_QNAME, ClearLogResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListLog }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.soap.example.com/", name = "listLog")
    public JAXBElement<ListLog> createListLog(ListLog value) {
        return new JAXBElement<ListLog>(_ListLog_QNAME, ListLog.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ClearLog }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.soap.example.com/", name = "clearLog")
    public JAXBElement<ClearLog> createClearLog(ClearLog value) {
        return new JAXBElement<ClearLog>(_ClearLog_QNAME, ClearLog.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListLogResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.soap.example.com/", name = "listLogResponse")
    public JAXBElement<ListLogResponse> createListLogResponse(ListLogResponse value) {
        return new JAXBElement<ListLogResponse>(_ListLogResponse_QNAME, ListLogResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LogFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.soap.example.com/", name = "LogFault")
    public JAXBElement<LogFault> createLogFault(LogFault value) {
        return new JAXBElement<LogFault>(_LogFault_QNAME, LogFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Log }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.soap.example.com/", name = "log")
    public JAXBElement<Log> createLog(Log value) {
        return new JAXBElement<Log>(_Log_QNAME, Log.class, null, value);
    }

}
