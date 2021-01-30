package repository.core;

public class Album {
    private String isrc;
    private String title;
    private int releaseYear;
    private String artist;
    private String contentDesc;

    public Album(String isrc, String title, int releaseYear, String artist, String contentDesc) {
        this.isrc = isrc;
        this.title = title;
        this.releaseYear = releaseYear;
        this.artist = artist;
        this.contentDesc = contentDesc;
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

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getContentDesc() {
        return contentDesc;
    }

    public void setContentDesc(String contentDesc) {
        this.contentDesc = contentDesc;
    }

    public String toString(){
        String description = contentDesc == null || contentDesc.isEmpty() ? "N/A" : contentDesc;
        return String.format("ISRC: %s, title: %s, release year: %d, artist: %s, description: %s", isrc, title, releaseYear, artist, description);
    }
}
