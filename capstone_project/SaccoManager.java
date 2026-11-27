package capstone_project;

// Console-based management system for M-Kopo SACCO in Nairobi

import java.io.*;
import java.util.*;

public class SaccoManager {

    // HashMap to store registered members using their unique Member ID
    private static HashMap<String, Member> membersDb = new HashMap<>();

    // HashSet to make sure no two members share the same National ID
    private static HashSet<String> registeredNationalIds = new HashSet<>();

    // File names for saving and loading data
    private static final String MEMBERS_FILE = "capstone_project/members.csv";
    private static final String TRANSACTIONS_FILE = "capstone_project/transactions.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Load any existing members from members.csv when the program starts
        loadDataFromFile();

        // Variable to store the option from the menu below
        int option = 0;

        do {
            // Display the menu options
            System.out.println();
            System.out.println("=====M-KOPO SACCO MANAGEMENT SYSTEM=====");
            System.out.println("Welcome to M-Kopo SACCO!");
            System.out.println("1. Register New Member");
            System.out.println("2. Open an Account");
            System.out.println("3. Deposit Money");
            System.out.println("4. Withdraw Money");
            System.out.println("5. View Member Details");
            System.out.println("6. Exit");
            System.out.print("Please pick an option: ");

            // Catching InputMismatchException in case the user types text instead of a number
            try {
                option = scanner.nextInt();

                // Consume newline character
                scanner.nextLine();
            }
            catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");

                // Consume the invalid input so the loop does not get stuck
                scanner.nextLine();
                continue;
            }

