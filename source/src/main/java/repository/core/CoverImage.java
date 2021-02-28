package repository.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Blob;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CoverImage {
    @XmlElement
    private String coverImageId;
    @XmlElement
    private String mimeType;
    private Blob blob; //blob should not be serialized
}
