import java.util.*;
public class Taxibooking {
    static List<Taxi> taxis = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static int customercounter =1;
    public static void main(String[] args){
        System.out.println("Enter Number of Taxis: ");
        int numTaxis = sc.nextInt();
        initializeTaxis(numTaxis);

        while (true) {
            System.out.println("\n1.Book Taxi\n2.Display Taxi Details\n3.Exit");
            System.out.println("Enter your choice: ");
            int choice=sc.nextInt();
            switch (choice) {
                case 1:
                    booktaxi();
                    break;
                case 2:
                    displaytaxidetails();
                    break;
                case 3:
                    System.out.println("Exit");
                    return;
                default:
                System.out.println("Invalid , Try Again");
            }
        }
    }

    public static void initializeTaxis(int n){
        for(int i=1;i<=n;i++){
            taxis.add(new Taxi(i));
        }
    }
    public static void booktaxi(){
        int customerid = customercounter++;
        System.out.println("\nEnter Pickup Point(A-F): ");
        char Pickup = sc.next().toUpperCase().charAt(0);
        System.out.println("\nEnter Drop Point(A-F): ");
        char Drop = sc.next().toUpperCase().charAt(0);
        System.out.println("\nEnter Pickup Time(in hours): ");
        int pickuptime =sc.nextInt();
        Taxi selectedTaxi = null;
        int mindistance = Integer.MAX_VALUE;
        for(Taxi taxi:taxis){
            if(taxi.isavailable(pickuptime)){
                int distance=Math.abs(taxi.currentplatform-Pickup);
                if(distance<mindistance || (distance==mindistance && taxi.earning <selectedTaxi.earning));{
                selectedTaxi =taxi;
                mindistance = distance;
                }
            }
        }
        if(selectedTaxi == null){
            System.out.println("No Taxis Available, Booking rejected");
            return;
        }
        int droptime= pickuptime +Math.abs(Drop-Pickup);
        int amount= selectedTaxi.calculateearning(Pickup, Drop);
        int bookingid = selectedTaxi.booking.size()+1;
        Booking booking = new Booking(bookingid, customerid, Pickup, Drop, pickuptime, droptime, amount);
        selectedTaxi.addbooking(booking);
        System.out.println("Taxi - " +selectedTaxi.id+ " is allocated\n");
    }
    public static void displaytaxidetails(){
        for(Taxi taxi:taxis){
            System.out.println("Taxi - " + taxi.id +" Total Earnings: Rs." + taxi.earning);
            System.out.printf("\n %-10s %-10s %-5s %-5s %-12s %-9s %-6s %n", "bookingid","customerid","pickup","drop","pickuptime","droptime","amount");   
        
        for(Booking bk: taxi.booking){
            System.out.printf("\n %-10d %-10d %-6c %-6c %-12d %-9d %-6d %n" , bk.bookingid, bk.customerid,bk.from,bk.to, bk.pickuptime, bk.droptime, bk.amount);   
        }
        }
    }
}