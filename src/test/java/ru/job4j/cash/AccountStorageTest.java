package ru.job4j.cash;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class AccountStorageTest {

    @Test
    void whenAdd() {
        var storage = new AccountStorage();
        assertThat(storage.add(new Account(1, 100))).isTrue();
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(100);
    }

    @Test
    void whenUpdate() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        assertThat(storage.update(new Account(1, 200))).isTrue();
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(200);
    }

    @Test
    void whenDelete() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        assertThat(storage.delete(1)).isTrue();
        assertThat(storage.getById(1)).isEmpty();
    }

    @Test
    void whenTransferFromNonexistentThenFalse() {
        var storage = new AccountStorage();
        storage.add(new Account(2, 100));
        storage.add(new Account(3, 100));
        assertThat(storage.transfer(1, 2, 100)).isFalse();
    }

    @Test
    void whenTransferToNonexistentThenFalse() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.add(new Account(3, 100));
        assertThat(storage.transfer(1, 2, 100)).isFalse();
    }

    @Test
    void whenTransferInsufficientThenFalse() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 99));
        storage.add(new Account(2, 100));
        assertThat(storage.transfer(1, 2, 100)).isFalse();
    }

    @Test
    void whenTransfer() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.add(new Account(2, 100));
        storage.transfer(1, 2, 100);
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        var secondAccount = storage.getById(2)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isZero();
        assertThat(secondAccount.amount()).isEqualTo(200);
    }



}