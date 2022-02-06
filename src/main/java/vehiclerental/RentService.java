package vehiclerental;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RentService {

    private List<Rentable> rentables = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private Map<Rentable, User> actualRenting = new TreeMap<>();

    public void registerUser(User user) {
        if (checkUserDuplication(user.getUserName())) {
            throw new UserNameIsAlreadyTakenException("Username is taken!");
        }
        users.add(user);
    }

    public void addRentable(Rentable rentable) {
        if (!rentables.contains(rentable)) {
            rentables.add(rentable);
        }
    }

    public void rent(User user, Rentable rentable, LocalTime time) {
        boolean isRentableAvailable = rentable.getRentingTime() == null;
        boolean hasEnoughMoney = user.getBalance() >= rentable.calculateSumPrice(180);
        if (isRentableAvailable & hasEnoughMoney) {
            addToActualRenting(user, rentable, time);
        } else {
            throw new IllegalStateException("Constraints not met!");
        }
    }

    public void closeRent(Rentable rentable, int minutes) {
        long amount = rentable.calculateSumPrice(minutes);
        actualRenting.put(rentable, actualRenting.get(rentable).minusBalance(amount));
        actualRenting.remove(rentable);
        rentable.closeRent();
    }

    public List<Rentable> getRentables() {
        return rentables;
    }

    public List<User> getUsers() {
        return users;
    }

    public Map<Rentable, User> getActualRenting() {
        return actualRenting;
    }

    private void addToActualRenting(User user, Rentable rentable, LocalTime time) {
        rentable.rent(time);
        actualRenting.put(rentable, user);
    }

    private boolean checkUserDuplication(String userNameToCheck) {
        if (users.size() == 0) {
            return false;
        } else {
            return users.stream()
                    .anyMatch(u -> u.getUserName().equals(userNameToCheck));
        }
    }
}
