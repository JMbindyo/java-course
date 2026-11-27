package capstone_project;

// Custom exception thrown when someone tries to withdraw more than their balance
public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}