package com.parallel.barber;

import com.parallel.waitingroom.WaitingRoom;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Barber extends Thread {
    Lock seats;
    Lock barber;
    Lock client;
    Condition barberReady;
    Condition clientReady;
    WaitingRoom waitingRoom;
    long cuttingTime;
    int clientsCut;

    public Barber(ThreadGroup threadGroup, String name, long cuttingTime, WaitingRoom waitingRoom,
                  Lock seats, Lock barber, Lock client,
                  Condition barberReady, Condition clientReady) {
        super(threadGroup, name);
        this.cuttingTime = cuttingTime;
        this.waitingRoom = waitingRoom;
        this.seats = seats;
        this.barber = barber;
        this.client = client;
        this.barberReady = barberReady;
        this.clientReady = clientReady;
        clientsCut = 0;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            if(waitingRoom.isEmpty()) sleep();
            inviteClient();
            cutHair();

        }
    }

    private void sleep () {
        try {
            System.out.println(this.getName() + " has fallen asleep.");
            client.lock();
            clientReady.await();
            client.unlock();
            System.out.println(this.getName() + " has awaken.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void inviteClient () {
        seats.lock();
        waitingRoom.freeSeat();
        barber.lock();
        barberReady.signal();
        barber.unlock();
        seats.unlock();
    }

    private void cutHair () {
        System.out.println("\n" + this.getName() + " is cutting hair.");
        try {
            Thread.sleep(cuttingTime);
            clientsCut++;
//            System.out.println("Clients cut : " + clientsCut);
        } catch (InterruptedException e) {
            interrupt();
        }
    }

    public int getClientsCut() {
        return clientsCut;
    }
}
