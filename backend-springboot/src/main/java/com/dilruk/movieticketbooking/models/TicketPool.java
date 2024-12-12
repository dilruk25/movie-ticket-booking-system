package com.dilruk.movieticketbooking.models;

import com.dilruk.movieticketbooking.models.event.Ticket;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class TicketPool {
    private final ConcurrentLinkedQueue<Ticket> tickets = new ConcurrentLinkedQueue<>();
    private final Lock lock = new ReentrantLock();
    private final int maxTicketCapacity;

    public TicketPool(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public boolean addTickets(Ticket ticket) {
        lock.lock();
        try {
            if (tickets.size() < maxTicketCapacity) {
                tickets.add(ticket);
                return true;
            }
            return false; // Capacity reached
        } finally {
            lock.unlock();
        }
    }

    public Ticket removeTicket() {
        lock.lock();
        try {
            return tickets.poll(); // Removes and returns a ticket or null if empty
        } finally {
            lock.unlock();
        }
    }

    public int getTicketCount() {
        return tickets.size();
    }
}