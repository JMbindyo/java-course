package capstone_project;

// Abstract parent class for SavingsAccount and LoanAccount

import java.io.*;
import java.util.ArrayList;

public abstract class Account implements Auditable {

    // Private fields for encapsulation
    private final String accountNumber;
    private double balance;
    private ArrayList<String> transactionHistory;

    // Master constructor
    public Account(String accountNumber, double initialBalance) {

        // Validation for accountNumber
        if (accountNumber == null || accountNumber.isBlank()) {
            this.accountNumber = "ACC-0000";
        }
        else {
            this.accountNumber = accountNumber;
        }

        // Validation for initialBalance so an account cannot start with negative money
        if (initialBalance < 0) {
            this.balance = 0.0;
        }
        else {
            this.balance = initialBalance;
        }

        // Initializing the ArrayList to store history of deposits and withdrawals
        this.transactionHistory = new ArrayList<>();
    }

    // Method for depositing money into the account
    public void deposit(double depositAmount) {

        // Checks whether the user entered a positive deposit amount
        if (depositAmount <= 0) {
            System.out.println("Error: Deposit amount must be greater than Ksh. 0.");
        }

        // The scenario where everything was okay
        else {

            // Adds depositAmount to balance and records the transaction
            balance += depositAmount;
            String log = "Deposited Ksh. " + depositAmount + " | New Balance: Ksh. " + balance;
            transactionHistory.add(log);
            logTransaction(log);

            // Shows how much money is now in the account
            System.out.println("Amount remaining in account: Ksh. " + balance);
        }
    }

    // Method for withdrawing money from the account
    public void withdraw(double withdrawAmount) throws InsufficientFundsException {

        // Checks whether the withdrawal amount is valid
        if (withdrawAmount <= 0) {
            System.out.println("Error: Withdrawal amount must be greater than Ksh. 0.");
            return;
        }

        // Checks if the amount someone wants to withdraw is greater than what is in the balance
        if (withdrawAmount > balance) {
            throw new InsufficientFundsException("Not enough money in account to withdraw. Current balance is Ksh. " + balance);
        }

        // The scenario where everything was okay
        balance -= withdrawAmount;
        String log = "Withdrew Ksh. " + withdrawAmount + " | New Balance: Ksh. " + balance;
        transactionHistory.add(log);
        logTransaction(log);

        System.out.println("Amount remaining in account: Ksh. " + balance);
    }

    // Abstract method that subclasses must override with their own interest rate
    public abstract double calculateInterest();

    // Appends every deposit or withdrawal to transactions.txt
    @Override
    public void logTransaction(String message) {

        // Using try-with-resources so the file closes automatically
        try (FileWriter fw = new FileWriter("capstone_project/transactions.txt", true);
             PrintWriter pw = new PrintWriter(fw)) {
                pw.println(accountNumber + " - " + message);
        }
        catch (IOException e) {
            System.out.println("Error: Could not write to transactions.txt.");
        }
    }

    // Getters
    public String getAccountNumber() {
        return this.accountNumber;
    }

    public double getBalance() {
        return this.balance;
    }

    public ArrayList<String> getTransactionHistory() {
        return new ArrayList<>(this.transactionHistory);
    }
}
