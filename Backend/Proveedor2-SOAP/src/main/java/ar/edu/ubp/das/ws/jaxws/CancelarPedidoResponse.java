
package ar.edu.ubp.das.ws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 3.5.6
 * Mon Oct 07 17:34:28 ART 2024
 * Generated source version: 3.5.6
 */

@XmlRootElement(name = "cancelarPedidoResponse", namespace = "http://ws.das.ubp.edu.ar/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cancelarPedidoResponse", namespace = "http://ws.das.ubp.edu.ar/")

public class CancelarPedidoResponse {

    @XmlElement(name = "return")
    private java.lang.String _return;

    public java.lang.String getReturn() {
        return this._return;
    }

    public void setReturn(java.lang.String new_return)  {
        this._return = new_return;
    }

}

