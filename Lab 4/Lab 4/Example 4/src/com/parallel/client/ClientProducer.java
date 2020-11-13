package com.parallel.client;

import com.parallel.waitingroom.WaitingRoom;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ClientProducer {
    ThreadGroup clientThreadGroup;
    int numberOfClients;
    long intervalLowest;
    long intervalHighest;
    WaitingRoom waitingRoom;
    Lock seats;
    Lock barber;
    Lock client;
    Condition barberReady;
    Condition clientReady;


    public ClientProducer(ThreadGroup clientThreadGroup, int numberOfClients, long intervalLowest,
                          long intervalHighest, WaitingRoom waitingRoom,
                          Lock seats, Lock barber, Lock client,
                          Condition barberReady, Condition clientReady) {
        this.clientThreadGroup = clientThreadGroup;
        this.numberOfClients = numberOfClients;
        this.intervalLowest = intervalLowest;
        this.intervalHighest = intervalHighest;
        this.waitingRoom = waitingRoom;
        this.seats = seats;
        this.barber = barber;
        this.client = client;
        this.clientReady = clientReady;
        this.barberReady = barberReady;
    }

    public void startProducingClients() {
        for (int i = 0; i < numberOfClients; i++) {
            handleInterval();
            Thread newClient  = new Client(clientThreadGroup,"Client #" + (i + 1), waitingRoom, seats, barber, client, barberReady, clientReady);
            newClient.start();
            System.out.println(newClient.getName() + " arrived to the barbershop.");
        }
    }

    private void handleInterval() {
        try {
            Thread.sleep(getNextInterval());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private long getNextInterval() {
        return (long) (Math.random() * (intervalHighest - intervalLowest) + intervalLowest);
    }
}
