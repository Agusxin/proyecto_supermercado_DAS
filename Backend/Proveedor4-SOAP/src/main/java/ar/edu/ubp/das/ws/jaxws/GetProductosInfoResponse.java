
package ar.edu.ubp.das.ws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 3.5.6
 * Tue Oct 08 20:22:43 ART 2024
 * Generated source version: 3.5.6
 */

@XmlRootElement(name = "getProductosInfoResponse", namespace = "http://ws.das.ubp.edu.ar/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getProductosInfoResponse", namespace = "http://ws.das.ubp.edu.ar/")

public class GetProductosInfoResponse {

    @XmlElement(name = "return")
    private java.util.List<java.lang.String> _return;

    public java.util.List<java.lang.String> getReturn() {
        return this._return;
    }

    public void setReturn(java.util.List<java.lang.String> new_return)  {
        this._return = new_return;
    }

}

