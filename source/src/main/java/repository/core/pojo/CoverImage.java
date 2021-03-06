package repository.core.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Blob;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CoverImage {
    @XmlElement
    private int coverImageId;
    @XmlElement
    private String mimeType;
    private Blob blob; //blob should not be serialized
    private String isrc;

    public String getIsrc() {
        return isrc;
    }

    public void setIsrc(String isrc) {
        this.isrc = isrc;
    }

    public int getCoverImageId() {
        return coverImageId;
    }

    public void setCoverImageId(int coverImageId) {
        this.coverImageId = coverImageId;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Blob getBlob() {
        return blob;
    }

    public void setBlob(Blob blob) {
        this.blob = blob;
    }
}
