
import java.util.*;

interface PaymentStrategy {
    void pay(double amount);
}

class CashPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Paid Rs." + amount + " using Cash");
    }
}

class UpiPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Paid Rs." + amount + " using UPI");
    }
}

class PaymentFactory {
    public static PaymentStrategy getPayment(String type) {
        if (type.equalsIgnoreCase("cash"))
            return new CashPayment();

        if (type.equalsIgnoreCase("upi"))
            return new UpiPayment();

        throw new IllegalArgumentException("Invalid payment type");
    }
}

interface HospitalObserver {
    void update(String message);
}

class PatientNotification implements HospitalObserver {
    public void update(String message) {
        System.out.println("Notification: " + message);
    }
}

class Hospital {
    private List<HospitalObserver> observers = new ArrayList<>();

    public void addObserver(HospitalObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(String message) {
        for (HospitalObserver observer : observers) {
            observer.update(message);
        }
    }
}

class Patient {
    private int id;
    private String name;

    Patient(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void displayPatient() {
        System.out.println("Patient ID: " + id);
        System.out.println("Patient Name: " + name);
    }
}

class Billing {
    private PaymentStrategy payment;

    Billing(PaymentStrategy payment) {
        this.payment = payment;
    }

    public void generateBill(double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Invalid bill amount");

        payment.pay(amount);
    }
}

interface DiagnosticService {
    void performTest();
}

class BloodTest implements DiagnosticService {
    public void performTest() {
        System.out.println("Blood test completed");
    }
}

class XRay implements DiagnosticService {
    public void performTest() {
        System.out.println("X-Ray completed");
    }
}

public class HospitalManagement {

    public static void main(String[] args) {

        Patient patient = new Patient(101, "Rishik");
        patient.displayPatient();

        Hospital hospital = new Hospital();
        hospital.addObserver(new PatientNotification());

        hospital.notifyObservers("Appointment confirmed");

        PaymentStrategy payment = PaymentFactory.getPayment("upi");
        Billing billing = new Billing(payment);
        billing.generateBill(1500);

        DiagnosticService bloodTest = new BloodTest();
        DiagnosticService xray = new XRay();

        bloodTest.performTest();
        xray.performTest();

        hospital.notifyObservers("Medical tests completed");

        System.out.println("Hospital management system completed.");
    }
}
