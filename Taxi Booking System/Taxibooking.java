import java.util.*;

public class TaxiBookingSystem {
    static List<Taxi> taxis = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static int customerCounter = 1; // Auto-incrementing Customer ID

    public static void main(String[] args) {
        System.out.print("Enter number of taxis: ");
        int numTaxis = sc.nextInt();
        initializeTaxis(numTaxis);

        while (true) {
            System.out.println("\n1. Book Taxi\n2. Display Taxi Details\n3. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    bookTaxi();
                    break;
                case 2:
                    displayTaxiDetails();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public static void initializeTaxis(int n) {
        for (int i = 1; i <= n; i++) {
            taxis.add(new Taxi(i));
        }
    }

    public static void bookTaxi() {
        int customerId = customerCounter++; // Automatically increment Customer ID 
        System.out.print("Enter Pickup Point (A-F): ");
        char pickup = sc.next().toUpperCase().charAt(0);
        System.out.print("Enter Drop Point (A-F): ");
        char drop = sc.next().toUpperCase().charAt(0);
        System.out.print("Enter Pickup Time (in hours): ");
        int pickupTime = sc.nextInt();

        Taxi selectedTaxi = null;
        int minDistance = Integer.MAX_VALUE;

        for (Taxi taxi : taxis) {
            if (taxi.isAvailable(pickupTime)) {
                int distance = Math.abs(taxi.currentPoint - pickup);
                if (distance < minDistance ||
                        (distance == minDistance && taxi.totalEarnings < selectedTaxi.totalEarnings)) {
                    selectedTaxi = taxi;
                    minDistance = distance;
                }
            }
        }

        if (selectedTaxi == null) {
            System.out.println("Booking rejected. No taxis available.");
            return;
        }

        int dropTime = pickupTime + Math.abs(drop - pickup);
        int amount = selectedTaxi.calculateEarnings(pickup, drop);
        int bookingId = selectedTaxi.bookings.size() + 1;

        Booking booking = new Booking(bookingId, customerId, pickup, drop, pickupTime, dropTime, amount);
        selectedTaxi.addBooking(booking);
        System.out.println("Taxi-" + selectedTaxi.id + " is allocated.");
    }

    public static void displayTaxiDetails() {
        for (Taxi taxi : taxis) {
            System.out.println("Taxi-" + taxi.id + " Total Earnings: Rs." + taxi.totalEarnings);
            System.out.printf("%-10s %-10s %-5s %-5s %-12s %-9s %-6s%n",
                    "BookingID", "CustomerID", "From", "To", "PickupTime", "DropTime", "Amount");
            for (Booking booking : taxi.bookings) {
                System.out.printf("%-10d %-10d %-5c %-5c %-12d %-9d %-6d%n",
                        booking.bookingId, booking.customerId, booking.from, booking.to,
                        booking.pickupTime, booking.dropTime, booking.amount);
            }
        }
    }
}
