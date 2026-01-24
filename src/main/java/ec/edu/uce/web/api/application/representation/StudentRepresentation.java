package ec.edu.uce.web.api.application.representation;

import java.time.LocalDateTime;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "student")
@XmlAccessorType(XmlAccessType.FIELD)
public class StudentRepresentation {
    @XmlElement
    public Long id;
    @XmlElement
    public String name;
    @XmlElement
    public String lastName;
    @XmlElement
    public String email;
    @XmlElement
    public LocalDateTime birthDay;
    @XmlElement
    public String province;
    @XmlElement
    public String gender;

}
