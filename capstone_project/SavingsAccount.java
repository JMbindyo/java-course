package capstone_project;

// Subclass for a Savings Account with a 5% interest rate

public class SavingsAccount extends Account {
    public SavingsAccount(String accountNumber, double initialBalance) {
        super(accountNumber, initialBalance);
    }

    // Calculates 5% interest on the current balance
    @Override
    public double calculateInterest() {
        return getBalance() * 0.05;
    }
}