            switch (option) {

                // Registering a new member
                case 1:
                    registerMember(scanner);
                    break;

                // Opening a Savings or Loan account
                case 2:
                    openAccount(scanner);
                    break;

                // Depositing money
                case 3:
                    depositMoney(scanner);
                    break;

                // Withdrawing money
                case 4:
                    withdrawMoney(scanner);
                    break;

                // Viewing member details and calculated interest
                case 5:
                    viewMemberDetails(scanner);
                    break;

                // Saving data and exiting the system
                case 6:
                    saveDataToFile();
                    System.out.println("Data saved successfully.");
                    System.out.println("Exiting.....");
                    break;

                default:
                    System.out.println("Please enter a valid option. Either 1, 2, 3, 4, 5, or 6.");
                    break;
            }
        } while (option != 6);

        // Closing to free up resources
        scanner.close();
    }

    // Method to register a new SACCO member
    private static void registerMember(Scanner scanner) {
        System.out.print("Enter Member ID (e.g., M-001): ");
        String memberId = scanner.nextLine().trim();

        // Checks if the Member ID is blank or already taken
        if (memberId.isBlank()) {
            System.out.println("Error: Member ID cannot be empty.");
            return;
        }

        if (membersDb.containsKey(memberId)) {
            System.out.println("Error: A member with this ID already exists.");
            return;
        }

        System.out.print("Enter Full Name: ");
        String name = scanner.nextLine().trim();

        if (name.isBlank()) {
            System.out.println("Error: Name cannot be empty.");
            return;
        }

        System.out.print("Enter National ID: ");
        String nationalId = scanner.nextLine().trim();

        if (nationalId.isBlank()) {
            System.out.println("Error: National ID cannot be empty.");
            return;
        }

        // Checks the HashSet to ensure no two members have the same National ID
        if (registeredNationalIds.contains(nationalId)) {
            System.out.println("Error: A member with this National ID is already registered.");
            return;
        }

        // The scenario where everything was okay
        Member newMember = new Member(memberId, name, nationalId);
        membersDb.put(memberId, newMember);
        registeredNationalIds.add(nationalId);

        // Log the registration in transactions.txt
        logSystemEvent("Registered new member: " + memberId + " (" + name + ")");
        System.out.println("Member registered successfully.");
    }

    // Method to open a Savings or Loan account for an existing member
    private static void openAccount(Scanner scanner) {
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine().trim();

        // Look up the member in the HashMap
        Member myMember = membersDb.get(memberId);

        if (myMember == null) {
            System.out.println("Error: Member not found.");
            return;
        }

        System.out.println("1. Savings Account");
        System.out.println("2. Loan Account");
        System.out.print("Please pick an account type: ");

        // Variable to store the chosen account type
        int type;
        try {
            type = scanner.nextInt();

            // Consume newline character
            scanner.nextLine();
        }
        catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter 1 or 2.");

            // Consume invalid input
            scanner.nextLine();
            return;
        }

        // Generates an account number using the Member ID and current time
        String accountNumber = memberId + "-" + (System.currentTimeMillis() % 10000);

        // Opening a Savings Account
        if (type == 1) {
            if (myMember.getAccount("Savings") != null) {
                System.out.println("Error: This member already has a Savings Account.");
                return;
            }

            myMember.addAccount("Savings", new SavingsAccount(accountNumber, 0.0));
            logSystemEvent("Opened Savings Account " + accountNumber + " for member " + memberId);
            System.out.println("Savings Account opened successfully. Account No: " + accountNumber);
        }

        // Opening a Loan Account
        else if (type == 2) {
            if (myMember.getAccount("Loan") != null) {
                System.out.println("Error: This member already has a Loan Account.");
                return;
            }

            myMember.addAccount("Loan", new LoanAccount(accountNumber, 0.0));
            logSystemEvent("Opened Loan Account " + accountNumber + " for member " + memberId);
            System.out.println("Loan Account opened successfully. Account No: " + accountNumber);
        }

        // Invalid account type chosen
        else {
            System.out.println("Please enter a valid option. Either 1 or 2.");
        }
    }

    // Method to handle depositing money
    private static void depositMoney(Scanner scanner) {
        Account myAccount = findAccount(scanner);

        if (myAccount == null) {
            return;
        }

        System.out.print("Enter amount to deposit: ");

        // Variable to store how much is being deposited
        double depositAmount;
        try {
            depositAmount = scanner.nextDouble();

            // Consume newline character
            scanner.nextLine();
        }
        catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");

            // Consume invalid input
            scanner.nextLine();
            return;
        }

        myAccount.deposit(depositAmount);
    }

    // Method to handle withdrawing money
    private static void withdrawMoney(Scanner scanner) {
        Account myAccount = findAccount(scanner);

        if (myAccount == null) {
            return;
        }

        System.out.print("Enter amount to withdraw: ");

        // Variable to store how much is being withdrawn
        double withdrawAmount;
        try {
            withdrawAmount = scanner.nextDouble();

            // Consume newline character
            scanner.nextLine();
        }
        catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");

            // Consume invalid input
            scanner.nextLine();
            return;
        }

        // Try withdrawing and catch our custom exception if funds are not enough
        try {
            myAccount.withdraw(withdrawAmount);
        }
        catch (InsufficientFundsException e) {
            System.out.println("Transaction failed: " + e.getMessage());
        }
    }

    // Method to display a member's details and accounts
    private static void viewMemberDetails(Scanner scanner) {
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine().trim();

        Member myMember = membersDb.get(memberId);

        if (myMember == null) {
            System.out.println("Error: Member not found.");
            return;
        }

        // Summary of the member's details
        System.out.println();
        System.out.println("=====MEMBER DETAILS=====");
        System.out.println("Member ID: " + myMember.getMemberId());
        System.out.println("Name: " + myMember.getName());
        System.out.println("National ID: " + myMember.getNationalId());

        Map<String, Account> memberAccounts = myMember.getAccounts();

        // Prints out all accounts owned by the member
        if (memberAccounts.isEmpty()) {
            System.out.println("No accounts opened yet.");
        }
        else {
            System.out.println();
            System.out.println("Accounts:");

            for (Map.Entry<String, Account> entry : memberAccounts.entrySet()) {
                Account acc = entry.getValue();
                System.out.println(entry.getKey() + " Account (" + acc.getAccountNumber() + ")");
                System.out.println("Balance: Ksh. " + acc.getBalance());
                System.out.println("Calculated Interest: Ksh. " + acc.calculateInterest());
            }
        }
    }

    // Helper method used by deposit and withdraw to locate a member's account
    private static Account findAccount(Scanner scanner) {
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine().trim();

        Member myMember = membersDb.get(memberId);

        if (myMember == null) {
            System.out.println("Error: Member not found.");
            return null;
        }

        System.out.print("Enter account type (Savings or Loan): ");
        String inputType = scanner.nextLine().trim();

        // Matching the input regardless of uppercase or lowercase
        String accountType;
        if (inputType.equalsIgnoreCase("Savings")) {
            accountType = "Savings";
        }
        else if (inputType.equalsIgnoreCase("Loan")) {
            accountType = "Loan";
        }
        else {
            System.out.println("Error: Invalid account type. Please enter Savings or Loan.");
            return null;
        }

        Account myAccount = myMember.getAccount(accountType);

        if (myAccount == null) {
            System.out.println("Error: This member does not have a " + accountType + " Account.");
            return null;
        }

        return myAccount;
    }

    // Helper method to append member registration and account creation events to transactions.txt
    private static void logSystemEvent(String message) {
        try (FileWriter fw = new FileWriter(TRANSACTIONS_FILE, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println("SYSTEM - " + message);
        }
        catch (IOException e) {
            System.out.println("Error: Could not write to " + TRANSACTIONS_FILE);
        }
    }

    // Reads members.csv when the program starts and rebuilds the HashMap and HashSet
    private static void loadDataFromFile() {
        File file = new File(MEMBERS_FILE);

        // If the file does not exist yet, start with empty collections
        if (!file.exists()) {
            System.out.println("No previous data found. Starting fresh.");
            return;
        }

        // Using try-with-resources to safely read from the CSV file
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split(",");

                // Expected CSV format: memberId,name,nationalId,accountType,accountNumber,balance
                if (parts.length == 6) {
                    String memberId = parts[0];
                    String name = parts[1];
                    String nationalId = parts[2];
                    String accType = parts[3];
                    String accNumber = parts[4];
                    double balance = Double.parseDouble(parts[5]);

                    // Create the member if they are not already in membersDb
                    Member myMember = membersDb.get(memberId);
                    if (myMember == null) {
                        myMember = new Member(memberId, name, nationalId);
                        membersDb.put(memberId, myMember);
                        registeredNationalIds.add(nationalId);
                    }

                    // Rebuild the member's Savings or Loan account if they have one
                    if (accType.equals("Savings")) {
                        myMember.addAccount("Savings", new SavingsAccount(accNumber, balance));
                    }
                    else if (accType.equals("Loan")) {
                        myMember.addAccount("Loan", new LoanAccount(accNumber, balance));
                    }
                }
            }
            System.out.println("Previous data loaded successfully from " + MEMBERS_FILE);
        }
        catch (FileNotFoundException e) {
            System.out.println("Error: Could not find " + MEMBERS_FILE);
        }
        catch (NumberFormatException e) {
            System.out.println("Error: Invalid balance value found in " + MEMBERS_FILE);
        }
    }

    // Saves all members and their accounts back to members.csv when exiting
    private static void saveDataToFile() {

        // Using try-with-resources to safely write to the CSV file
        try (PrintWriter pw = new PrintWriter(new FileWriter(MEMBERS_FILE))) {
            for (Member myMember : membersDb.values()) {
                Map<String, Account> memberAccounts = myMember.getAccounts();

                // If a member has not opened an account yet, still save their details so they are not lost
                if (memberAccounts.isEmpty()) {
                    pw.println(myMember.getMemberId() + "," +
                            myMember.getName() + "," +
                            myMember.getNationalId() + ",NONE,NONE,0.0");
                }
                else {
                    for (Map.Entry<String, Account> entry : memberAccounts.entrySet()) {
                        String accType = entry.getKey();
                        Account acc = entry.getValue();

                        pw.println(myMember.getMemberId() + "," +
                                myMember.getName() + "," +
                                myMember.getNationalId() + "," +
                                accType + "," +
                                acc.getAccountNumber() + "," +
                                acc.getBalance());
                    }
                }
            }
        }
        catch (IOException e) {
            System.out.println("Error: Could not save data to " + MEMBERS_FILE);
        }
    }
}
