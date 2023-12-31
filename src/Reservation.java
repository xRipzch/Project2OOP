import java.time.LocalDateTime;


public class Reservation {
    private String name;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
    private int price;
    private boolean hasPaid;
    private String gender;

    public Reservation(String name, LocalDateTime timeStart, LocalDateTime timeEnd, int price, boolean hasPaid,String gender) {
        this.name = name;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.price = price;
        this.hasPaid = hasPaid;
        this.gender = gender;
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

    public boolean getHasPaid() {
        return hasPaid;
    }


    public void setPrice(int price) {
        this.price = price;
    }

    public void setHasPaid(boolean hasPaid) {
        this.hasPaid = hasPaid;
    }

    public String toString() {
        return     name+","+timeStart.getYear()+","+timeStart.getMonthValue()+","+timeStart.getDayOfMonth()+","+timeStart.getHour()+","+timeStart.getMinute()+","+ timeEnd.getYear()+","+timeEnd.getMonthValue()+","+timeEnd.getDayOfMonth()+","+timeEnd.getHour()+","+timeEnd.getMinute()+","+price+","+hasPaid+","+gender;
    }
}