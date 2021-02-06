
import client.ArtistClient;
import enums.MainMenuAction;
import enums.ResourceMenuAction;

import java.util.Scanner;
import org.netbeans.saas.root.Rootapplication;
import org.netbeans.saas.RestResponse;

public class Console {

    private Scanner scanner = new Scanner(System.in);
    private MainMenuAction mainMenuAction;
    private ResourceMenuAction resourceMenuAction;
    private ArtistClient artistClient;

    public Console() {
        artistClient = new ArtistClient();
    }

    public void start() {
        do {
            mainMenuAction = selectMainMenuOption();

            if (mainMenuAction == MainMenuAction.INVALID) {
                System.out.println("Invalid option, please try again");
            } else if (mainMenuAction != MainMenuAction.EXIT) {
                resourceMenuAction = selectResourceMenuOption();

                String result = null;
                if (mainMenuAction == MainMenuAction.ARTIST) {
                    switch (resourceMenuAction) {
                        case LIST:
                            result = listArtists();
                            break;
                        case GET:
                            result = getArtist();
                            break;
                        case POST:
                            result = createArtist();
                            break;
                        case PUT:
                            result = updateArtist();
                            break;
                        case DELETE:
                            result = deleteArtist();
                            break;
                    }
                }else{
                    switch (resourceMenuAction) {
                        case LIST:
                            result = listAlbums();
                            break;
                        case GET:
                            result = getAlbum();
                            break;
                        case POST:
                            result = createAlbum();
                            break;
                        case PUT:
                            result = updateAlbum();
                            break;
                        case DELETE:
                            result = deleteAlbum();
                            break;
                    }
                }

                if (result != null) {
                    System.out.println(result);
                } else {
                    System.out.println("An error has occurred, please try again.");
                }

                System.out.println();
            }
        } while (mainMenuAction != MainMenuAction.EXIT);
    }

    public MainMenuAction selectMainMenuOption() {
        scanner = new Scanner(System.in);
        System.out.println("Please an option by entering the option number: ");
        System.out.println("1. Manage albums");
        System.out.println("2. Manage artists");
        System.out.println("3. Quit");
        int option = scanner.nextInt();

        switch (option) {
            case 1:
                return MainMenuAction.ALBUM;
            case 2:
                return MainMenuAction.ARTIST;
            case 3:
                return MainMenuAction.EXIT;
            default:
                return MainMenuAction.INVALID;
        }
    }

    public ResourceMenuAction selectResourceMenuOption() {
        String resourceText = mainMenuAction.toString().toLowerCase();

        System.out.println("\nPlease select an action: ");
        System.out.printf("1. List %ss%n", resourceText);
        System.out.printf("2. Get %s%n", resourceText);
        System.out.printf("3. Create %s%n", resourceText);
        System.out.printf("4. Update %s%n", resourceText);
        System.out.printf("5. Delete %s%n", resourceText);
        int option = scanner.nextInt();
        scanner.nextLine(); //Consumes new line character
        System.out.println();

        switch (option) {
            case 1:
                return ResourceMenuAction.LIST;
            case 2:
                return ResourceMenuAction.GET;
            case 3:
                return ResourceMenuAction.POST;
            case 4:
                return ResourceMenuAction.PUT;
            case 5:
                return ResourceMenuAction.DELETE;
            default:
                return ResourceMenuAction.INVALID;
        }
    }

    public String listArtists() {
        return artistClient.list();
    }

    public String getArtist() {
        System.out.println("Please enter the nickname of the artist: ");
        String nickname = getUserInput("Nickname", true);
        return artistClient.get(nickname);
    }

    public String createArtist() {
        System.out.print("Please enter the information of the artist: ");
        String nickname = getUserInput("Nickname", true);
        String firstname = getUserInput("First name", true);
        String lastname = getUserInput("Last name", true);
        String bio = getUserInput("Bio (optional)", false);

        return artistClient.post(nickname, firstname, lastname, bio);
    }

    public String updateArtist() {
        System.out.print("Please enter the information of the artist: ");
        String nickname = getUserInput("Nickname", true);
        String firstname = getUserInput("First name", true);
        String lastname = getUserInput("Last name", true);
        String bio = getUserInput("Bio (optional)", false);

        return artistClient.put(nickname, firstname, lastname, bio);
    }

    public String deleteArtist() {
        System.out.print("Please enter the nickname of the artist: ");
        String nickname = getUserInput("Nickname", true);
        return artistClient.delete(nickname);
    }

    public String listAlbums() {
        try {
            RestResponse result = Rootapplication.listAlbum();
            return result.getDataAsString();
        } catch (Exception ex) {
            return "There was an error sending the request.";
        }
    }

    public String getAlbum() {
        try {
            System.out.print("Please enter the isrc of the album: ");
            String isrc = getUserInput("ISRC: ", true);
            RestResponse result = Rootapplication.getAlbum(isrc);
            return result.getDataAsString();
        } catch (Exception ex) {
            return "There was an error sending the request.";
        }
    }

    public String createAlbum() {
        try {
            System.out.print("Please enter the information of the album: ");
            String isrc = getUserInput("ISRC: ", true);
            String title = getUserInput("Title: ", true);
            int releaseYear = Integer.parseInt(getUserInput("Release year: ", true));
            String artist = getUserInput("Artist: ", true);
            String contentDesc = getUserInput("Content description (optional): ", true);

            RestResponse result = Rootapplication.addAlbum(isrc, title, releaseYear, artist, contentDesc);
            return result.getDataAsString();
        } catch (Exception ex) {
            return "There was an error sending the request.";
        }
    }

    public String updateAlbum() {
        try {
            System.out.print("Please enter the information of the album: ");
            String isrc = getUserInput("ISRC: ", true);
            String title = getUserInput("Title: ", true);
            int releaseYear = Integer.parseInt(getUserInput("Release year: ", true));
            String artist = getUserInput("Artist: ", true);
            String contentDesc = getUserInput("Content description (optional): ", true);

            RestResponse result = Rootapplication.updateAlbum(isrc, title, releaseYear, artist, contentDesc);
            return result.getDataAsString();
        } catch (Exception ex) {
            return "There was an error sending the request.";
        }
    }

    public String deleteAlbum() {
        try {
            System.out.print("Please enter the information of the album: ");
            String isrc = getUserInput("ISRC: ", true);
            RestResponse result = Rootapplication.deleteAlbum(isrc);
            return result.getDataAsString();
        } catch (Exception ex) {
            return "There was an error sending the request.";
        }
    }

    public String getUserInput(String inputName, boolean isRequired) {
        if (isRequired) {
            String input = "";
            while (input.isEmpty()) {
                System.out.print(inputName + ": ");
                input = scanner.nextLine();

                if (input.isEmpty()) {
                    System.out.println("This value is required, please enter again.");
                }
            }

            return input;
        } else {
            System.out.print(inputName + ": ");
            return scanner.nextLine();
        }
    }
}
