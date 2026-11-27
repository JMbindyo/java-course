package capstone_project;

// Subclass for a Loan Account with a 12% interest rate
public class LoanAccount extends Account {
    public LoanAccount(String accountNumber, double initialBalance) {
        super(accountNumber, initialBalance);
    }

    // Calculates 12% interest on the current balance
    @Override
    public double calculateInterest() {
        return getBalance() * 0.12;
    }
}