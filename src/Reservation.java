import java.time.LocalDateTime;


public class Reservation {
    private String name;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
    private int price;
    private boolean hasPaid;

    public Reservation(String name, LocalDateTime timeStart, LocalDateTime timeEnd, int price, boolean hasPaid) {
        this.name = name;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.price = price;
        this.hasPaid = hasPaid;
    }

    public String getName() {
        return name;
    }
    
    public LocalDateTime getTimeStart() {
        return timeStart;
    }
    
    public LocalDateTime getTimeEnd() {
        return timeEnd;
    }
    
    public int getPrice() {
        return price;
    }
    
    
    

    public boolean setHasPaid() {
        return hasPaid;
    }

    public void setPrice(int i) {
    }
}