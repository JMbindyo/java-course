# Java Course

A collection of Java coursework completed over ten weeks, progressing from Java setup and core syntax to object-oriented programming, abstraction, exception handling, collections, and file processing.

The repository also includes a complete **M-Kopo SACCO Management System** capstone project that brings together many of the concepts learned throughout the course.

## M-Kopo SACCO Capstone Project

**M-Kopo SACCO Management System** is a console-based Java application for managing SACCO members, savings and loan accounts, deposits, withdrawals, account interest, and persistent data.

### Features

- Register members with a unique Member ID and National ID
- Open savings or loan accounts
- Deposit and withdraw money
- Prevent duplicate members and duplicate National IDs
- Calculate interest for each account type
- View member details and account balances
- Save member and account data to CSV
- Load saved data automatically at startup
- Append deposits, withdrawals, registrations, and account events to a transaction log
- Handle invalid menu choices and numeric input gracefully
- Report insufficient-fund transactions with a custom exception

### Account Types

| Account | Interest rate | Behavior |
|---|---:|---|
| Savings | 5% | Calculates interest from the current balance |
| Loan | 12% | Calculates interest from the current balance |

### Main Menu

```text
=====M-KOPO SACCO MANAGEMENT SYSTEM=====
Welcome to M-Kopo SACCO!
1. Register New Member
2. Open an Account
3. Deposit Money
4. Withdraw Money
5. View Member Details
6. Exit
```

### Capstone Structure

```text
capstone_project/
├── SaccoManager.java              # Console UI and application logic
├── Member.java                    # Member data and owned accounts
├── Account.java                   # Abstract account base class
├── SavingsAccount.java            # Savings account implementation
├── LoanAccount.java               # Loan account implementation
├── Auditable.java                 # Transaction logging contract
├── InsufficientFundsException.java # Custom insufficient-funds exception
├── members.csv                    # Persisted member and account records
└── transactions.txt               # Deposit, withdrawal, and system-event log
```

### Core Concepts Demonstrated

- Encapsulation and validated constructors
- Abstract classes and polymorphic interest calculation
- Interface implementation
- Inheritance and method overriding
- HashMap member lookup and HashSet duplicate prevention
- ArrayList transaction history
- Custom checked exceptions
- CSV and text-file persistence
- Input validation and menu-driven control flow
- `try-with-resources` and exception handling

### Running the Capstone

Run the application from the repository root so the relative data file paths resolve correctly:

```bash
javac capstone_project/*.java
java capstone_project.SaccoManager
```

On exit, member and account data are saved in [`capstone_project/members.csv`](capstone_project/members.csv). Transactions are appended to [`capstone_project/transactions.txt`](capstone_project/transactions.txt).

## Course Topics

| Week | Topics |
|---|---|
| 01 | Java setup, syntax, procedural and object-oriented approaches |
| 02 | Data types, operators, type conversion, and interactive input |
| 03 | Boolean expressions, conditionals, switches, and ternary operators |
| 04 | `for`, `while`, and `do-while` loops, debugging, and menu systems |
| 05 | Arrays, multidimensional arrays, `ArrayList`, and algorithms |
| 06 | Classes, objects, constructors, encapsulation, and class design |
| 07 | Inheritance, composition, and method overriding |
| 08 | Abstract classes, polymorphism, and casting |
| 09 | Interfaces, exceptions, and banking examples |
| 10 | Collections, CSV/text files, and data processing |

## Repository Structure

```text
java-course/
├── week01/              # Java basics and introductory OOP
├── week02/              # Data types and operators
├── week03/              # Conditional statements
├── week04/              # Loops and debugging
├── week05/              # Arrays and ArrayLists
├── week06/              # Object-oriented classes
├── week07/              # Inheritance and composition
├── week08/              # Abstraction and polymorphism
├── week09/              # Interfaces and exceptions
├── week10/              # Collections and file handling
└── capstone_project/    # M-Kopo SACCO Management System
```

## Prerequisites

- JDK 11 or newer
- A Java IDE such as IntelliJ IDEA, Eclipse, or VS Code
- A terminal for compiling and running programs

## Running a Weekly Example

Each standalone weekly Java file can usually be compiled and run independently.

```bash
cd week01
javac HelloWorld.java
java HelloWorld
```

Run examples that depend on local files from their weekly directory:

```bash
cd week10
javac DataProcessor.java
java DataProcessor
```

## Highlights

- Procedural and object-oriented implementations
- Practical exercises using menus and number games
- Array, multidimensional-array, and `ArrayList` algorithms
- Encapsulation and class design
- Inheritance, composition, and polymorphism demonstrations
- Banking, fintech, and student-record examples
- Interface and exception-handling implementations
- Collection and CSV/text-file processing
- A complete SACCO management capstone with persistent storage

## License

No license has been specified for this educational repository.

## Author

Created by [JMbindyo](https://github.com/JMbindyo) as school coursework.
