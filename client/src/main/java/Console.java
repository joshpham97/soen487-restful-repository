import java.util.Scanner;

public class Console {
    private Scanner scanner = new Scanner(System.in);

    public void start(){
        MainMenuAction mainMenuAction = selectMainMenuOption();

        if(mainMenuAction == MainMenuAction.INVALID)
            System.out.println("Invalid option, please try again");
        //else if(mainMenuAction != MainMenuAction.EXIT)

    }

    public MainMenuAction selectMainMenuOption(){
        scanner = new Scanner(System.in);
        System.out.println("Please an option by entering the option number: ");
        System.out.println("1. Manage albums");
        System.out.println("2. Manage artists");
        System.out.println("3. Quit");
        int option = scanner.nextInt();

        switch (option){
            case 1: return MainMenuAction.ALBUM;
            case 2: return MainMenuAction.ARTIST;
            case 3: return MainMenuAction.EXIT;
            default: return MainMenuAction.INVALID;
        }
    }

    public ResourceMenuAction selectResourceMenuOption(MainMenuAction mainMenuAction){
        String resourceText = mainMenuAction.toString().toLowerCase();

        System.out.println("\nPlease select an action: ");
        System.out.printf("1. List %ss%n", resourceText);
        System.out.printf("2. Get %s%n", resourceText);
        System.out.printf("3. Create %s%n", resourceText);
        System.out.printf("4. Update %s%n", resourceText);
        System.out.printf("5. Delete %s%n", resourceText);
        int option = scanner.nextInt();

        switch (option){
            case 1: return ResourceMenuAction.LIST;
            case 2: return ResourceMenuAction.GET;
            case 3: return ResourceMenuAction.POST;
            case 4: return ResourceMenuAction.PUT;
            case 5: return ResourceMenuAction.DELETE;
            default: return ResourceMenuAction.INVALID;
        }
    }
}
