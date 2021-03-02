package repository.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Album implements Serializable {
    @XmlElement
    private String isrc;
    @XmlElement
    private String title;
    @XmlElement
    private Integer releaseYear;
    /**
    @XmlElement
    private String artist;
     */
    @XmlElement
    private String contentDesc;
    @XmlElement
    private Artist artist;

    public Album() {

    }

    public Album(String isrc, String title, int releaseYear, String contentDesc, Artist artist) {
        this.isrc = isrc;
        this.title = title;
        this.releaseYear = releaseYear;
        //this.artist = artist;
        this.contentDesc = contentDesc;
        this.artist = artist;
    }

    public String getIsrc() {
        return isrc;
    }

    public void setIsrc(String isrc) {
        this.isrc = isrc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public String getContentDesc() {
        return contentDesc;
    }

    public void setContentDesc(String contentDesc) {
        this.contentDesc = contentDesc;
    }

    public void trim() {
        this.setIsrc(isrc != null ? isrc.trim() : null);
        this.setTitle(title != null ? title.trim() : null);
        this.setContentDesc(contentDesc != null ? contentDesc.trim() : null);

        if(artist != null) {
            artist.setFirstname(artist.getFirstname() != null ? artist.getFirstname().trim() : null);
            artist.setLastname(artist.getLastname() != null ? artist.getLastname().trim() : null);
        }
    }

    public String toString(){
        String description = contentDesc == null || contentDesc.isEmpty() ? "N/A" : contentDesc;
        return String.format("ISRC: %s, title: %s, release year: %d, artist: %s, description: %s", isrc, title, releaseYear, artist, description);
    }
}
