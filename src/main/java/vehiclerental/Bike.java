package vehiclerental;

import java.time.LocalTime;

public class Bike implements Rentable{

    private String id;
    private LocalTime rentingTime;
    public final static int PRICE_PER_MINUTE = 15;

    public Bike(String id) {
        this.id = id;
    }

    @Override
    public int calculateSumPrice(long minutes) {
        return (int) (minutes * PRICE_PER_MINUTE);
    }

    @Override
    public LocalTime getRentingTime() {
        return rentingTime;
    }

    @Override
    public void rent(LocalTime time) {
        this.rentingTime = time;
    }

    @Override
    public void closeRent() {
        this.rentingTime = null;
    }
}
