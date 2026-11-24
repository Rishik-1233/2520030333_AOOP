
interface Withdrawable {
    void withdraw(double amount);
}

abstract class BankAccount {
    protected double balance;

    BankAccount(double balance) {
        if (balance < 0)
            throw new IllegalArgumentException("Invalid balance");
        this.balance = balance;
    }

    public void deposit(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Invalid deposit amount");

        balance += amount;
    }

    public double getBalance() {
        return balance;
    }
}

class SavingsAccount extends BankAccount implements Withdrawable {
    SavingsAccount(double balance) {
        super(balance);
    }

    public void withdraw(double amount) {
        if (amount <= 0 || amount > balance)
            throw new IllegalArgumentException("Invalid withdrawal");

        balance -= amount;
    }
}

class CurrentAccount extends BankAccount implements Withdrawable {
    CurrentAccount(double balance) {
        super(balance);
    }

    public void withdraw(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Invalid withdrawal");

        balance -= amount;
    }
}

class FixedDepositAccount extends BankAccount {
    FixedDepositAccount(double balance) {
        super(balance);
    }

    public void closeAtMaturity() {
        System.out.println("Fixed deposit matured. Amount: " + balance);
    }
}

class BankingService {
    public void showBalance(BankAccount account) {
        System.out.println("Balance: " + account.getBalance());
    }

    public void makeWithdrawal(Withdrawable account, double amount) {
        account.withdraw(amount);
    }
}

public class OnlineBanking {

    public static void main(String[] args) {

        BankingService service = new BankingService();

        SavingsAccount savings = new SavingsAccount(10000);
        CurrentAccount current = new CurrentAccount(20000);
        FixedDepositAccount fixed = new FixedDepositAccount(50000);

        savings.deposit(2000);
        current.deposit(5000);
        fixed.deposit(10000);

        service.makeWithdrawal(savings, 3000);
        service.makeWithdrawal(current, 4000);

        service.showBalance(savings);
        service.showBalance(current);
        service.showBalance(fixed);

        fixed.closeAtMaturity();

        System.out.println("All banking operations completed successfully.");
    }
}
