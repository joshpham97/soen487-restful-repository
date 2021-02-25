package repository.core;

public class Artist {
    private String firstname;
    private String lastname;

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
