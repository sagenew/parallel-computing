package com.parallel.waitingroom;

public class WaitingRoom {
    private final int maxNumberOfSeats;
    private int seatsOccupied;
    public WaitingRoom(int maxNumberOfSeats) {
        this.maxNumberOfSeats = maxNumberOfSeats;
        seatsOccupied = 0;
    }

    public void occupySeat () {
        seatsOccupied++;
    }

    public void freeSeat () {
        seatsOccupied--;
    }

    public boolean isFull () {
        if(seatsOccupied == maxNumberOfSeats) {
//            System.out.println("Waiting room is full.");
            return true;
        } else return false;
    }

    public boolean isEmpty () {
        if(seatsOccupied == 0) {
//            System.out.println("Waiting room is empty.");
            return true;
        } else return false;
    }

    public int getSeatsOccupied() {
        return seatsOccupied;
    }
}
