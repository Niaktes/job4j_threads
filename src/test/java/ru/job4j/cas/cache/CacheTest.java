package ru.job4j.cas.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheTest {

    private Cache cache;

    @BeforeEach
    void init() {
        cache = new Cache();
    }

    @Test
    void whenAddModelTwiceThenGetFalse() {
        assertTrue(cache.add(new Base(1, 1)));
        assertFalse(cache.add(new Base(1, 1)));
    }

    @Test
    void whenDeleteCachedModelThenGetFalse() {
        Base model = new Base(1, 1);
        cache.add(model);
        cache.delete(model);
        assertNull(cache.get(model.getId()));
    }

    @Test
    void whenUpdateModelThenGetModelWithIncreasedVersion() {
        Base model = new Base(1, 1);
        Base updater = new Base (1, 1);
        updater.setName("updated");
        cache.add(model);
        assertFalse(cache.add(updater));
        assertTrue(cache.update(updater));
        assertEquals(2, cache.get(1).getVersion());
        assertEquals("updated", cache.get(1).getName());
    }

    @Test
    void whenUpdateWithWrongVersionThenExceptionThrown() {
        Base model = new Base(1, 3);
        Base updater = new Base (1, 2);
        cache.add(model);
        assertThrows(OptimisticException.class, () -> cache.update(updater));
    }

}