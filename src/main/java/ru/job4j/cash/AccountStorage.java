package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class AccountStorage {

    @GuardedBy("this")
    private final Map<Integer, Account> accounts = new ConcurrentHashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), new Account(account.id(), account.amount())) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.computeIfPresent(account.id(), (k, v) -> new Account(k, account.amount())) != null;
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id) != null;
    }

    public synchronized Optional<Account> getById(int id) {
        Account account = accounts.get(id);
        return Optional.ofNullable(account != null ? new Account(account.id(), account.amount()) : null);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean done = accounts.containsKey(fromId)
                && accounts.containsKey(toId)
                && accounts.get(fromId).amount() >= amount;
        if (done) {
            Account from = accounts.get(fromId);
            Account to = accounts.get(toId);
            accounts.put(fromId, new Account(fromId, from.amount() - amount));
            accounts.put(toId, new Account(toId, to.amount() + amount));
        }
        return done;
    }

}