import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.TreeMap;

class PhonebookApp {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Usage: PhonebookApp 'phonebook-filename'");
        } else {
            String inputDecision = "";
            Scanner sc1 = new Scanner(System.in);
            Phonebook phonebook = new Phonebook(args[0]);
            //Phonebook phonebook = new Phonebook("phonebook.text");
            String input = "", mainMenu = "";
            mainMenu = mainMenu(mainMenu, sc1);

            try {
                while (!inputDecision.equalsIgnoreCase("q")) {
                    if (mainMenu.equals("l")) {
                        input = phonebook.lookUp(sc1);

                    } else if (mainMenu.equals("q")) {
                        break;
                    }
                    System.out.println(input);

                    mainMenu = mainMenu(mainMenu, sc1);

                }

                //System.out.println();
            } catch (Exception s) {
                System.out.println("*** Exception *** Phonebook capacity exceeded - increase size of underlying array");
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

class Name implements Comparable<Name> {

    public Name(String last, String first) {
        this.last = last;
        this.first = first;
    }
    public Name(String first) {
        this("", first);}

    public String getFormal() {
        return first + " " + last;}
    public String getOfficial() {
        return last + ", " + first;}

    public boolean equals(Name other) {
        return first.equals(other.first) && last .equals(other.last);}

    public String toString() {return first + " " + last;}

    public static Name read(Scanner scanner) {
        if (!scanner.hasNext()) return null;
        String last = scanner.next();
        String first = scanner.next();
        return new Name(first,last);
    }



    public String getInitials() {
        return first.toUpperCase().charAt(0) + "." + last.toUpperCase().charAt(0) + ".";
    }

    private String first, last;

    public int compareTo(Name name) {
        return this.toString().compareTo(name.toString());
    }
}

class ExtendedPhoneNumber extends PhoneNumber{


    public ExtendedPhoneNumber(String num, String phoneNumberDescription){
        super(num);
        this.phoneNumberDescription = phoneNumberDescription;

    }
    private String phoneNumberDescription;



    public static ExtendedPhoneNumber read(Scanner scanner) {
        if (!scanner.hasNext()) return null;
        String phoneNumberDescription = scanner.next();
        String num = scanner.next();
        //System.out.println(phoneNumberDescription);
        return new ExtendedPhoneNumber(phoneNumberDescription, num);
    }

    public String getType() {
        return this.phoneNumberDescription;
    }
}

class PhonebookEntry {

        public PhonebookEntry(Name name, ArrayList<ExtendedPhoneNumber> arrli) {
            this.name = name;
            this.arrli = arrli;
        }

        private Name name;
        private ArrayList<ExtendedPhoneNumber> arrli;


        public Name getName() {
            return this.name;
        }

        public String getPhoneNumbers() {
            String symbol = "";
            for (int i = 0; i < arrli.size(); i++) {
                if ((arrli.size() == 1 || (arrli.size() - 1) == i)) {
                    symbol += arrli.get(i).toString() + ": " + arrli.get(i).getType();
                    symbol += "]";
                    symbol+= "\n";
                }
                else {
                    symbol+= arrli.get(i).toString()+ ": " +  arrli.get(i).getType() + ", ";
                }

            }
            return symbol;
        }

        public String call() {
            return (this.getPhoneNumbers().charAt(1) != '8') ? ("Dialing " + this.toString())
                    : ("Dialing (toll-free) " + this.toString());
        }

        public String toString() {
            String symbol = "";
            for(int i = 0; i < arrli.size(); i++) {
                 symbol+= arrli.get(i);
            }
            return getName() + ": " + symbol;
        }

        public Boolean equals(PhonebookEntry other) {
            return ((this.name.toString().equalsIgnoreCase(other.name.toString()))
                    && (this.arrli.toString().equals(other.arrli.toString())));
        }

        public static PhonebookEntry read(Scanner scanner) {
            ArrayList<ExtendedPhoneNumber> extend = new ArrayList<ExtendedPhoneNumber>();
            int countPhones;
            Name name;
            if(!scanner.hasNext()) return null;
            name = Name.read(scanner);
            countPhones = scanner.nextInt();
            for(int i = 0; i < countPhones; i++) {
                extend.add(ExtendedPhoneNumber.read(scanner));

            }
            return new PhonebookEntry(name, extend);
        }

    }




class PhoneNumber {
        public PhoneNumber(String num) {
            this.number = num;
        }

        public boolean equals(PhoneNumber other) {
            return number.equals(other.number);}

        public String toString() {
            return number;
        }

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

class Phonebook {

    private TreeMap<Name, PhonebookEntry> phonebookTreeMap;
    public Phonebook(String fileName) throws Exception {
        Scanner phonebook = new Scanner(new File(fileName));
        phonebookTreeMap = new TreeMap<>();
        while (phonebook.hasNext()) {

            PhonebookEntry phonebookEntry = PhonebookEntry.read(phonebook);
            Name name = phonebookEntry.getName();
            phonebookTreeMap.put(name, phonebookEntry);
        }
    }
    public  String lookUp(Scanner sc1) {
        String firstName, lastName;
        System.out.print("last name? ");
        lastName = sc1.next();
        System.out.print("first name? ");
        firstName = sc1.next();
        phonebookTreeMap.get(new Name(firstName, lastName));
        if(phonebookTreeMap.containsKey((new Name(firstName, lastName)))) {
            return firstName + " " +  lastName + "'s" + " phone numbers: [" + phonebookTreeMap.get(new Name(firstName, lastName)).getPhoneNumbers();
        }
        else {
            return "-- Name not found\n";
        }

    }
}

