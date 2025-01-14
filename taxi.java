import java.util.*;
class Taxi{
    int id;
    char currentplatform ='A';
    int earning = 0;
    List <Booking> booking = new ArrayList<>();
    public Taxi(int id){
        this.id=id;
    }
    public boolean isavailable(int requesttime){
        if(booking.isEmpty()){
            return true;
        }
        Booking lastBooking = booking.get(booking.size()-1);
        return lastBooking.droptime <= requesttime;
    }
    public int calculateearning(char from, char to){
        int distance =Math.abs(to-from)*15;
        return 100+ Math.max(0,(distance -5)*10);
    }
    public void addbooking(Booking bk){
        booking.add(bk);
        earning += bk.amount;
        currentplatform = bk.to;
    }
}
