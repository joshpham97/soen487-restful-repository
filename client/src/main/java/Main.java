import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option = 0;

        do{
            option = 0;
            System.out.println("Please an option by entering the option number: ");
            System.out.println("1. Manage albums");
            System.out.println("2. Manage artists");
            System.out.println("3. Quit");
            option = scanner.nextInt();

            if (option < 1 || option > 3){
                System.out.println("Invalid option, please try again");
            }else if (option < 3){
                MainMenuAction mainMenuAction;
                if (option == 1)
                    manageResource(MainMenuAction.ALBUM);
                else
                    manageResource(MainMenuAction.ARTIST);
            }

        }while(option != 3);
    }
    public static void displayMenu(MainMenuAction mainMenuAction){
        String resourceText = mainMenuAction.toString().toLowerCase();

        System.out.println("\nPlease select an action: ");
        System.out.printf("1. List %ss%n", resourceText);
        System.out.printf("2. Get %s%n", resourceText);
        System.out.printf("3. Create %s%n", resourceText);
        System.out.printf("4. Update %s%n", resourceText);
        System.out.printf("5. Delete %s%n", resourceText);
    }

    public static void manageResource(MainMenuAction mainMenuAction){
        displayMenu(mainMenuAction);
    }
}
