package com.parallel.client;

import com.parallel.waitingroom.WaitingRoom;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Client extends Thread {
    Lock seats;
    Lock barber;
    Lock client;
    Condition barberReady;
    Condition clientReady;
    WaitingRoom waitingRoom;

    public Client(ThreadGroup threadGroup, String name, WaitingRoom waitingRoom,
                  Lock seats, Lock barber, Lock client,
                  Condition barberReady, Condition clientReady) {
        super(threadGroup, name);
        this.seats = seats;
        this.barber = barber;
        this.client = client;
        this.waitingRoom = waitingRoom;
        this.barberReady = barberReady;
        this.clientReady = clientReady;
    }

    @Override
    public void run() {
        seats.lock();
        if (!waitingRoom.isFull()) {
            accessWaitingRoom();
            wakeUpBarber();
            seats.unlock();
            waitForBarber();
            leaveBarbershop();
        } else {
            System.out.println(this.getName() + " can`t enter the waiting room as it is full.");
            leaveBarbershop();
            seats.unlock();
        }
    }


    private void accessWaitingRoom () {
        waitingRoom.occupySeat();
        System.out.println(this.getName() + " has entered the waiting room, number of seats occupied : " + waitingRoom.getSeatsOccupied());
    }

    private void wakeUpBarber () {
        client.lock();
        clientReady.signal();
        client.unlock();
    }

    private void waitForBarber () {
        System.out.println(this.getName() + " is waiting for the barber.");
        try {
            barber.lock();
            barberReady.await();
            System.out.println(this.getName() + " is getting his hair cut.");
            barber.unlock();
        } catch (InterruptedException e) {
            interrupt();
        }
    }

    private void leaveBarbershop () {
        System.out.println(this.getName() + " left the barbershop.");
    }

}
