package capstone_project;

// Interface that requires every account to log its transactions
public interface Auditable {
    void logTransaction(String message);
}