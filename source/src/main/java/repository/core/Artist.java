package repository.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Artist implements Serializable {
    @XmlElement
    private String firstname;
    @XmlElement
    private String lastname;

    public Artist() {

    }

    public Artist(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public String artistDetails(){
        //String description = !bio.isEmpty() ? bio : "N/A";
        return String.format("fullname: %s %s", firstname, lastname);
    }

    public String toString(){
        //String description = !bio.isEmpty() ? bio : "N/A";
        return String.format("first name: %s, last name: %s", firstname, lastname);
    }
}
