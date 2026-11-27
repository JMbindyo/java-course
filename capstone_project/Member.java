package capstone_project;

// Class representing a registered SACCO member

import java.util.HashMap;
import java.util.Map;

public class Member {

    // Private fields for encapsulation
    private final String memberId;
    private String name;
    private final String nationalId;

    // Stores the member's accounts by type ("Savings" or "Loan")
    private Map<String, Account> accounts;

    // Master constructor
    public Member(String memberId, String name, String nationalId) {

        // Validation for memberId
        if (memberId == null || memberId.isBlank()) {
            this.memberId = "M-000";
        }
        else {
            this.memberId = memberId;
        }

        // Validation for name
        if (name == null || name.isBlank()) {
            this.name = "Unknown";
        }
        else {
            this.name = name;
        }

        // Validation for nationalId
        if (nationalId == null || nationalId.isBlank()) {
            this.nationalId = "00000000";
        }
        else {
            this.nationalId = nationalId;
        }

        // Initializing the HashMap for the member's accounts
        this.accounts = new HashMap<>();
    }

    // Controlled setters
    public void setName(String name) {

        // Validation
        if (name == null || name.isBlank()) {
            System.out.println("Invalid format. Name cannot be blank.");
        }
        else {
            this.name = name;
        }
    }

    public void addAccount(String type, Account account) {
        if (type != null && !type.isBlank() && account != null) {
            accounts.put(type, account);
        }
        else {
            System.out.println("Error: Invalid account details.");
        }
    }

    // Helper method to get one specific account belonging to this member
    public Account getAccount(String type) {
        return accounts.get(type);
    }

    // Getters
    public String getMemberId() {
        return this.memberId;
    }

    public String getName() {
        return this.name;
    }

    public String getNationalId() {
        return this.nationalId;
    }

    public Map<String, Account> getAccounts() {
        return new HashMap<>(this.accounts);
    }
}
