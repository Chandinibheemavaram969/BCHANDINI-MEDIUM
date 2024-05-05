import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Account {
    private String accountNumber;
    private String pin;
    private double balance;

    public Account(String accountNumber, String pin, double balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public boolean validatePin(String pin) {
        return this.pin.equals(pin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public boolean transfer(Account destination, double amount) {
        if (withdraw(amount)) {
            destination.deposit(amount);
            return true;
        }
        return false;
    }
}

public class ATM {
    private static Map<String, Account> accounts = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the ATM");

        while (true) {
            System.out.println("\n1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void createAccount() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Create your account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Create your PIN: ");
        String pin = scanner.nextLine();
        System.out.print("Enter initial balance: $");
        double balance = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        Account account = new Account(accountNumber, pin, balance);
        accounts.put(accountNumber, account);
        System.out.println("Account created successfully!");
    }

    private static void login() {
        System.out.print("Enter your account number: ");
        String accountNumber = scanner.nextLine();
        Account account = accounts.get(accountNumber);
        if (account == null) {
            System.out.println("Account not found. Please try again.");
            return;
        }

        System.out.print("Enter your PIN: ");
        String pin = scanner.nextLine();
        if (!account.validatePin(pin)) {
            System.out.println("Incorrect PIN. Please try again.");
            return;
        }

        showMainMenu(account);
    }

    private static void showMainMenu(Account account) {
        System.out.println("\nMain Menu:");
        System.out.println("1. Check Balance");
        System.out.println("2. Withdraw Cash");
        System.out.println("3. Deposit Money");
        System.out.println("4. Transfer Funds");
        System.out.println("5. Logout");
        System.out.print("Please select an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                checkBalance(account);
                break;
            case 2:
                withdrawCash(account);
                break;
            case 3:
                depositMoney(account);
                break;
            case 4:
                transferFunds(account);
                break;
            case 5:
                System.out.println("Logged out.");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void checkBalance(Account account) {
        System.out.println("Your current balance is: $" + account.getBalance());
    }

    private static void withdrawCash(Account account) {
        System.out.print("Enter the amount to withdraw: $");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        if (account.withdraw(amount)) {
            System.out.println("Please take your cash.");
            System.out.println("Your new balance is: $" + account.getBalance());
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    private static void depositMoney(Account account) {
        System.out.print("Enter the amount to deposit: $");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        account.deposit(amount);
        System.out.println("Deposit successful.");
        System.out.println("Your new balance is: $" + account.getBalance());
    }

    private static void transferFunds(Account source) {
        System.out.print("Enter the recipient's account number: ");
        String recipientAccountNumber = scanner.nextLine();
        Account recipient = accounts.get(recipientAccountNumber);
        if (recipient == null) {
            System.out.println("Recipient account not found.");
            return;
        }

        System.out.print("Enter the amount to transfer: $");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        if (source.transfer(recipient, amount)) {
            System.out.println("Transfer successful.");
            System.out.println("Your new balance is: $" + source.getBalance());
        } else {
            System.out.println("Insufficient funds.");
        }
    }
}
