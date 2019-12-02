import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

 class PhonebookApp {
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(new File("phonebook.text"));
        if (args.length == 0) {
            System.out.println("Usage: PhonebookApp 'phonebook-filename");
        } else {
            String inputDecision = "";
            Scanner sc1 = new Scanner(System.in);
            Phonebook phonebook = new Phonebook(args[0]);
            int count = 0, countLookUp = 0;


            String input = "", mainMenu = "";
            mainMenu = mainMenu(mainMenu, sc1);

            try {
                while (!inputDecision.equalsIgnoreCase("q")) {
                    if (mainMenu.equals("l")) {
                        input = phonebook.lookUp(sc1);
                        countLookUp++;
                    } else if (mainMenu.equals("q")) {
                        break;
                    }
                    System.out.println(input);

                    mainMenu = mainMenu(mainMenu, sc1);

                }

                System.out.println();
                System.out.println(countLookUp + " lookups performed");
            } catch (Exception s) {
                System.out.println("*** Exception *** Phonebook capacity exxceeded - increase size of underlying array");
            }
        }
    }



    //This method gives you the choices to perform for the program
    private static String mainMenu(String mainMenu, Scanner sc1) {
        String choice;
        System.out.print("lookup, quit (l/q)? ");
        choice = sc1.next();
        return choice;
    }
}



    class PhonebookEntry {

        public PhonebookEntry(Name name, PhoneNumber phone) {
            this.name = name;
            this.phoneNumber = phone;
        }

        private Name name;
        private PhoneNumber phoneNumber;

        public String getName() {
            return this.name.toString();
        }

        public String getPhoneNumber() {
            return this.phoneNumber.toString();
        }

        public String call() {
            return (this.getPhoneNumber().charAt(1) != '8') ? ("Dialing " + this.toString())
                    : ("Dialing (toll-free) " + this.toString());
        }

        public String toString() {
            return getName() + ": " + getPhoneNumber();
        }

        public Boolean equals(PhonebookEntry other) {
            return ((this.name.toString().equalsIgnoreCase(other.name.toString()))
                    && (this.phoneNumber.toString().equals(other.phoneNumber.toString())));
        }

        public static PhonebookEntry read(Scanner scanner) {
            if (!scanner.hasNext()) return null;
            Name name               = Name.read(scanner);
            PhoneNumber phoneNumber = PhoneNumber.read(scanner);

            return new PhonebookEntry (name, phoneNumber);
        }
    }


class PhoneNumber {
        public PhoneNumber(String num) {
            this.number = num;
        }

        public boolean equals(PhoneNumber other) {return number.equals(other.number);}

        public String toString() {return number;}

        public static PhoneNumber read(Scanner scanner) {
            if (!scanner.hasNext()) return null;
            String number = scanner.next();
            return new PhoneNumber(number);
        }

        public String getAreaCode(PhoneNumber num) {
            return num.toString().substring(1, 4);
        }

        public String getExchange(PhoneNumber num) {
            return num.toString().substring(5, 8);
        }

        public String getLineNumber(PhoneNumber num) {
            return num.toString().substring(9, 13);
        }

        public Boolean isTollFree(PhoneNumber num) {
            return (num.toString().charAt(1) == '8') ? true : false;
        }

        private String number;
    }
 class Name {

    public Name(String last, String first) {
        this.last = last;
        this.first = first;
    }
    public Name(String first) {this("", first);}

    public String getFormal() {return first + " " + last;}
    public String getOfficial() {return last + ", " + first;}

    public boolean equals(Name other) {return first.equals(other.first) && last .equals(other.last);}

    public String toString() {return first + " " + last;}

    public static Name read(Scanner scanner) {
        if (!scanner.hasNext()) return null;
        String last = scanner.next();
        String first = scanner.next();
        return new Name(last, first);
    }

    public String getInitials() {
        return first.toUpperCase().charAt(0) + "." + last.toUpperCase().charAt(0) + ".";
    }

    private String first, last;
}
