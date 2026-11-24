
import java.util.UUID;

enum PaymentStatus {
    SUCCESS, FAILED
}

class PaymentResult {
    private final String transactionId;
    private final PaymentStatus status;
    private final double amount;

    PaymentResult(PaymentStatus status, double amount) {
        this.transactionId = UUID.randomUUID().toString();
        this.status = status;
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public double getAmount() {
        return amount;
    }
}

interface PaymentProcessor {
    PaymentResult processPayment(double amount);
}

class CreditCardProcessor implements PaymentProcessor {
    public PaymentResult processPayment(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Invalid amount");

        return new PaymentResult(PaymentStatus.SUCCESS, amount);
    }
}

class UpiProcessor implements PaymentProcessor {
    public PaymentResult processPayment(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Invalid amount");

        return new PaymentResult(PaymentStatus.SUCCESS, amount);
    }
}

class NetBankingProcessor implements PaymentProcessor {
    public PaymentResult processPayment(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Invalid amount");

        return new PaymentResult(PaymentStatus.SUCCESS, amount);
    }
}

class DefectivePaymentProcessor implements PaymentProcessor {
    public PaymentResult processPayment(double amount) {
        return null;
    }
}

class PaymentContractTest {

    public static void test(PaymentProcessor processor, String name) {
        PaymentResult result = processor.processPayment(1000);

        if (result == null)
            throw new AssertionError("Payment result is null");

        if (result.getStatus() != PaymentStatus.SUCCESS)
            throw new AssertionError("Payment failed");

        if (result.getAmount() != 1000)
            throw new AssertionError("Incorrect payment amount");

        if (result.getTransactionId() == null ||
            result.getTransactionId().isEmpty())
            throw new AssertionError("Invalid transaction ID");

        System.out.println(name + " contract test passed");
    }
}

public class PaymentProcessing {

    public static void main(String[] args) {

        PaymentProcessor creditCard = new CreditCardProcessor();
        PaymentProcessor upi = new UpiProcessor();
        PaymentProcessor netBanking = new NetBankingProcessor();
        PaymentProcessor defective = new DefectivePaymentProcessor();

        try {
            PaymentContractTest.test(creditCard, "Credit Card");
            PaymentContractTest.test(upi, "UPI");
            PaymentContractTest.test(netBanking, "Net Banking");

            PaymentContractTest.test(defective, "Defective Processor");

        } catch (AssertionError error) {
            System.out.println("Contract violation detected: " + error.getMessage());
        }

        try {
            creditCard.processPayment(-500);
        } catch (IllegalArgumentException error) {
            System.out.println("Invalid amount rejected: " + error.getMessage());
        }

        System.out.println("Payment testing completed.");
    }
}
