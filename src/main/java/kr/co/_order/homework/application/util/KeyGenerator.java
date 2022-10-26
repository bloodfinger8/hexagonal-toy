package kr.co._order.homework.application.util;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

public class KeyGenerator {
    private static final AtomicInteger intVal = new AtomicInteger(0);

    private long generateLong() {
        return Instant.now().toEpochMilli() * 65536L + (long)intVal.accumulateAndGet(1, (index, inc) -> {
            return (index + inc) % 65536;
        });
    }
    private String generate() {
        return Long.toHexString(this.generateLong());
    }

    public String makeStringKey() {
        return String.valueOf(this.generateLong());
    }
}
