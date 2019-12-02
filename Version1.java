import java.util.Scanner;
import java.io.File;


public class Phonebook {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("phonebook.text"));
        Scanner sc1 = new Scanner(System.in);
        final int SIZE = 10000;
        int count = 0, countLookUp = 0, countReverse = 0;
        String[] lastName = new String[SIZE];
        String[] firstName = new String[SIZE];
        String[] phoneNumber = new String[SIZE];
        String input = "", mainMenu = "";
        count = populateArray(lastName, firstName, phoneNumber);
        mainMenu = mainMenu(mainMenu, sc1);
        for (int i = 0; i < count; i++) {
            if (mainMenu.equals("l")) {
                input = lookUp(lastName, firstName, phoneNumber, count, input, sc1);
                countLookUp++;
            } else if (mainMenu.equals("r")) {
                input = reverseLookUp(phoneNumber, lastName, firstName, count, sc1);
                countReverse++;
            } else if (mainMenu.equals("q")) {
                break;
            }
            System.out.println(input);
            
            
            mainMenu = mainMenu(mainMenu, sc1);

        }
        System.out.println();
        System.out.println(countLookUp + " lookups performed");
        System.out.println(countReverse + " reverse lookups performed");
    }
    
    //Populates array and counts the number of names in array
    private static int populateArray(String[] lastName, String[] firstName, String[] phoneNumber) throws Exception {
        Scanner sc = new Scanner(new File("phonebook.text"));
        int count = 0;
        while (sc.hasNext()) {
            lastName[count] = sc.next();
            firstName[count] = sc.next();
            phoneNumber[count] = sc.next();
            count++;
        }
        return count;
    }

    //Looks up phone numbers by last name and first name
    private static String lookUp(String[] LastName, String[] FirstName, String[] PhoneNumber, int n, String input, Scanner sc1) {
        String firstName, lastName;
        System.out.print("last name? ");
        lastName = sc1.next();
        System.out.print("first name? ");
        firstName = sc1.next();
        for (int i = 0; i < n; i++) {
            if (LastName[i].equalsIgnoreCase(lastName) && FirstName[i].equalsIgnoreCase(firstName)) {
                return FirstName[i] + " " + LastName[i] + "'s phone number is " + PhoneNumber[i] + "\n";
            }
        }
        return "-- Name not found\n";
    }

    //Looks up first name and last name of person by inputting phone number
    private static String reverseLookUp(String[] PhoneNumber, String[] LastName, String[] FirstName, int n, Scanner sc1) {
        String firstName, lastName, phoneNumber;
        System.out.print("phone number (nnn-nnn-nnnn)? ");
        phoneNumber = sc1.next();
        for (int i = 0; i < n; i++) {
            if (PhoneNumber[i].equals(phoneNumber)) {
                return PhoneNumber[i] + " belongs to " + LastName[i] + ", " + FirstName[i] + "\n";
            }
        }
        return "-- Phone number not found\n";
    }

    //This method gives you the choices to perform for the program
    private static String mainMenu(String mainMenu, Scanner sc1) {
        String choice;
        System.out.print("lookup, reverse-lookup, quit (l/r/q)? ");
        choice = sc1.next();
        return choice;
    }
}
