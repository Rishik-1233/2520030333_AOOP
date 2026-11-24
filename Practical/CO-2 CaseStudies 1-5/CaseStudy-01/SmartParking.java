
import java.util.*;

public class SmartParking {

    enum VehicleType {
        CAR, BIKE, TRUCK
    }

    static class Vehicle {
        String registration;
        VehicleType type;

        Vehicle(String registration, VehicleType type) {
            if (registration == null || registration.isBlank() || type == null)
                throw new IllegalArgumentException("Invalid vehicle");

            this.registration = registration;
            this.type = type;
        }
    }

    static class ParkingSpace {
        String id;
        VehicleType accepts;
        boolean occupied;

        ParkingSpace(String id, VehicleType accepts) {
            this.id = id;
            this.accepts = accepts;
        }
    }

    static class ParkingTicket {
        String ticketId;
        String spaceId;
        String registration;

        ParkingTicket(String ticketId, String spaceId, String registration) {
            this.ticketId = ticketId;
            this.spaceId = spaceId;
            this.registration = registration;
        }

        public String toString() {
            return ticketId + " -> " + spaceId + " (" + registration + ")";
        }
    }

    interface SpotAllocator {
        ParkingTicket allocate(Vehicle vehicle, int minutes);
        List<ParkingSpace> spaces();
    }

    static abstract class BaseAllocator implements SpotAllocator {
        List<ParkingSpace> spaces;
        int sequence = 1;

        BaseAllocator(List<ParkingSpace> spaces) {
            this.spaces = spaces;
        }

        ParkingTicket assign(ParkingSpace space, Vehicle vehicle) {
            space.occupied = true;
            return new ParkingTicket("T" + sequence++, space.id, vehicle.registration);
        }

        void validate(Vehicle vehicle, int minutes) {
            if (vehicle == null || minutes <= 0)
                throw new IllegalArgumentException("Invalid request");
        }

        public List<ParkingSpace> spaces() {
            return spaces;
        }
    }

    static class FirstFitAllocator extends BaseAllocator {
        FirstFitAllocator(List<ParkingSpace> spaces) {
            super(spaces);
        }

        public ParkingTicket allocate(Vehicle vehicle, int minutes) {
            validate(vehicle, minutes);

            for (ParkingSpace space : spaces) {
                if (!space.occupied && space.accepts == vehicle.type)
                    return assign(space, vehicle);
            }

            return null;
        }
    }

    static class SmallestFitAllocator extends BaseAllocator {
        SmallestFitAllocator(List<ParkingSpace> spaces) {
            super(spaces);
        }

        public ParkingTicket allocate(Vehicle vehicle, int minutes) {
            validate(vehicle, minutes);

            for (ParkingSpace space : spaces) {
                if (!space.occupied && space.accepts == vehicle.type)
                    return assign(space, vehicle);
            }

            return null;
        }
    }

    static class PriorityAllocator extends BaseAllocator {
        PriorityAllocator(List<ParkingSpace> spaces) {
            super(spaces);
        }

        public ParkingTicket allocate(Vehicle vehicle, int minutes) {
            validate(vehicle, minutes);

            List<ParkingSpace> ordered = new ArrayList<>(spaces);
            ordered.sort(Comparator.comparing(space -> space.id));

            for (ParkingSpace space : ordered) {
                if (!space.occupied && space.accepts == vehicle.type)
                    return assign(space, vehicle);
            }

            return null;
        }
    }

    static class SaboteurAllocator extends BaseAllocator {
        SaboteurAllocator(List<ParkingSpace> spaces) {
            super(spaces);
        }

        public ParkingTicket allocate(Vehicle vehicle, int minutes) {
            validate(vehicle, minutes);

            for (ParkingSpace space : spaces) {
                if (!space.occupied && space.accepts == vehicle.type)
                    return new ParkingTicket("BAD" + sequence++, space.id, vehicle.registration);
            }

            return null;
        }
    }

    static List<ParkingSpace> sampleSpaces() {
        return new ArrayList<>(List.of(
            new ParkingSpace("P1", VehicleType.CAR),
            new ParkingSpace("P2", VehicleType.BIKE),
            new ParkingSpace("P3", VehicleType.TRUCK)
        ));
    }

    static void contractTest(String name, SpotAllocator allocator) {
        Vehicle vehicle = new Vehicle("TS09AB1234", VehicleType.CAR);
        ParkingTicket ticket = allocator.allocate(vehicle, 60);

        if (ticket == null)
            throw new AssertionError("Allocation failed");

        ParkingSpace assigned = allocator.spaces().stream()
            .filter(space -> space.id.equals(ticket.spaceId))
            .findFirst()
            .orElseThrow();

        if (!assigned.occupied)
            throw new AssertionError("Space is not occupied");

        System.out.println(name + " passed: " + ticket);
    }

    public static void main(String[] args) {
        contractTest("FirstFit", new FirstFitAllocator(sampleSpaces()));
        contractTest("SmallestFit", new SmallestFitAllocator(sampleSpaces()));
        contractTest("Priority", new PriorityAllocator(sampleSpaces()));

        try {
            contractTest("Saboteur", new SaboteurAllocator(sampleSpaces()));
        } catch (AssertionError error) {
            System.out.println("Saboteur detected: " + error.getMessage());
        }
    }
}
