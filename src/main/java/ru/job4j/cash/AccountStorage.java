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
        Optional<Account> possibleFrom = getById(fromId);
        Optional<Account> possibleTo = getById(toId);
        boolean done = possibleFrom.isPresent() && possibleTo.isPresent() && possibleFrom.get().amount() >= amount;
        if (done) {
            Account from = possibleFrom.get();
            Account to = possibleTo.get();
            accounts.put(from.id(), new Account(from.id(), from.amount() - amount));
            accounts.put(to.id(), new Account(to.id(), to.amount() + amount));
        }
        return done;
    }

}