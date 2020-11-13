package com.parallel;

import com.parallel.barber.Barber;
import com.parallel.client.ClientProducer;
import com.parallel.waitingroom.WaitingRoom;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SleepingBarberExample {
    //4. Сплячий перукар - Reentrant Lock.
    private final static int CLIENTS_NUMBER = 50;
    private final static int CLIENT_ARRIVING_INTERVAL_LOWEST = 10;
    private final static int CLIENT_ARRIVING_INTERVAL_HIGHEST = 50;
    private final static int WAITING_ROOM_SEATS = 10;
    private final static int CUTTING_TIME = 50;

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock seats = new ReentrantLock();
        ReentrantLock barber = new ReentrantLock();
        ReentrantLock client = new ReentrantLock();

        Condition barberReady = barber.newCondition();
        Condition clientReady = client.newCondition();

        WaitingRoom waitingRoom = new WaitingRoom(WAITING_ROOM_SEATS);

        ThreadGroup barberThreadGroup = new ThreadGroup("Barbers");
        Barber myBarber = new Barber(barberThreadGroup, "Barber", CUTTING_TIME, waitingRoom, seats, barber, client, barberReady, clientReady);
        myBarber.start();

        ThreadGroup clientThreadGroup = new ThreadGroup("Clients");
        ClientProducer clientProducer = new ClientProducer(clientThreadGroup, CLIENTS_NUMBER,
                CLIENT_ARRIVING_INTERVAL_LOWEST, CLIENT_ARRIVING_INTERVAL_HIGHEST,
                waitingRoom, seats, barber, client, barberReady, clientReady);
        clientProducer.startProducingClients();

        while (clientThreadGroup.activeCount() != 0) { Thread.sleep(10); }

        myBarber.interrupt();
        System.out.println();
        System.out.println("Barbershop is closed. The barber has cut " + myBarber.getClientsCut() + " clients.");
    }
}
